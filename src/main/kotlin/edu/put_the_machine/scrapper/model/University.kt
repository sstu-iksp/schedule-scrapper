package edu.put_the_machine.scrapper.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class University(
    val name: String,
    @JsonIgnore val parserName: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
