package de.neusta.dddworkshop.application.raum.dto

import de.neusta.dddworkshop.domain.raum.Raum

data class RaumAbfrageDto(
    val raum: Raum,
    val personen: List<String>
)