package de.neusta.dddworkshop.application.person

import de.neusta.dddworkshop.common.Erzeugt
import de.neusta.dddworkshop.common.UseCase
import de.neusta.dddworkshop.domain.person.Person
import de.neusta.dddworkshop.domain.person.PersonRepository

@UseCase
class PersonAnlage(private val personRepository: PersonRepository) {

    fun legeAn(
        vorname: String,
        nachname: String,
        benutzername: String,
        namenszusatz: String?
    ): Ergebnis {
        Person(
            vorname = vorname,
            nachname = nachname,
            benutzername = benutzername,
            namenszusatz = namenszusatz
        )
            .apply {
                when (this) {
                    is de.neusta.dddworkshop.common.UngueltigeArgumente -> return UngueltigeArgumente(this.fehler)

                    is Erzeugt -> {
                        if (personRepository.existiertMit(benutzername = this.value.benutzername)) {
                            return BenutzernameSchonVergeben
                        }

                        personRepository.speichere(this.value)

                        return PersonAngelegt(this.value)
                    }
                }
            }
    }

    sealed class Ergebnis

    class PersonAngelegt(val person: Person) : Ergebnis()
    class UngueltigeArgumente(val fehler: List<String>) : Ergebnis()
    object BenutzernameSchonVergeben : Ergebnis()
}