package de.neusta.dddworkshop.domain.raum

import de.neusta.dddworkshop.common.AggregateRoot
import de.neusta.dddworkshop.common.ValueObject
import java.util.UUID

@AggregateRoot
data class Raum(
    val id: Id = Id(),
    val raumnummer: Nummer,
    val name: Name,
    val personIds: MutableList<PersonId> = ArrayList()
) {
    @ValueObject
    data class Id(val value: UUID = UUID.randomUUID())

    @ValueObject
    data class Nummer(val value: String) {
        init {
            require(value.length == 4 && value.all { it.isDigit() }) {
                "Die Raumnummer '$value' ist ungültig."
            }
        }
    }

    @ValueObject
    data class Name(val value: String) {
        init {
            require(value.isNotBlank() && value.length <= 100) {
                "Der Name '$value' ist ungültig."
            }
        }
    }

    @ValueObject
    data class PersonId(val value: UUID)

    fun fuegePersonHinzu(personId: PersonId) {
        personIds.add(personId)
    }
}