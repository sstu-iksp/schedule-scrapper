package edu.put_the_machine.scrapper.model

import javax.persistence.DiscriminatorColumn
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "location_type")
abstract class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

@Entity
@DiscriminatorValue("FIXED_LOCATION")
class FixedLocation(
    val audience: Long,
    val building: Long,
): Location()

@Entity
@DiscriminatorValue("RAW_LOCATION")
class RawLocation(
    val name: String,
): Location()