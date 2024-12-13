package de.neusta.dddworkshop.application.raum

import de.neusta.dddworkshop.application.raum.dto.RaumMitPersonenDto
import de.neusta.dddworkshop.common.UseCase
import de.neusta.dddworkshop.domain.person.Person
import de.neusta.dddworkshop.domain.person.PersonRepository
import de.neusta.dddworkshop.domain.raum.Raum
import de.neusta.dddworkshop.domain.raum.RaumRepository

@UseCase
class RaumSuche(private val raumRepository: RaumRepository, private val personRepository: PersonRepository) {

    fun sucheRaum(id: Raum.Id): Ergebnis {
        val raum = raumRepository.findeMit(id) ?: return RaumExistiertNicht(id)

        val personen =
            raum.personIds.mapNotNull { personRepository.findePerson(Person.Id(it.value))?.erzeugeKurzschreibweise() }

        return RaumGefunden(
            RaumMitPersonenDto(
                raum = raum,
                personen = personen
            )
        )
    }

    sealed class Ergebnis
    class RaumGefunden(val raumMitPersonen: RaumMitPersonenDto) : Ergebnis()
    class RaumExistiertNicht(val raumId: Raum.Id) : Ergebnis()
}