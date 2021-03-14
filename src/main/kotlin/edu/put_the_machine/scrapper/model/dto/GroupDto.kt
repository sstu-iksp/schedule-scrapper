package edu.put_the_machine.scrapper.model.dto


data class GroupDto @JvmOverloads constructor(
    val name: String,
    val universityId: Long,
    val id: Long? = null
)