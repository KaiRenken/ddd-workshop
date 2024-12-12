package de.neusta.dddworkshop.application.raum

import de.neusta.dddworkshop.common.UseCase
import de.neusta.dddworkshop.domain.raum.Raum
import de.neusta.dddworkshop.domain.raum.RaumRepository

@UseCase
class PersonHinzufuegung(private val raumRepository: RaumRepository) {

    fun fuegePersonZuRaumHinzu(
        raumId: Raum.Id,
        vorname: String,
        nachname: String,
        benutzername: String,
        namenszusatz: Raum.Person.Namenszusatz?
    ): Ergebnis {
        try {
            val person = Raum.Person(
                name = Raum.Person.Name(vorname = vorname, nachname = nachname),
                namenszusatz = namenszusatz,
                benutzername = Raum.Person.Benutzername(benutzername)
            )

            val potentiellVonPersonBesetzterRaum = raumRepository.findeRaumZuPerson(person.benutzername)

            if (potentiellVonPersonBesetzterRaum != null) {
                return PersonExistiertSchonInAnderemRaum(potentiellVonPersonBesetzterRaum.id)
            }

            val raum = raumRepository.findeMitId(raumId) ?: return RaumExistiertNicht(raumId)

            raum.fuegePersonHinzu(person)

            raumRepository.aktuelisiereRaum(raum)

            return PersonHinzugefuegt(person)
        } catch (exception: Exception) {
            return UngueltigerWert(fehlermeldeung = exception.message.orEmpty())
        }
    }

    sealed class Ergebnis
    class PersonHinzugefuegt(val person: Raum.Person) : Ergebnis()
    class UngueltigerWert(val fehlermeldeung: String) : Ergebnis()
    class PersonExistiertSchonInAnderemRaum(val raumId: Raum.Id) : Ergebnis()
    class RaumExistiertNicht(val raumId: Raum.Id) : Ergebnis()
}