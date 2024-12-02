package de.neusta.dddworkshop.infrastructure.person.rest.dto

import java.util.UUID

data class ReadPersonDto(
    val id: UUID,
    val vorname: String,
    val nachname: String,
    val benutzername: String,
    val namenszusatz: String?
)