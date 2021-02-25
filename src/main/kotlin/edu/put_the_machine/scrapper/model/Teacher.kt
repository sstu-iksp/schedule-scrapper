package edu.put_the_machine.scrapper.model

import javax.persistence.*

@Entity
class Teacher(
    val name: String,
    val url: String?,
    @ManyToOne
    val university: University
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}