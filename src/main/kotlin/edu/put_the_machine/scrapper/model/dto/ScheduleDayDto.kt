package edu.put_the_machine.scrapper.model.dto

import java.time.LocalDate
import java.time.LocalDateTime

abstract class ScheduleDayDto(
    val lessons: List<LessonDto>,
    val date: LocalDate,
    val lastCheck: LocalDateTime
)

class GroupScheduleDayDto(val group: GroupDto,
                          lessons: List<LessonDto>, date: LocalDate, lastCheck: LocalDateTime) : ScheduleDayDto(lessons, date, lastCheck)

class LocationScheduleDayDto(val location: LocationDto,
                             lessons: List<LessonDto>, date: LocalDate, lastCheck: LocalDateTime) : ScheduleDayDto(lessons, date, lastCheck)

class TeacherScheduleDayDto(val teacher: TeacherDto,
                            lessons: List<LessonDto>, date: LocalDate, lastCheck: LocalDateTime) : ScheduleDayDto(lessons, date, lastCheck)