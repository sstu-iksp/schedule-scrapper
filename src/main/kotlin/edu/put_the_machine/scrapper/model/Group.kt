package edu.put_the_machine.scrapper.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity(name = "UniversityGroup")
class Group(
    val name: String,
    @ManyToOne
    val university: University
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Group) return false

        if (name != other.name) return false
        if (university != other.university) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + university.hashCode()
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Group(name='$name', university=$university, id=$id)"
    }
}