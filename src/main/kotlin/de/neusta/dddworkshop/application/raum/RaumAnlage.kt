package de.neusta.dddworkshop.application.raum

import de.neusta.dddworkshop.common.UseCase
import de.neusta.dddworkshop.domain.raum.Raum
import de.neusta.dddworkshop.domain.raum.RaumRepository

@UseCase
class RaumAnlage(private val raumRepository: RaumRepository) {

    fun legeAn(raumnummer: String, name: String): Ergebnis {
        try {
            val neuerRaum = Raum(
                raumnummer = Raum.Nummer(raumnummer),
                name = Raum.Name(name)
            )

            if (raumRepository.existiert(raumnummer = neuerRaum.raumnummer)) {
                return RaumnummerExistiertSchon(neuerRaum.raumnummer)
            }

            raumRepository.legeAn(neuerRaum)

            return RaumAngelegt(neuerRaum)
        } catch (exception: Exception) {
            return UngueltigerWert(exception.message.orEmpty())
        }
    }

    sealed class Ergebnis

    class RaumAngelegt(val raum: Raum) : Ergebnis()
    class UngueltigerWert(val fehlermeldung: String) : Ergebnis()
    class RaumnummerExistiertSchon(val raumnummer: Raum.Nummer) : Ergebnis()
}