package edu.put_the_machine.scrapper.model.dto

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "_type_")
@JsonSubTypes(
    JsonSubTypes.Type(value = FixedLocationDto::class, name = "FixedLocationDto"),
    JsonSubTypes.Type(value =  RawLocationDto::class, name = "RawLocationDto"),
)
abstract class LocationDto

class FixedLocationDto(val audience: Int, val building: Int): LocationDto() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FixedLocationDto) return false

        if (audience != other.audience) return false
        if (building != other.building) return false

        return true
    }

    override fun hashCode(): Int {
        var result = audience
        result = 31 * result + building
        return result
    }
}

class RawLocationDto(val name: String): LocationDto() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RawLocationDto) return false

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}