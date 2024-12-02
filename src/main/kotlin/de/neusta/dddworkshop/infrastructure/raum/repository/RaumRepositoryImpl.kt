package de.neusta.dddworkshop.infrastructure.raum.repository

import de.neusta.dddworkshop.domain.raum.Raum
import de.neusta.dddworkshop.domain.raum.RaumRepository
import org.springframework.stereotype.Repository

@Repository
class RaumRepositoryImpl : RaumRepository {

    private val raumList = ArrayList<Raum>()

    override fun existiert(raumnummer: Raum.Nummer) = raumList.any { it.raumnummer == raumnummer }

    override fun existiert(id: Raum.Id): Boolean = raumList.any { it.id == id }

    override fun legeAn(raum: Raum) {
        raumList.add(raum)
    }

    override fun findeRaum(id: Raum.Id): Raum? = raumList.firstOrNull { it.id == id }

    override fun findeRaumMitPerson(personId: Raum.PersonId): Raum? =
        raumList.firstOrNull { it.personIds.contains(personId) }

    override fun fuegePersonHinzu(raumId: Raum.Id, personId: Raum.PersonId) {
        raumList.first { it.id == raumId }.fuegePersonHinzu(personId)
    }
}