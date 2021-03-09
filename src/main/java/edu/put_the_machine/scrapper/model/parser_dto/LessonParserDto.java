package edu.put_the_machine.scrapper.model.parser_dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class LessonParserDto {
    private final String subject;
    private final String type;
    private final String location;
    private final TeacherParserDto teacher;
    private final LessonTimeInterval lessonTimeInterval;
}
