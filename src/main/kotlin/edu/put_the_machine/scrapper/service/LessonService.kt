package edu.put_the_machine.scrapper.service

import edu.put_the_machine.scrapper.extensions.getReference
import edu.put_the_machine.scrapper.extensions.getReferenceOfNullable
import edu.put_the_machine.scrapper.model.Lesson
import edu.put_the_machine.scrapper.model.dto.LessonDto
import edu.put_the_machine.scrapper.repository.LessonRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

interface LessonService {
    fun findByGroupIdAndBetween(groupId: Long, start: LocalDate, end: LocalDate): List<Lesson>
    fun deleteByGroupIdAndBetween(groupId: Long, start: LocalDate, end: LocalDate): List<Lesson>
    fun saveLessons(lessonDtos: List<LessonDto>): Iterable<Lesson>
    fun findLessons(
        universityId: Long?,
        teacherId: Long?,
        groupId: Long?,
        startDate: LocalDate?,
        endDate: LocalDate?,
        subject: String?,
        location: String?,
        type: String?,
        pageable: Pageable
    ): List<Lesson>
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

    override fun findLessons(
        universityId: Long?,
        teacherId: Long?,
        groupId: Long?,
        startDate: LocalDate?,
        endDate: LocalDate?,
        subject: String?,
        location: String?,
        type: String?,
        pageable: Pageable
    ): List<Lesson> {
        val specification = Specification { root: Root<Lesson>, _, criteriaBuilder: CriteriaBuilder ->
            val predicates: MutableList<Predicate> = ArrayList()

            if (universityId != null) {
                predicates.add(
                    criteriaBuilder.equal(
                        root.get<Any>("group").get<Any>("university").get<Any>("id"),
                        universityId
                    )
                )
            }
            if (teacherId != null) {
                predicates.add(criteriaBuilder.equal(root.get<Any>("teacher").get<Any>("id"), teacherId))
            }
            if (groupId != null) {
                predicates.add(criteriaBuilder.equal(root.get<Any>("group").get<Any>("id"), groupId))
            }
            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), startDate))
            }
            if (endDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), endDate))
            }
            if (subject != null) {
                predicates.add(criteriaBuilder.equal(root.get<Any>("subject"), subject))
            }
            if (location != null) {
                predicates.add(criteriaBuilder.equal(root.get<Any>("location"), location))
            }
            if (type != null) {
                predicates.add(criteriaBuilder.equal(root.get<Any>("type"), type))
            }
            criteriaBuilder.and(*predicates.toTypedArray())
        }
        return lessonRepository.findAll(specification, pageable).toList()
    }

}

