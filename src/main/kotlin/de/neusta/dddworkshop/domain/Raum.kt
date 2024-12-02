package de.neusta.dddworkshop.domain

import de.neusta.dddworkshop.common.AggregateRoot
import de.neusta.dddworkshop.common.ValueObject
import java.util.UUID

@AggregateRoot
class Raum(
    val id: Id = Id(),
    val raumnummer: Raumnummer,
    val name: Name
)

@ValueObject
class Id(val value: UUID = UUID.randomUUID())

@ValueObject
class Raumnummer(val value: String) {
    init {
        require(value.length == 4 && value.all { it.isDigit() }) {
            "Die Raumnummer '$value' ist ungültig."
        }
    }
}

@ValueObject
class Name(val value: String) {
    init {
        require(value.isNotBlank() && value.length <= 100) {
            "Der Name '$value' ist ungültig."
        }
    }
}