package edu.put_the_machine.scrapper.model.parser_dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class LessonTimeInterval {
    private final LocalTime start;
    private final LocalTime end;
    private final LocalDate when;
}
