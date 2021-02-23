package edu.put_the_machine.scrapper.model

import javax.persistence.*

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(
        columnNames = arrayOf("name")
    )]
)
class University(
    val name: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
