package de.neusta.dddworkshop.domain.person

import java.util.UUID

data class Person(
    val id: Id = Id(),
    val name: Name,
    val benutzername: Benutzername,
    val namenszusatz: Namenszusatz?
) {
    data class Id(val value: UUID = UUID.randomUUID())

    data class Name(val vorname: String, val nachname: String) {
        init {
            require(vorname.isNotBlank())
            require(nachname.isNotBlank())
        }
    }

    data class Benutzername(val value: String) {
        init {
            require(value.isNotBlank())
        }
    }

    enum class Namenszusatz {
        VON,
        VAN,
        DE
    }

    fun erzeugeKurzschreibweise(): String =
        "${name.vorname} ${namenszusatz?.name ?: ""} ${name.nachname} (${benutzername.value})"
}