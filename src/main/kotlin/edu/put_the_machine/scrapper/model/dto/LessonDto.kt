package edu.put_the_machine.scrapper.model.dto

import java.time.LocalTime

class LessonDto(
    val subject: String,
    val type: String,
    val start: LocalTime,
    val end: LocalTime,
    val teacher: TeacherDto,
    val location: LocationDto
)