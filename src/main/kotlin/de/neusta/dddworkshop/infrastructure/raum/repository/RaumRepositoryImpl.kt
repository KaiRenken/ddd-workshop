package de.neusta.dddworkshop.infrastructure.raum.repository

import de.neusta.dddworkshop.domain.raum.Raum
import de.neusta.dddworkshop.domain.raum.RaumRepository
import org.springframework.stereotype.Repository

@Repository
class RaumRepositoryImpl : RaumRepository {

    private val raumList = ArrayList<Raum>()

    override fun existiert(raumnummer: Raum.Nummer) = raumList.any { it.raumnummer == raumnummer }

    override fun legeAn(raum: Raum) {
        raumList.add(raum)
    }

    override fun findeRaum(id: Raum.Id): Raum? = raumList.firstOrNull { it.id == id }

    override fun findeRaumZuPerson(benutzername: Raum.Person.Benutzername): Raum? {
        TODO("Not yet implemented")
    }

    override fun findeMitId(raumId: Raum.Id): Raum? {
        TODO("Not yet implemented")
    }

    override fun aktuelisiereRaum(raum: Raum) {
        TODO("Not yet implemented")
    }
}