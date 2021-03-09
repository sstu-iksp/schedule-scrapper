package edu.put_the_machine.scrapper.model.dto


class TeacherDto(
    val name: String,
    val url: String?

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TeacherDto) return false

        if (name != other.name) return false
        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (url?.hashCode() ?: 0)
        return result
    }
}