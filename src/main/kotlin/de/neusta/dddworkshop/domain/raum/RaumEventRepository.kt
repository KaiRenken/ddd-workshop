package de.neusta.dddworkshop.domain.raum

import de.neusta.dddworkshop.common.Repository

@Repository
interface RaumEventRepository {

    fun werfePersonWurdeRaumZugeordnetEvent(personWurdeRaumZugeordnetEvent: PersonWurdeRaumZugeordnetEvent)
}