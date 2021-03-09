package edu.put_the_machine.scrapper.model.parser_dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class GroupLessons {
    private final String groupName;
    private final List<LessonParserDto> lessons;
    private final LocalDate start;
    private final LocalDate end;
}
