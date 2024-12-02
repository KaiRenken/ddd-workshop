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

    fun fuegePersonHinzu(raumId: Raum.Id, personId: Raum.PersonId): Ergebnis {
        if (!personRepository.existiert(Person.Id(personId.value))) return PersonNichtGefunden(personId)

        if (!raumRepository.existiert(raumId)) return RaumNichtGefunden(raumId)

        val raumMitPerson = raumRepository.findeRaumMitPerson(personId)
        if (raumMitPerson != null) return PersonIstSchonAnderemRaumZugeordnet(personId, raumMitPerson.id)

        raumRepository.fuegePersonHinzu(raumId, personId)

        return PersonHinzugefuegt
    }

    sealed class Ergebnis

    data object PersonHinzugefuegt : Ergebnis()
    class PersonNichtGefunden(val personId: Raum.PersonId) : Ergebnis()
    class RaumNichtGefunden(val raumId: Raum.Id) : Ergebnis()
    class PersonIstSchonAnderemRaumZugeordnet(val personId: Raum.PersonId, val raumId: Raum.Id) : Ergebnis()
}