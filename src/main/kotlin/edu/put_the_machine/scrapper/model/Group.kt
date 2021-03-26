package edu.put_the_machine.scrapper.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "`group`")
data class Group(
    val name: String,
    @ManyToOne
    val university: University
) {
    @Id
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = GENERATOR_NAME)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME)
    val id: Long? = null

    companion object {
        const val GENERATOR_NAME = "group_id_generator"
    }
}