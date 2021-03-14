package edu.put_the_machine.scrapper.model.dto.parser.dto

import lombok.EqualsAndHashCode
import lombok.Getter
import lombok.RequiredArgsConstructor
import lombok.ToString

class UniversityLessons(
    val universityName: String,
    val groupsLessons: List<GroupLessons>
)