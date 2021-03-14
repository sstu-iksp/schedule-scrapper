package edu.put_the_machine.scrapper.model

import javax.persistence.*

@Entity
data class University(
    @Column(unique = true)
    val name: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
