package edu.put_the_machine.scrapper.model

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class ScheduleDay(
    @OneToMany
    val lessons: List<Lesson>,
    val date: LocalDate,
    @ManyToOne
    val group: Group,
    val lastCheck: LocalDateTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}