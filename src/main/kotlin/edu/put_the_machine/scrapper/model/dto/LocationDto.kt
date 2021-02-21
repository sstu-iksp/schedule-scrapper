package edu.put_the_machine.scrapper.model.dto

abstract class LocationDto

class FixedLocationDto(val audience: Long, val building: Long): LocationDto()

class RawLocationDto(val name: String): LocationDto()