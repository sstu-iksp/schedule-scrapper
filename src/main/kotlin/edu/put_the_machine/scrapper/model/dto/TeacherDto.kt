package edu.put_the_machine.scrapper.model.dto


data class TeacherDto @JvmOverloads constructor(
        val name: String,
        val url: String?,
        val universityId: Long,
        val id: Long? = null
)
