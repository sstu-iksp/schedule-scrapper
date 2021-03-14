package edu.put_the_machine.scrapper.model.dto.parser.dto

data class LessonParserDto(
    val subject: String,
    val type: String,
    val location: String?,
    val teacher: TeacherParserDto?,
    val lessonTimeInterval: LessonTimeInterval
)