package de.neusta.dddworkshop.infrastructure.person.rest.dto

data class CreatePersonDto(
    val name: NameDto,
    val benutzername: String,
    val namenszusatz: String?
) {
    data class NameDto(
        val vorname: String,
        val nachname: String
    )
}