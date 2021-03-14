package edu.put_the_machine.scrapper.service

import edu.put_the_machine.scrapper.extensions.getReference
import edu.put_the_machine.scrapper.extensions.getReferenceOfNullable
import edu.put_the_machine.scrapper.model.Lesson
import edu.put_the_machine.scrapper.model.dto.LessonDto
import edu.put_the_machine.scrapper.repository.LessonRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.EntityManager

interface LessonService {
    fun findByGroupIdAndBetween(groupId: Long, start: LocalDate, end: LocalDate): List<Lesson>
    fun deleteByGroupIdAndBetween(groupId: Long, start: LocalDate, end: LocalDate): List<Lesson>
    fun saveLessons(lessonDtos: List<LessonDto>): Iterable<Lesson>
}

@Service
class LessonServiceImpl(
    private val lessonRepository: LessonRepository,
    private val entityManager: EntityManager
) : LessonService {
    override fun findByGroupIdAndBetween(groupId: Long, start: LocalDate, end: LocalDate): List<Lesson> =
        lessonRepository.findByGroupIdAndDateBetween(groupId, start, end)


    override fun deleteByGroupIdAndBetween(groupId: Long, start: LocalDate, end: LocalDate): List<Lesson> =
        lessonRepository.deleteByGroupIdAndDateBetween(groupId, start, end)

    override fun saveLessons(lessonDtos: List<LessonDto>): Iterable<Lesson> {
        val lessons = lessonDtos.map {
            Lesson(
                subject = it.subject,
                type = it.type,
                date = it.date,
                start = it.start,
                finish = it.end,
                group = entityManager.getReference(it.groupId),
                teacher = entityManager.getReferenceOfNullable(it.teacherId),
                location = it.location,
                lastCheck = LocalDateTime.now()
            )
        }
        return lessonRepository.saveAll(lessons)
    }

}

