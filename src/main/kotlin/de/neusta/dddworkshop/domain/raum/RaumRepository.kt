package de.neusta.dddworkshop.domain.raum

import de.neusta.dddworkshop.common.Repository
import de.neusta.dddworkshop.domain.person.Person

@Repository
interface RaumRepository {

    fun existiert(raumnummer: Raum.Nummer): Boolean

    fun speichere(raum: Raum)

    fun existiert(id: Raum.Id): Boolean

    fun findeRaumMitPerson(personId: Person.Id): Raum?

    fun fuegePersonHinzu(raumId: Raum.Id, personId: Person.Id)

    fun findeMit(id: Raum.Id): Raum?
}