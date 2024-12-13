package de.neusta.dddworkshop.domain.raum

import de.neusta.dddworkshop.common.Repository

@Repository
interface RaumRepository {

    fun existiert(raumnummer: Raum.Nummer): Boolean

    fun speichere(raum: Raum)

    fun existiert(id: Raum.Id): Boolean

    fun findeRaumMitPerson(personId: Raum.PersonId): Raum?

    fun fuegePersonHinzu(raumId: Raum.Id, personId: Raum.PersonId)

    fun findeMit(id: Raum.Id): Raum?
}