package de.neusta.dddworkshop.application.raum

import de.neusta.dddworkshop.common.UseCase
import de.neusta.dddworkshop.domain.person.Person
import de.neusta.dddworkshop.domain.person.PersonRepository
import de.neusta.dddworkshop.domain.raum.PersonWurdeRaumZugeordnetEvent
import de.neusta.dddworkshop.domain.raum.Raum
import de.neusta.dddworkshop.domain.raum.RaumEventRepository
import de.neusta.dddworkshop.domain.raum.RaumRepository

@UseCase
class PersonZuRaumHinzufuegung(
    private val raumRepository: RaumRepository,
    private val personRepository: PersonRepository,
    private val raumEventRepository: RaumEventRepository
) {

    fun fuegePersonHinzu(raumId: Raum.Id, personId: Person.Id): Ergebnis {
        if (!personRepository.existiert(personId)) return PersonNichtGefunden(personId)

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
    class PersonNichtGefunden(val personId: Person.Id) : Ergebnis()
    class RaumNichtGefunden(val raumId: Raum.Id) : Ergebnis()
}