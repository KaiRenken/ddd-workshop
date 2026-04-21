package de.neusta.dddworkshop.application.raum

import de.neusta.dddworkshop.common.Erzeugt
import de.neusta.dddworkshop.common.UseCase
import de.neusta.dddworkshop.domain.raum.Raum
import de.neusta.dddworkshop.domain.raum.RaumRepository

@UseCase
class PersonZuordnung(
    private val raumRepository: RaumRepository
) {

    fun ordnePersonRaumZu(
        vorname: String,
        nachname: String,
        namenszusatz: String?,
        benutzerame: String,
        raumId: Raum.Id
    ): Ergebnis {
        val raum = raumRepository.findeMit(raumId) ?: return RaumExistiertNicht

        val person = Raum.Person(
            vorname = vorname,
            nachname = nachname,
            namenszusatz = namenszusatz,
            benutzername = benutzerame
        )

        when (person) {
            is Erzeugt -> {
                raumRepository.findeMit(person.value.benutzername)?.let {
                    return PersonSchonInAnderemRaum(it.id)
                }

                raum.fuegePersonHinzu(person.value)

                raumRepository.bearbeite(raum)
            }

            is de.neusta.dddworkshop.common.UngueltigeArgumente -> UngueltigeArgumente(person.fehler)
        }

        return PersonHinzugefuegt
    }

    sealed class Ergebnis
    object PersonHinzugefuegt : Ergebnis()
    class PersonSchonInAnderemRaum(val raumId: Raum.Id) : Ergebnis()
    object RaumExistiertNicht : Ergebnis()
    class UngueltigeArgumente(val fehler: List<String>) : Ergebnis()
}