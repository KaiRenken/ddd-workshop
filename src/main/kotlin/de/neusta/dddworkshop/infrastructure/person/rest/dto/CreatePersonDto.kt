package de.neusta.dddworkshop.infrastructure.person.rest.dto

data class CreatePersonDto(
    val vorname: String,
    val nachname: String,
    val benutzername: String,
    val namenszusatz: String?
)