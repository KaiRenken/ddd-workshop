package de.neusta.dddworkshop.infrastructure.raum.repository

import de.neusta.dddworkshop.domain.raum.Raum
import de.neusta.dddworkshop.domain.raum.RaumRepository
import org.springframework.stereotype.Repository

@Repository
class RaumRepositoryImpl : RaumRepository {

    private val roomList = ArrayList<Raum>()

    override fun existiert(raumnummer: Raum.Nummer) = roomList.any { it.raumnummer == raumnummer }

    override fun legeAn(raum: Raum) {
        roomList.add(raum)
    }
}