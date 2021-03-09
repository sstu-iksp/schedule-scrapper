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
    @Column(name = "`end`")
    val end: LocalTime,
    @ManyToOne
    val teacher: Teacher?,
    val location: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}