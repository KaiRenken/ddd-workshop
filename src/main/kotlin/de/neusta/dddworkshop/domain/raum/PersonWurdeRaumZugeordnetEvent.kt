package de.neusta.dddworkshop.domain.raum

import de.neusta.dddworkshop.common.DomainEvent
import de.neusta.dddworkshop.domain.person.Person

@DomainEvent
data class PersonWurdeRaumZugeordnetEvent(
    val personId: Person.Id,
    val raumId: Raum.Id
)