package de.neusta.dddworkshop.domain.raum

import de.neusta.dddworkshop.common.*
import java.util.*

@AggregateRoot
data class Raum(
    val id: Id = Id(),
    val nummer: Nummer,
    val name: Name,
    val personen: MutableList<Person> = mutableListOf()
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

    @Entity
    data class Person(
        val id: Id = Id(),
        val name: Name,
        val benutzername: Benutzername,
        val namenszusatz: Namenszusatz?
    ) {

        @ValueObject
        data class Id(val value: UUID = UUID.randomUUID())

        @ValueObject
        data class Name private constructor(val vorname: String, val nachname: String) {

            companion object {

                operator fun invoke(vorname: String, nachname: String): ErzeugungsErgebnis<Name> {
                    val errors = mutableListOf<String>().apply {
                        if (vorname.isBlank()) add("Der Vorname darf nicht leer sein.")
                        if (nachname.isBlank()) add("Der Nachname darf nicht leer sein.")
                        if (vorname.length > 100) add("Der Vorname '$vorname' darf nicht mehr als 100 Zeichen lang sein.")
                        if (nachname.length > 100) add("Der Nachname '$nachname' darf nicht mehr als 100 Zeichen lang sein.")
                    }

                    return if (errors.isNotEmpty()) UngueltigeArgumente(errors)
                    else Erzeugt(Name(vorname = vorname, nachname = nachname))
                }
            }
        }

        @ValueObject
        data class Benutzername private constructor(val value: String) {

            companion object {

                operator fun invoke(value: String): ErzeugungsErgebnis<Benutzername> {
                    val errors = mutableListOf<String>().apply {
                        if (value.length > 100) add("Der Benutzername '$value' darf nicht mehr als 100 Zeichen lang sein.")
                    }

                    return if (errors.isNotEmpty()) UngueltigeArgumente(errors)
                    else Erzeugt(Benutzername(value))
                }
            }
        }

        @ValueObject
        enum class Namenszusatz(val value: String) {
            VON("von"),
            VAN("van"),
            DE("de")
        }

        companion object {

            operator fun invoke(
                vorname: String,
                nachname: String,
                benutzername: String,
                namenszusatz: String?
            ): ErzeugungsErgebnis<Person> {
                val errors = mutableListOf<String>().apply {
                    if (Name(vorname = vorname, nachname = nachname) is UngueltigeArgumente) {
                        addAll((Name(vorname = vorname, nachname = nachname) as UngueltigeArgumente).fehler)
                    }

                    if (Benutzername(benutzername) is UngueltigeArgumente) {
                        addAll((Benutzername(benutzername) as UngueltigeArgumente).fehler)
                    }

                    namenszusatz?.let {
                        try {
                            Namenszusatz.valueOf(namenszusatz)
                        } catch (_: Exception) {
                            add("Ungültiger Namenszusatz '$namenszusatz'.")
                        }
                    }
                }

                return if (errors.isNotEmpty()) UngueltigeArgumente(errors)
                else Erzeugt(
                    Person(
                        name = (Name(vorname = vorname, nachname = nachname) as Erzeugt).value,
                        benutzername = (Benutzername(benutzername) as Erzeugt).value,
                        namenszusatz = namenszusatz?.let { Namenszusatz.valueOf(it) }
                    )
                )
            }
        }

        fun erzeugeKurzschreibweise(): String =
            "${name.vorname} ${namenszusatz?.value ?: ""} ${name.nachname} (${benutzername.value})"
    }

    fun fuegePersonHinzu(person: Person) {
        personen.apply {
            if (personen.map { it.id }.contains(person.id)) return

            add(person)
        }
    }
}