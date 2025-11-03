package de.neusta.dddworkshop.infrastructure.person.rest

import de.neusta.dddworkshop.application.person.PersonAnlage
import de.neusta.dddworkshop.infrastructure.common.ErrorResponseDto
import de.neusta.dddworkshop.infrastructure.person.rest.dto.CreatePersonDto
import de.neusta.dddworkshop.infrastructure.person.rest.dto.ReadPersonDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/person")
class PersonController(private val personAnlage: PersonAnlage) {

    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    fun postPerson(@RequestBody createPersonDto: CreatePersonDto): ResponseEntity<Any> {
        personAnlage.legeAn(
            vorname = createPersonDto.name.vorname,
            nachname = createPersonDto.name.nachname,
            benutzername = createPersonDto.benutzername,
            namenszusatz = createPersonDto.namenszusatz
        ).apply {
            when (this) {
                PersonAnlage.BenutzernameSchonVergeben -> return ResponseEntity.badRequest()
                    .body(ErrorResponseDto("Der Benutzername ${createPersonDto.benutzername} ist schon vergeben."))

                is PersonAnlage.PersonAngelegt -> return ResponseEntity.ok(
                    ReadPersonDto(
                        id = this.person.id.value,
                        name = ReadPersonDto.NameDto(
                            vorname = this.person.name.vorname,
                            nachname = this.person.name.nachname
                        ),
                        benutzername = this.person.benutzername.value,
                        namenszusatz = this.person.namenszusatz?.value
                    )
                )

                is PersonAnlage.UngueltigeArgumente -> return ResponseEntity.badRequest()
                    .body("Unguelige Argumente: '${this.fehler}'")
            }
        }
    }
}