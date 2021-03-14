package edu.put_the_machine.scrapper.model

import javax.persistence.*

@Entity
data class Teacher(
    val name: String,
    val url: String?,
    @ManyToOne
    val university: University
) {
    @Id
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = GENERATOR_NAME)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME)
    val id: Long? = null

    companion object {
        const val GENERATOR_NAME = "teacher_id_generator"
    }
}