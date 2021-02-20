package edu.put_the_machine.scrapper.model

import javax.persistence.*

@Entity(name = "`group`")
class Group(
    val name: String,
    @ManyToOne
    val university: University
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}