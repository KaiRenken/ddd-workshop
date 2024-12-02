package de.neusta.dddworkshop.infrastructure.raum.rest.dto

import java.util.UUID

data class ReadRaumDto(
    val id: UUID,
    val raumnummer: String,
    val name: String
)