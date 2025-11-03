package de.neusta.dddworkshop.application.raum

import de.neusta.dddworkshop.common.UseCase
import de.neusta.dddworkshop.domain.person.Person
import de.neusta.dddworkshop.domain.person.PersonRepository
import de.neusta.dddworkshop.domain.raum.Raum
import de.neusta.dddworkshop.domain.raum.RaumRepository

@UseCase
class PersonHinzufuegung(
    private val personRepository: PersonRepository,
    private val raumRepository: RaumRepository
) {

    fun fuegePersonZuRaumHinzu(
        personId: Person.Id,
        raumId: Raum.Id
    ): Ergebnis {
        if (!personRepository.existiertMit(personId)) return PersonExistiertNicht

        val raum = raumRepository.findeMit(raumId) ?: return RaumExistiertNicht

        raum.fuegePersonHinzu(personId)

        raumRepository.bearbeite(raum)

        return PersonHinzugefuegt
    }

    sealed class Ergebnis
    object PersonHinzugefuegt : Ergebnis()
    object PersonExistiertNicht : Ergebnis()
    object RaumExistiertNicht : Ergebnis()
}