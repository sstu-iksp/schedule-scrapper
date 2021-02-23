package edu.put_the_machine.scrapper.model.dto

import java.time.LocalTime

class LessonDto(
    val subject: String,
    val type: String,
    val start: LocalTime,
    val end: LocalTime,
    val teacher: TeacherDto?,
    val location: LocationDto

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LessonDto) return false

        if (subject != other.subject) return false
        if (type != other.type) return false
        if (start != other.start) return false
        if (end != other.end) return false
        if (teacher != other.teacher) return false
        if (location != other.location) return false

        return true
    }

    override fun hashCode(): Int {
        var result = subject.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + start.hashCode()
        result = 31 * result + end.hashCode()
        result = 31 * result + (teacher?.hashCode() ?: 0)
        result = 31 * result + location.hashCode()
        return result
    }
}