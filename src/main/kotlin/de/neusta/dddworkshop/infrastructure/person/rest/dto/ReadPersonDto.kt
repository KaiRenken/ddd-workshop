package de.neusta.dddworkshop.infrastructure.person.rest.dto

import java.util.*

data class ReadPersonDto(
    val id: UUID,
    val name: NameDto,
    val benutzername: String,
    val namenszusatz: String?
) {
    data class NameDto(
        val vorname: String,
        val nachname: String
    )
}