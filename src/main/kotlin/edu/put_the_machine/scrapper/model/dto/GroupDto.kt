package edu.put_the_machine.scrapper.model.dto


data class GroupDto @JvmOverloads constructor(
    val name: String,
    val university: UniversityDto,
    val id: Long? = null
)