package de.neusta.dddworkshop.application.raum

import de.neusta.dddworkshop.common.UseCase
import de.neusta.dddworkshop.domain.person.Person
import de.neusta.dddworkshop.domain.person.PersonRepository
import de.neusta.dddworkshop.domain.raum.Raum
import de.neusta.dddworkshop.domain.raum.RaumEventRepository
import de.neusta.dddworkshop.domain.raum.RaumRepository
import de.neusta.dddworkshop.domain.raum.events.PersonWurdeRaumZugeordnetEvent

@UseCase
class PersonZuRaumHinzufuegung(
    private val raumRepository: RaumRepository,
    private val raumEventRepository: RaumEventRepository,
    private val personRepository: PersonRepository
) {

    fun fuegePersonHinzu(raumId: Raum.Id, personId: Raum.PersonId): Ergebnis {
        if (!personRepository.existiert(Person.Id(personId.value))) return PersonNichtGefunden(personId)

        if (!raumRepository.existiert(raumId)) return RaumNichtGefunden(raumId)

        raumRepository.fuegePersonHinzu(raumId, personId)
        raumEventRepository.werfePersonWurdeRaumZugeordnetEvent(
            PersonWurdeRaumZugeordnetEvent(
                personId = personId,
                raumId = raumId
            )
        )

        return PersonHinzugefuegt
    }

    sealed class Ergebnis

    data object PersonHinzugefuegt : Ergebnis()
    class PersonNichtGefunden(val personId: Raum.PersonId) : Ergebnis()
    class RaumNichtGefunden(val raumId: Raum.Id) : Ergebnis()
}