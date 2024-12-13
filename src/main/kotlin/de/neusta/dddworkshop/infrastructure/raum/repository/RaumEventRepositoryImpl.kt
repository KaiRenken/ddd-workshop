package de.neusta.dddworkshop.infrastructure.raum.repository

import de.neusta.dddworkshop.domain.raum.PersonWurdeRaumZugeordnetEvent
import de.neusta.dddworkshop.domain.raum.RaumEventRepository
import org.springframework.stereotype.Repository

@Repository
class RaumEventRepositoryImpl : RaumEventRepository {

    override fun werfePersonWurdeRaumZugeordnetEvent(personWurdeRaumZugeordnetEvent: PersonWurdeRaumZugeordnetEvent) {
        println("Person mit ID '${personWurdeRaumZugeordnetEvent.personId.value}' wurde Raum mit ID '${personWurdeRaumZugeordnetEvent.raumId.value}' zugeordnet.")
    }
}