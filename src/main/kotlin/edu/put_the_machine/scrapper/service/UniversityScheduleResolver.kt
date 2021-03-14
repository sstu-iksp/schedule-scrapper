package edu.put_the_machine.scrapper.service

import edu.put_the_machine.scrapper.model.Group
import edu.put_the_machine.scrapper.model.Teacher
import edu.put_the_machine.scrapper.model.University
import edu.put_the_machine.scrapper.model.dto.LessonDto
import edu.put_the_machine.scrapper.model.dto.TeacherDto
import edu.put_the_machine.scrapper.model.dto.parser.GroupLessons
import edu.put_the_machine.scrapper.model.dto.parser.LessonParserDto
import edu.put_the_machine.scrapper.model.dto.parser.TeacherParserDto
import edu.put_the_machine.scrapper.model.dto.parser.UniversityLessons
import edu.put_the_machine.scrapper.service.interfaces.GroupService
import edu.put_the_machine.scrapper.service.interfaces.TeacherService
import edu.put_the_machine.scrapper.service.interfaces.UniversityService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface UniversityScheduleResolver {
    fun resolve(universityLessons: UniversityLessons)
}

@Service
class UniversityScheduleResolverImpl(
    private val universityService: UniversityService,
    private val groupService: GroupService,
    private val lessonService: LessonService,
    private val teacherService: TeacherService
) : UniversityScheduleResolver {

    @Transactional
    override fun resolve(universityLessons: UniversityLessons) {
        val university = universityService.findByName(universityLessons.universityName)
            .orElseThrow { throw IllegalArgumentException("University with name ${universityLessons.universityName} was not found.") }

        universityLessons.groupsLessons
            .forEach { groupLessons -> resolveUniversityGroupLessons(university, groupLessons) }
    }

    private fun resolveUniversityGroupLessons(
        university: University,
        groupLessons: GroupLessons
    ) {
        val group = groupService.findByUniversityIdAndName(university.id, groupLessons.groupName)
            .orElseGet { groupService.createGroup(university, groupLessons.groupName) }

        if (groupLessons.lessons.isEmpty())
            return

        val teachers = resolveTeachers(university, groupLessons.lessons.mapNotNull { it.teacher })

        resolveGroupLessons(groupLessons.lessons, group, teachers)
    }

    private fun resolveTeachers(
        university: University,
        teachersToResolve: List<TeacherParserDto>
    ): List<Teacher> {
        val existsTeachers =
            teacherService.findTeachersByUniversityIdAndNames(university.id, teachersToResolve.map { it.name })

        existsTeachers
            .map { teacher -> teacher to teachersToResolve.find { it.name == teacher.name } }
            .filter { (teacher, teacherToResolve) -> teacher.url != teacherToResolve?.url }
            .forEach { (teacher, teacherToResolve) -> teacherService.updateUrl(teacher.id, teacherToResolve?.url) }

        val nonExistsTeacherDtos = teachersToResolve
            .filter { teacher -> !existsTeachers.map { it.name }.contains(teacher.name) }
            .map { teacher -> TeacherDto(teacher.name, teacher.url, university.id!!) }

        val createdTeachers = teacherService.createTeachers(university, nonExistsTeacherDtos)

        return existsTeachers.plus(createdTeachers)
    }

    fun resolveGroupLessons(lessonParserDtos: List<LessonParserDto>, group: Group, teachers: List<Teacher>) {
        if (lessonParserDtos.isEmpty()) {
            return
        }

        val lessonsStart = lessonParserDtos.minOf { it.lessonTimeInterval.date }
        val lessonsEnd = lessonParserDtos.maxOf { it.lessonTimeInterval.date }

        lessonService.deleteByGroupIdAndBetween(group.id!!, lessonsStart, lessonsEnd)

        val teacherMap = teachers.map { it.name to it }.toMap()

        val lessonDtos = lessonParserDtos.map {
            LessonDto(
                subject = it.subject,
                type = it.type,
                date = it.lessonTimeInterval.date,
                start = it.lessonTimeInterval.start,
                end = it.lessonTimeInterval.end,
                teacherId = teacherMap[it.teacher?.name]?.id,
                groupId = group.id!!,
                location = it.location
            )
        }

        lessonService.saveLessons(lessonDtos)

    }
}