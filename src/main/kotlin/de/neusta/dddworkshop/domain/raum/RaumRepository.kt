package de.neusta.dddworkshop.domain.raum

import de.neusta.dddworkshop.common.Repository

@Repository
interface RaumRepository {

    fun existiert(nummer: Raum.Nummer): Boolean

    fun speichere(raum: Raum)

    fun bearbeite(raum: Raum)

    fun findeMit(raumId: Raum.Id): Raum?

    fun findeMit(benutzername: Raum.Person.Benutzername): Raum?
}