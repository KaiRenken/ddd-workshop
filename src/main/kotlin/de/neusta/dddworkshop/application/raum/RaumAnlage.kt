package de.neusta.dddworkshop.application.raum

import de.neusta.dddworkshop.common.Erzeugt
import de.neusta.dddworkshop.common.UseCase
import de.neusta.dddworkshop.domain.raum.Raum
import de.neusta.dddworkshop.domain.raum.RaumRepository

@UseCase
class RaumAnlage(private val raumRepository: RaumRepository) {

    fun legeAn(
        nummer: String,
        name: String
    ): Ergebnis {
        Raum(
            nummer = nummer,
            name = name
        )
            .apply {
                when (this) {
                    is de.neusta.dddworkshop.common.UngueltigeArgumente -> return UngueltigeArgumente(this.fehler)

                    is Erzeugt -> {
                        if (raumRepository.existiert(nummer = this.value.nummer)) {
                            return RaumnummerExistiertSchon
                        }

                        raumRepository.speichere(this.value)

                        return RaumAngelegt(this.value)
                    }
                }
            }
    }

    sealed class Ergebnis

    class RaumAngelegt(val raum: Raum) : Ergebnis()
    class UngueltigeArgumente(val fehler: List<String>) : Ergebnis()
    object RaumnummerExistiertSchon : Ergebnis()
}