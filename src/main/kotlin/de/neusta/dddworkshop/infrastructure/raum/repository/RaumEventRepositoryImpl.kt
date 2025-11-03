package de.neusta.dddworkshop.infrastructure.raum.repository

import de.neusta.dddworkshop.domain.raum.PersonWurdeRaumZugeordnetEvent
import de.neusta.dddworkshop.domain.raum.RaumEventRepository
import org.springframework.stereotype.Repository

@Repository
class RaumEventRepositoryImpl : RaumEventRepository {

    override fun werfePersonWurdeRaumZugeordnetEvent(event: PersonWurdeRaumZugeordnetEvent) {
        println("Dem Raum mit der ID ${event.raumId.value} wurde die Person mit der ID ${event.personId.value} zugeordnet.")
    }
}