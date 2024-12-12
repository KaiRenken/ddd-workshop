package de.neusta.dddworkshop.infrastructure.person.repository

import de.neusta.dddworkshop.domain.person.Person
import de.neusta.dddworkshop.domain.person.PersonRepository
import org.springframework.stereotype.Repository

@Repository
class PersonRepositoryImpl : PersonRepository {

    private val personList = ArrayList<Person>()

    override fun legeAn(person: Person) {
        personList.add(person)
    }

    override fun existiert(benutzername: Person.Benutzername): Boolean =
        personList.any { it.benutzername == benutzername }

    override fun existiert(id: Person.Id): Boolean =
        personList.any { it.id == id }

    override fun findePerson(id: Person.Id): Person? = personList.firstOrNull { it.id == id }
}