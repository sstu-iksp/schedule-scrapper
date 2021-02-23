package edu.put_the_machine.scrapper.model.dto

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "_type_")
@JsonSubTypes(
    JsonSubTypes.Type(value = FixedLocationDto::class, name = "FixedLocationDto"),
    JsonSubTypes.Type(value =  RawLocationDto::class, name = "RawLocationDto"),
)
abstract class LocationDto

class FixedLocationDto(val audience: Int, val building: Int): LocationDto()

class RawLocationDto(val name: String): LocationDto()