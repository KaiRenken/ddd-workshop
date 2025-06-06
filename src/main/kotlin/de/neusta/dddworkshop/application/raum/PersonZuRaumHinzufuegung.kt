package de.neusta.dddworkshop.application.raum

import de.neusta.dddworkshop.common.UseCase
import de.neusta.dddworkshop.domain.person.Person
import de.neusta.dddworkshop.domain.person.PersonRepository
import de.neusta.dddworkshop.domain.raum.Raum
import de.neusta.dddworkshop.domain.raum.RaumRepository

@UseCase
class PersonZuRaumHinzufuegung(
    private val raumRepository: RaumRepository,
    private val personRepository: PersonRepository
) {

    fun fuegePersonHinzu(raumId: Raum.Id, personId: Person.Id): Ergebnis {
        if (!personRepository.existiert(personId)) return PersonNichtGefunden(personId)

        val raumMitPerson = raumRepository.findeRaumMitPerson(personId)
        if (raumMitPerson != null) return PersonIstSchonAnderemRaumZugeordnet(personId, raumMitPerson.id)

        val raumZumPersonHinzufuegen = raumRepository.findeMit(raumId) ?: return RaumNichtGefunden(raumId)

        raumZumPersonHinzufuegen.fuegePersonHinzu(personId)

        raumRepository.speichere(raumZumPersonHinzufuegen)

        return PersonHinzugefuegt
    }

    sealed class Ergebnis

    data object PersonHinzugefuegt : Ergebnis()
    class PersonNichtGefunden(val personId: Person.Id) : Ergebnis()
    class RaumNichtGefunden(val raumId: Raum.Id) : Ergebnis()
    class PersonIstSchonAnderemRaumZugeordnet(val personId: Person.Id, val raumId: Raum.Id) : Ergebnis()
}