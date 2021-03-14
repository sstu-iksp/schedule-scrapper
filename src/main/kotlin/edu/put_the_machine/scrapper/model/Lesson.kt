package edu.put_the_machine.scrapper.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Lesson(
    val subject: String,
    val type: String,
    val date: LocalDate,
    val start: LocalTime,
    val finish: LocalTime,
    @ManyToOne
    val group: Group,
    @ManyToOne
    val teacher: Teacher?,
    val location: String,
    val lastCheck: LocalDateTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}