package de.neusta.dddworkshop.domain.person

import de.neusta.dddworkshop.common.Repository

@Repository
interface PersonRepository {

    fun speichere(person: Person)

    fun existiertMit(benutzername: Person.Benutzername): Boolean

    fun existiertMit(id: Person.Id): Boolean
}