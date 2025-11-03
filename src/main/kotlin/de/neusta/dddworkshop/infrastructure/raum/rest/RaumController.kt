package de.neusta.dddworkshop.infrastructure.raum.rest

import de.neusta.dddworkshop.application.raum.PersonHinzufuegung
import de.neusta.dddworkshop.application.raum.RaumAbfrage
import de.neusta.dddworkshop.application.raum.RaumAnlage
import de.neusta.dddworkshop.domain.person.Person
import de.neusta.dddworkshop.domain.raum.Raum
import de.neusta.dddworkshop.infrastructure.common.ErrorResponseDto
import de.neusta.dddworkshop.infrastructure.raum.rest.dto.CreateRaumDto
import de.neusta.dddworkshop.infrastructure.raum.rest.dto.PutPersonInRaumDto
import de.neusta.dddworkshop.infrastructure.raum.rest.dto.ReadRaumDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/room")
class RaumController(
    private val raumAnlage: RaumAnlage,
    private val raumAbfrage: RaumAbfrage,
    private val personHinzufuegung: PersonHinzufuegung
) {

    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    fun postRaum(@RequestBody createRoomDto: CreateRaumDto): ResponseEntity<Any> {
        raumAnlage.legeAn(
            nummer = createRoomDto.nummer,
            name = createRoomDto.name
        ).apply {
            when (this) {
                is RaumAnlage.RaumAngelegt -> return ResponseEntity.ok(
                    ReadRaumDto(
                        id = this.raum.id.value,
                        raumnummer = this.raum.nummer.value,
                        name = this.raum.name.value
                    )
                )

                is RaumAnlage.RaumnummerExistiertSchon -> return ResponseEntity.badRequest()
                    .body(ErrorResponseDto("Die Raumnummer ${createRoomDto.nummer} existiert schon."))

                is RaumAnlage.UngueltigeArgumente -> return ResponseEntity.badRequest()
                    .body("Unguelige Argumente: '${this.fehler}'")
            }
        }
    }

    @GetMapping(value = ["/{id}"], produces = ["application/json"])
    fun getRaum(@PathVariable id: UUID): ResponseEntity<Any> {
        raumAbfrage.frageRaumAb(Raum.Id(id))
            .apply {
                when (this) {
                    is RaumAbfrage.RaumNichtGefunden -> return ResponseEntity(
                        ErrorResponseDto("Raum mit ID '$id' existiert nicht"),
                        HttpStatus.NOT_FOUND
                    )

                    is RaumAbfrage.RaumGefunden -> return ResponseEntity.ok(
                        ReadRaumDto(
                            id = this.raum.id.value,
                            raumnummer = this.raum.nummer.value,
                            name = this.raum.name.value
                        )
                    )
                }
            }
    }

    @PutMapping(value = ["/{raumId}/person"], consumes = ["application/json"], produces = ["application/json"])
    fun putPersonInRaum(
        @PathVariable raumId: UUID,
        @RequestBody putPersonInRaumDto: PutPersonInRaumDto
    ): ResponseEntity<Any> {
        personHinzufuegung.fuegePersonZuRaumHinzu(
            personId = Person.Id(putPersonInRaumDto.personId),
            raumId = Raum.Id(raumId)
        ).apply {
            when (this) {
                PersonHinzufuegung.PersonExistiertNicht -> return ResponseEntity(
                    ErrorResponseDto("Die Person mit der ID ${putPersonInRaumDto.personId} existiert nicht."),
                    HttpStatus.NOT_FOUND
                )

                PersonHinzufuegung.PersonHinzugefuegt -> return ResponseEntity.noContent().build()

                is PersonHinzufuegung.PersonSchonInAnderemRaum -> return ResponseEntity.badRequest()
                    .body(ErrorResponseDto("Die Person mit der ID ${putPersonInRaumDto.personId} ist schon im Raum mit der ID ${this.raumId.value}"))

                PersonHinzufuegung.RaumExistiertNicht -> return ResponseEntity(
                    ErrorResponseDto("Der Raum mit der ID $raumId existiert nicht."),
                    HttpStatus.NOT_FOUND
                )
            }
        }
    }
}

