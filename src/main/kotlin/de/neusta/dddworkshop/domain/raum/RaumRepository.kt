package de.neusta.dddworkshop.domain.raum

import de.neusta.dddworkshop.common.Repository

@Repository
interface RaumRepository {

    fun existiert(raumnummer: Raum.Nummer): Boolean

    fun existiert(id: Raum.Id): Boolean

    fun legeAn(raum: Raum)

    fun findeRaum(id: Raum.Id): Raum?

    fun findeRaumMitPerson(personId: Raum.PersonId): Raum?

    fun fuegePersonHinzu(raumId: Raum.Id, personId: Raum.PersonId)
}