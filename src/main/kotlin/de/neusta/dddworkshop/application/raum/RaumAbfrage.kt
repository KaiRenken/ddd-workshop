package de.neusta.dddworkshop.application.raum

import de.neusta.dddworkshop.application.raum.dto.RaumAbfrageDto
import de.neusta.dddworkshop.common.UseCase
import de.neusta.dddworkshop.domain.raum.Raum
import de.neusta.dddworkshop.domain.raum.RaumRepository

@UseCase
class RaumAbfrage(
    private val raumRepository: RaumRepository
) {

    fun frageRaumAb(id: Raum.Id): Ergebnis = raumRepository
        .findeMit(id)
        ?.let {
            RaumGefunden(
                RaumAbfrageDto(
                    raum = it,
                    personen = it.personen.map { person ->
                        person.erzeugeKurzschreibweise()
                    }
                )
            )
        }
        ?: RaumNichtGefunden

    sealed class Ergebnis
    class RaumGefunden(val raum: RaumAbfrageDto) : Ergebnis()
    object RaumNichtGefunden : Ergebnis()
}