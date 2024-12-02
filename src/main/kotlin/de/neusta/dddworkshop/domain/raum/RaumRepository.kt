package de.neusta.dddworkshop.domain.raum

import de.neusta.dddworkshop.common.Repository

@Repository
interface RaumRepository {

    fun existiert(raumnummer: Raum.Nummer): Boolean

    fun legeAn(raum: Raum)
}