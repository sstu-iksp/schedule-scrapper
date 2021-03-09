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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is University) return false

        if (name != other.name) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "University(name='$name', id=$id)"
    }
}
