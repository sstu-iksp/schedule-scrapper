package edu.put_the_machine.scrapper.model.parser_dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class TeacherParserDto {
    private final String name;
    private final String url;
}
