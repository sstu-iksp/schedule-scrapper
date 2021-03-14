package edu.put_the_machine.scrapper.extensions

import javax.persistence.EntityManager

inline fun <reified T> EntityManager.getReference(primaryKey: Any): T =
    this.getReference(T::class.java, primaryKey)

inline fun <reified T> EntityManager.getReferenceOfNullable(primaryKey: Any?): T? =
    primaryKey?.let { this.getReference(T::class.java, primaryKey) }