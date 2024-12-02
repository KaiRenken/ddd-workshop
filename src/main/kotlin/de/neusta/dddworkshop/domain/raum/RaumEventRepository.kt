package de.neusta.dddworkshop.domain.raum

import de.neusta.dddworkshop.common.Repository
import de.neusta.dddworkshop.domain.raum.events.PersonWurdeRaumZugeordnetEvent

@Repository
interface RaumEventRepository {

    fun werfePersonWurdeRaumZugeordnetEvent(personWurdeRaumZugeordnetEvent: PersonWurdeRaumZugeordnetEvent)
}