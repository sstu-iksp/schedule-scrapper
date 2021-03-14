package edu.put_the_machine.scrapper.model.dto.parser.dto

import java.time.LocalDate


data class GroupLessons(
    val groupName: String,
    val lessons: List<LessonParserDto>,
    val start: LocalDate,
    val end: LocalDate
)