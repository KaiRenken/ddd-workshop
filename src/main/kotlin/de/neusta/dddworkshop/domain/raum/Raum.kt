package de.neusta.dddworkshop.domain.raum

import de.neusta.dddworkshop.common.AggregateRoot
import de.neusta.dddworkshop.common.Entity
import de.neusta.dddworkshop.common.ValueObject
import java.util.UUID

@AggregateRoot
data class Raum(
    val id: Id = Id(),
    val raumnummer: Nummer,
    val name: Name,
    val personen: MutableList<Person> = mutableListOf()
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

    @Entity
    data class Person(
        val name: Name,
        val benutzername: Benutzername,
        val namenszusatz: Namenszusatz?
    ) {

        @ValueObject
        data class Name(val vorname: String, val nachname: String)

        @ValueObject
        data class Benutzername(val value: String)

        @ValueObject
        enum class Namenszusatz {
            VON,
            VAN,
            DE
        }
    }

    fun fuegePersonHinzu(person: Person) {
        this.personen.add(person)
    }
}