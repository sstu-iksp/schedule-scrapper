package edu.put_the_machine.scrapper.model.dto

import java.time.LocalTime

data class LessonDto @JvmOverloads constructor(
    val subject: String,
    val type: String,
    val start: LocalTime,
    val end: LocalTime,
    val teacher: TeacherDto?,
    val group: GroupDto,
    val location: String,
    val id: Long? = null
)