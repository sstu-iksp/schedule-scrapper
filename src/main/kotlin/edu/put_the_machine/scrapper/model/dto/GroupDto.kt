package edu.put_the_machine.scrapper.model.dto


class GroupDto(
    val name: String,
    val university: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GroupDto) return false

        if (name != other.name) return false
        if (university != other.university) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + university.hashCode()
        return result
    }
}