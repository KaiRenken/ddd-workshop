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
}