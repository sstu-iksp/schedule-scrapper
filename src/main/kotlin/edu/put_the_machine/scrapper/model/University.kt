package edu.put_the_machine.scrapper.model

import javax.persistence.*

@Entity
data class University(
    @Column(unique = true)
    val name: String,
) {
    @Id
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = GENERATOR_NAME)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME)
    val id: Long? = null

    companion object {
        const val GENERATOR_NAME = "university_id_generator"
    }
}
