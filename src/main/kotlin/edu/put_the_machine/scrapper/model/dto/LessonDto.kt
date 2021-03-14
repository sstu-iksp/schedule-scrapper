package edu.put_the_machine.scrapper.model.dto

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