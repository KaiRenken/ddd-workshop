package de.neusta.dddworkshop.domain.raum

import de.neusta.dddworkshop.common.Repository
import de.neusta.dddworkshop.domain.person.Person

@Repository
interface RaumRepository {

    fun existiert(nummer: Raum.Nummer): Boolean

    fun speichere(raum: Raum)

    fun findeMit(raumId: Raum.Id): Raum?

    fun findeMit(personId: Person.Id): Raum?
}