package de.neusta.dddworkshop.domain.raum.events

import de.neusta.dddworkshop.common.DomainEvent
import de.neusta.dddworkshop.domain.raum.Raum

@DomainEvent
data class PersonWurdeRaumZugeordnetEvent(
    val personId: Raum.PersonId,
    val raumId: Raum.Id
)