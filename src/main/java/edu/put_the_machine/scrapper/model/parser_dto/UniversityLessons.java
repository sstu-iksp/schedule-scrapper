package edu.put_the_machine.scrapper.model.parser_dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class UniversityLessons {
    private final String universityName;
    private final List<GroupLessons> groupsLessons;
}
