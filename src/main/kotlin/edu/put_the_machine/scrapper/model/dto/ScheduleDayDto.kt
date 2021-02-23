package edu.put_the_machine.scrapper.model.dto

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.time.LocalDate
import java.time.LocalDateTime


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "_type_")
@JsonSubTypes(
    JsonSubTypes.Type(value = GroupScheduleDayDto::class, name = "GroupScheduleDayDto"),
    JsonSubTypes.Type(value =  LocationScheduleDayDto::class, name = "LocationScheduleDayDto"),
    JsonSubTypes.Type(value =  TeacherScheduleDayDto::class, name = "TeacherScheduleDayDto")
)
abstract class ScheduleDayDto(
    val lessons: List<LessonDto>,
    val date: LocalDate,
    val lastCheck: LocalDateTime


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ScheduleDayDto) return false

        if (lessons != other.lessons) return false
        if (date != other.date) return false

        return true
    }

    override fun hashCode(): Int {
        var result = lessons.hashCode()
        result = 31 * result + date.hashCode()
        return result
    }
}

class GroupScheduleDayDto(val group: GroupDto,
                          lessons: List<LessonDto>, date: LocalDate, lastCheck: LocalDateTime) : ScheduleDayDto(lessons, date, lastCheck) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GroupScheduleDayDto) return false
        if (!super.equals(other)) return false

        if (group != other.group) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + group.hashCode()
        return result
    }
}

class LocationScheduleDayDto(val location: LocationDto,
                             lessons: List<LessonDto>, date: LocalDate, lastCheck: LocalDateTime) : ScheduleDayDto(lessons, date, lastCheck)

class TeacherScheduleDayDto(val teacher: TeacherDto,
                            lessons: List<LessonDto>, date: LocalDate, lastCheck: LocalDateTime) : ScheduleDayDto(lessons, date, lastCheck)