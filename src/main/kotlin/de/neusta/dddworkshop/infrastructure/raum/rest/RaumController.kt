package de.neusta.dddworkshop.infrastructure.raum.rest

import de.neusta.dddworkshop.application.raum.PersonZuRaumHinzufuegung
import de.neusta.dddworkshop.application.raum.RaumAnlage
import de.neusta.dddworkshop.application.raum.RaumSuche
import de.neusta.dddworkshop.domain.person.Person
import de.neusta.dddworkshop.domain.raum.Raum
import de.neusta.dddworkshop.infrastructure.common.ErrorResponseDto
import de.neusta.dddworkshop.infrastructure.raum.rest.dto.AddPersonDto
import de.neusta.dddworkshop.infrastructure.raum.rest.dto.CreateRaumDto
import de.neusta.dddworkshop.infrastructure.raum.rest.dto.ReadRaumDto
import de.neusta.dddworkshop.infrastructure.raum.rest.dto.ReadRaumMitPersonenDto
import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RaumController(
    private val raumAnlage: RaumAnlage,
    private val raumSuche: RaumSuche,
    private val personZuRaumHinzufuegung: PersonZuRaumHinzufuegung
) {

    @PostMapping(value = ["/api/room"], consumes = ["application/json"], produces = ["application/json"])
    fun postRaum(@RequestBody createRaumDto: CreateRaumDto): ResponseEntity<Any> {
        val anlageErgebnis = raumAnlage.legeAn(raumnummer = createRaumDto.raumnummer, name = createRaumDto.name)

        return when (anlageErgebnis) {
            is RaumAnlage.RaumAngelegt -> ResponseEntity.ok(
                ReadRaumDto(
                    id = anlageErgebnis.raum.id.value,
                    raumnummer = anlageErgebnis.raum.raumnummer.value,
                    name = anlageErgebnis.raum.name.value
                )
            )

            is RaumAnlage.RaumnummerExistiertSchon -> ResponseEntity.badRequest()
                .body(ErrorResponseDto("Die Raumnummer ${anlageErgebnis.raumnummer.value} existiert schon."))

            is RaumAnlage.UngueltigerWert -> ResponseEntity.badRequest()
                .body("Ungueliger Wert: '${anlageErgebnis.fehlermeldung}'")
        }
    }

    @GetMapping(value = ["/api/room/{id}"], produces = ["application/json"])
    fun getRaum(@PathVariable id: UUID): ResponseEntity<Any> {
        return when (val suchErgebnis = raumSuche.sucheRaum(Raum.Id(id))) {
            is RaumSuche.RaumExistiertNicht -> ResponseEntity(
                ErrorResponseDto("Raum mit id '${suchErgebnis.raumId.value}' existiert nicht"),
                HttpStatus.NOT_FOUND
            )

            is RaumSuche.RaumGefunden -> ResponseEntity.ok(
                ReadRaumMitPersonenDto(
                    id = suchErgebnis.raumMitPersonen.raum.id.value,
                    raumnummer = suchErgebnis.raumMitPersonen.raum.raumnummer.value,
                    name = suchErgebnis.raumMitPersonen.raum.name.value,
                    personen = suchErgebnis.raumMitPersonen.personen
                )
            )
        }
    }

    @PutMapping(value = ["/api/room/{id}/person"], consumes = ["application/json"], produces = ["application/json"])
    fun putPersonInRaum(@PathVariable id: UUID, @RequestBody addPersonDto: AddPersonDto): ResponseEntity<Any> {
        val ergebnis =
            personZuRaumHinzufuegung.fuegePersonHinzu(
                raumId = Raum.Id(id),
                personId = Person.Id(addPersonDto.personId)
            )

        return when (ergebnis) {
            PersonZuRaumHinzufuegung.PersonHinzugefuegt -> ResponseEntity.ok().build()

            is PersonZuRaumHinzufuegung.PersonNichtGefunden -> ResponseEntity(
                ErrorResponseDto("Person mit id '${ergebnis.personId.value}' existiert nicht"),
                HttpStatus.NOT_FOUND
            )

            is PersonZuRaumHinzufuegung.RaumNichtGefunden -> ResponseEntity(
                ErrorResponseDto("Raum mit id '${ergebnis.raumId.value}' existiert nicht"),
                HttpStatus.NOT_FOUND
            )
        }
    }
}
