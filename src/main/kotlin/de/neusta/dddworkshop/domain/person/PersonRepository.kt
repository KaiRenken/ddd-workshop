package de.neusta.dddworkshop.domain.person

import de.neusta.dddworkshop.common.Repository

@Repository
interface PersonRepository {

    fun legeAn(person: Person)

    fun existiert(benutzername: Person.Benutzername): Boolean

    fun existiert(id: Person.Id): Boolean

    fun findePerson(id: Person.Id): Person?
}