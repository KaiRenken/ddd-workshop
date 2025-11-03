package de.neusta.dddworkshop.domain.raum

import de.neusta.dddworkshop.common.*
import de.neusta.dddworkshop.domain.person.Person
import java.util.*

@AggregateRoot
data class Raum(
    val id: Id = Id(),
    val nummer: Nummer,
    val name: Name,
    val personen: MutableList<Person.Id> = mutableListOf()
) {
    @ValueObject
    data class Id(val value: UUID = UUID.randomUUID())

    @ValueObject
    data class Nummer private constructor(val value: String) {

        companion object {

            operator fun invoke(value: String): ErzeugungsErgebnis<Nummer> {
                val errors = mutableListOf<String>().apply {
                    if (value.length == 4) add("Die Raumnummer '$value' muss vierstellig sein.")
                    if (value.all { it.isDigit() }) add("Die Raumnummer '$value' darf nur Ziffern enthalten.")
                }

                return if (errors.isNotEmpty()) UngueltigeArgumente(errors)
                else Erzeugt(Nummer(value))
            }
        }
    }

    @ValueObject
    data class Name private constructor(val value: String) {

        companion object {

            operator fun invoke(value: String): ErzeugungsErgebnis<Name> {
                val errors = mutableListOf<String>().apply {
                    if (value.isBlank()) add("Der Raumname darf nicht leer sein.")
                    if (value.length > 100) add("Der Raumnname '$value' darf nicht mehr als 100 Zeichen lang sein.")
                }

                return if (errors.isNotEmpty()) UngueltigeArgumente(errors)
                else Erzeugt(Name(value))
            }
        }
    }

    companion object {

        operator fun invoke(
            nummer: String,
            name: String
        ): ErzeugungsErgebnis<Raum> {
            val errors = mutableListOf<String>().apply {
                if (Nummer(nummer) is UngueltigeArgumente) {
                    addAll((Nummer(nummer) as UngueltigeArgumente).fehler)
                }

                if (Name(name) is UngueltigeArgumente) {
                    addAll((Name(name) as UngueltigeArgumente).fehler)
                }
            }

            return if (errors.isNotEmpty()) UngueltigeArgumente(errors)
            else Erzeugt(
                Raum(
                    nummer = (Nummer(nummer) as Erzeugt).value,
                    name = (Name(name) as Erzeugt).value
                )
            )
        }
    }

    fun fuegePersonHinzu(personId: Person.Id) = personen.add(personId)
}