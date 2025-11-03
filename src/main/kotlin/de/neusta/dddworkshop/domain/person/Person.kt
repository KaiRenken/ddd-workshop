package de.neusta.dddworkshop.domain.person

import de.neusta.dddworkshop.common.*
import java.util.*

@AggregateRoot
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