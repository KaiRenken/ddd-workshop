package de.neusta.dddworkshop.infrastructure.raum.repository

import de.neusta.dddworkshop.domain.person.Person
import de.neusta.dddworkshop.domain.raum.Raum
import de.neusta.dddworkshop.domain.raum.RaumRepository
import org.springframework.stereotype.Repository

@Repository
class RaumRepositoryImpl : RaumRepository {

    private val raumList = ArrayList<Raum>()

    override fun existiert(raumnummer: Raum.Nummer) = raumList.any { it.raumnummer == raumnummer }

    override fun speichere(raum: Raum) {
        raumList.add(raum)
    }

    override fun existiert(id: Raum.Id): Boolean = raumList.any { it.id == id }

    override fun findeMit(id: Raum.Id): Raum? = raumList.firstOrNull { it.id == id }

    override fun findeRaumMitPerson(personId: Person.Id): Raum? =
        raumList.firstOrNull { it.personIds.contains(personId) }

    override fun fuegePersonHinzu(raumId: Raum.Id, personId: Person.Id) {
        raumList.first { it.id == raumId }.fuegePersonHinzu(personId)
    }
}