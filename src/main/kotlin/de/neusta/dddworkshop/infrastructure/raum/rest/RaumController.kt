package de.neusta.dddworkshop.infrastructure.raum.rest

import de.neusta.dddworkshop.application.raum.RaumAnlage
import de.neusta.dddworkshop.domain.raum.Raum
import de.neusta.dddworkshop.domain.raum.RaumRepository
import de.neusta.dddworkshop.infrastructure.common.ErrorResponseDto
import de.neusta.dddworkshop.infrastructure.raum.rest.dto.CreateRaumDto
import de.neusta.dddworkshop.infrastructure.raum.rest.dto.ReadRaumDto
import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RaumController(private val raumAnlage: RaumAnlage, private val raumRepository: RaumRepository) {

    @PostMapping(value = ["/api/room"], consumes = ["application/json"], produces = ["application/json"])
    fun postRaum(@RequestBody createRoomDto: CreateRaumDto): ResponseEntity<Any> {
        val anlageErgebnis = raumAnlage.legeAn(raumnummer = createRoomDto.raumnummer, name = createRoomDto.name)

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
        val raum = raumRepository.findeMit(Raum.Id(id))

        return if (raum != null) {
            ResponseEntity.ok(
                ReadRaumDto(
                    id = raum.id.value,
                    raumnummer = raum.raumnummer.value,
                    name = raum.name.value
                )
            )
        } else {
            ResponseEntity(
                ErrorResponseDto("Raum mit id '$id' existiert nicht"),
                HttpStatus.NOT_FOUND
            )
        }
    }
}

