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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Teacher) return false

        if (name != other.name) return false
        if (url != other.url) return false
        if (university != other.university) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (url?.hashCode() ?: 0)
        result = 31 * result + university.hashCode()
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Teacher(name='$name', url=$url, university=$university, id=$id)"
    }
}