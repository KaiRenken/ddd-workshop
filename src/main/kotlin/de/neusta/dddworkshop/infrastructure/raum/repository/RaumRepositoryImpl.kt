package de.neusta.dddworkshop.infrastructure.raum.repository

import de.neusta.dddworkshop.domain.raum.Raum
import de.neusta.dddworkshop.domain.raum.RaumRepository
import org.springframework.stereotype.Repository

@Repository
class RaumRepositoryImpl : RaumRepository {

    private val raumList = ArrayList<Raum>()

    override fun existiert(nummer: Raum.Nummer) = raumList.any { it.nummer == nummer }

    override fun speichere(raum: Raum) {
        raumList.add(raum)
    }

    override fun bearbeite(raum: Raum) {
        raumList.first { it.id == raum.id }
            .apply {
                raumList.remove(this)
                raumList.add(raum)
            }
    }

    override fun findeMit(raumId: Raum.Id): Raum? = raumList.firstOrNull { it.id == raumId }

    override fun findeMit(benutzername: Raum.Person.Benutzername) =
        raumList.firstOrNull { it.personen.map { person -> person.benutzername }.contains(benutzername) }
}