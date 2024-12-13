package de.neusta.dddworkshop.infrastructure.person.rest

import de.neusta.dddworkshop.application.person.PersonAnlage
import de.neusta.dddworkshop.domain.person.PersonRepository
import de.neusta.dddworkshop.infrastructure.common.ErrorResponseDto
import de.neusta.dddworkshop.infrastructure.person.rest.dto.CreatePersonDto
import de.neusta.dddworkshop.infrastructure.person.rest.dto.ReadPersonDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PersonController(private val personAnlage: PersonAnlage, private val personRepository: PersonRepository) {

    @PostMapping(value = ["/api/person"], consumes = ["application/json"], produces = ["application/json"])
    fun postPerson(@RequestBody createPersonDto: CreatePersonDto): ResponseEntity<Any> {
        val anlageErgebnis = personAnlage.legeAn(
            vorname = createPersonDto.vorname,
            nachname = createPersonDto.nachname,
            namenszusatz = createPersonDto.namenszusatz,
            benutzername = createPersonDto.benutzername
        )

        return when (anlageErgebnis) {
            is PersonAnlage.PersonAngelegt -> ResponseEntity.ok(
                ReadPersonDto(
                    id = anlageErgebnis.person.id.value,
                    vorname = anlageErgebnis.person.name.vorname,
                    nachname = anlageErgebnis.person.name.nachname,
                    benutzername = anlageErgebnis.person.benutzername.value,
                    namenszusatz = anlageErgebnis.person.namenszusatz?.name
                )
            )

            is PersonAnlage.BenutzernameExistiertSchon -> ResponseEntity.badRequest()
                .body(ErrorResponseDto("Der Benutzername ${anlageErgebnis.benutzername.value} existiert schon."))

            is PersonAnlage.UngueltigerWert -> ResponseEntity.badRequest()
                .body("Ungueliger Wert: '${anlageErgebnis.fehlermeldung}'")
        }
    }
}

