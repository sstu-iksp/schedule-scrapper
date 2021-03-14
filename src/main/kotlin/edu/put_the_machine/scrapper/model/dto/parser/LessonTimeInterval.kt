package edu.put_the_machine.scrapper.model.dto.parser

import java.time.LocalDate
import java.time.LocalTime


data class LessonTimeInterval(
    val start: LocalTime,
    val end: LocalTime,
    val date: LocalDate
)

