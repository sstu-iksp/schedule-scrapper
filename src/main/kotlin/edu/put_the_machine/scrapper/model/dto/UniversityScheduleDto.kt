package edu.put_the_machine.scrapper.model.dto

import edu.put_the_machine.scrapper.model.Lesson
import edu.put_the_machine.scrapper.model.University
import java.time.LocalDate

data class UniversityScheduleDto(
    val universityDto: University,
    val start: LocalDate,
    val end: LocalDate,
    val lessons: List<Lesson>
)