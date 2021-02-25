package edu.put_the_machine.scrapper.model

import java.time.LocalTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Lesson(
    val subject: String,
    val type: String,
    val start: LocalTime,
    //TODO: change 'end' name to 'finish' after ScheduleParser branch merge
    @Column(name = "`end`")
    val end: LocalTime,
    @ManyToOne
    val teacher: Teacher,
    @ManyToOne
    val location: Location
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}