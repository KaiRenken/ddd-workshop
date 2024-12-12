package de.neusta.dddworkshop.domain.person

import de.neusta.dddworkshop.common.AggregateRoot
import de.neusta.dddworkshop.common.ValueObject
import java.util.UUID

@AggregateRoot
data class Person(
    val id: Id = Id(),
    val name: Name,
    val benutzername: Benutzername,
    val namenszusatz: Namenszusatz?
) {
    @ValueObject
    data class Id(val value: UUID = UUID.randomUUID())

    @ValueObject
    data class Name(val vorname: String, val nachname: String) {
        init {
            require(vorname.isNotBlank()) {
                "Der Vorname '$vorname' ist ungültig."
            }
            require(nachname.isNotBlank()) {
                "Der Nachname '$nachname' ist ungültig."
            }
        }
    }

    @ValueObject
    data class Benutzername(val value: String) {
        init {
            require(value.isNotBlank()) {
                "Der Benutzername ist leer."
            }
        }
    }

    @ValueObject
    enum class Namenszusatz {
        VON,
        VAN,
        DE
    }

    fun erzeugeKurzschreibweise(): String =
        "${name.vorname} ${namenszusatz?.name?.lowercase()?.plus(" ") ?: ""}${name.nachname} (${benutzername.value})"
}