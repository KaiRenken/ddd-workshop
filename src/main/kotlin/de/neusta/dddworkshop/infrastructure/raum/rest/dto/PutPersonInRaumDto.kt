package de.neusta.dddworkshop.infrastructure.raum.rest.dto

data class PutPersonInRaumDto(
    val vorname: String,
    val nachname: String,
    val benutzername: String,
    val namenszusatz: String?
)