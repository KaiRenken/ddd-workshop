package de.neusta.dddworkshop.application.person

import de.neusta.dddworkshop.common.UseCase
import de.neusta.dddworkshop.domain.person.Person
import de.neusta.dddworkshop.domain.person.PersonRepository

@UseCase
class PersonAnlage(private val personRepository: PersonRepository) {

    fun legeAn(vorname: String, nachname: String, namenszusatz: String?, benutzername: String): Ergebnis {
        try {
            val neuePerson = Person(
                name = Person.Name(
                    vorname = vorname,
                    nachname = nachname
                ),
                benutzername = Person.Benutzername(benutzername),
                namenszusatz = if (namenszusatz != null) Person.Namenszusatz.valueOf(namenszusatz.uppercase()) else null
            )

            if (personRepository.existiert(benutzername = neuePerson.benutzername)) {
                return BenutzernameExistiertSchon(neuePerson.benutzername)
            }

            personRepository.legeAn(neuePerson)

            return PersonAngelegt(neuePerson)
        } catch (exception: Exception) {
            return UngueltigerWert(exception.message.orEmpty())
        }
    }

    sealed class Ergebnis

    class PersonAngelegt(val person: Person) : Ergebnis()
    class UngueltigerWert(val fehlermeldung: String) : Ergebnis()
    class BenutzernameExistiertSchon(val benutzername: Person.Benutzername) : Ergebnis()
}