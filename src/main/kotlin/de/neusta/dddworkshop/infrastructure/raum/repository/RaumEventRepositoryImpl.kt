package de.neusta.dddworkshop.infrastructure.raum.repository

import de.neusta.dddworkshop.domain.raum.RaumEventRepository
import de.neusta.dddworkshop.domain.raum.events.PersonWurdeRaumZugeordnetEvent
import org.springframework.stereotype.Repository

@Repository
class RaumEventRepositoryImpl : RaumEventRepository {

    override fun werfePersonWurdeRaumZugeordnetEvent(personWurdeRaumZugeordnetEvent: PersonWurdeRaumZugeordnetEvent) {
        println("Person '${personWurdeRaumZugeordnetEvent.personId.value}' wurde Raum '${personWurdeRaumZugeordnetEvent.raumId.value}' zugeordnet.")
    }
}