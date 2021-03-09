package edu.put_the_machine.scrapper.model.dto


data class TeacherDto @JvmOverloads constructor(
        val name: String,
        val url: String?,
        val university: UniversityDto,
        val id: Long? = null
)
