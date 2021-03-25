package edu.put_the_machine.scrapper.model.dto

import edu.put_the_machine.scrapper.model.Lesson
import java.time.LocalDate
import java.time.LocalTime

data class LessonDto @JvmOverloads constructor(
    val subject: String,
    val type: String,
    val date: LocalDate,
    val start: LocalTime,
    val end: LocalTime,
    val teacherId: Long?,
    val groupId: Long,
    val location: String?,
    val id: Long? = null
)

fun Lesson.toDto() = LessonDto(
    this.subject,
    this.type,
    this.date,
    this.start,
    this.finish,
    this.teacher?.id,
    this.group.id!!,
    this.location,
    this.id
)
