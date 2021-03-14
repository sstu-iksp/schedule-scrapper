package edu.put_the_machine.scrapper.model.dto.parser


data class GroupLessons(
    val groupName: String,
    val lessons: List<LessonParserDto>
)