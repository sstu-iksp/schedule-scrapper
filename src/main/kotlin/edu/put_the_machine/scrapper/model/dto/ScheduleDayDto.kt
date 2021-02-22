package edu.put_the_machine.scrapper.model.dto

import java.time.LocalDate
import java.time.LocalDateTime

class ScheduleDayDto(
    val lessons: List<LessonDto>,
    val date: LocalDate,
    val group: GroupDto,
    val lastCheck: LocalDateTime
)