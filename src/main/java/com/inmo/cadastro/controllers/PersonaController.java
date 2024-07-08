package com.inmo.cadastro.controllers;

import com.inmo.cadastro.models.Persona;
import com.inmo.cadastro.services.PersonaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/persona")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping("/read")
    public ResponseEntity<List<Persona>> read() {
        return new ResponseEntity<>(personaService.findByAll(), HttpStatus.OK);
    }

    @GetMapping("/cedulaUnica")
    public ResponseEntity<Boolean> cedulaUnica(@RequestParam String ci) {
        return new ResponseEntity<>(personaService.cedulaUnica(ci), HttpStatus.OK);
    }

    @GetMapping("/rgUnico")
    public ResponseEntity<Boolean> rgUnico(@RequestParam String rg) {
        return new ResponseEntity<>(personaService.rgUnico(rg), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Persona> create(@RequestBody Persona p) {
        return new ResponseEntity<>(personaService.save(p), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Persona> update(@RequestParam Long id, @RequestBody Persona p) {
        Persona persona = personaService.findById(id);
        if (persona != null) {
            try {

                persona.setPerApellido(p.getPerApellido());
                persona.setPerCedula(p.getPerCedula());
                persona.setPerDireccion(p.getPerDireccion());
                persona.setPerTelefono(p.getPerTelefono());
                persona.setPerNombre(p.getPerNombre());
                persona.setPerFechaNacimiento(p.getPerFechaNacimiento());

                return new ResponseEntity<>(personaService.save(persona), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Persona> delete(@RequestParam Long id) {
        personaService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
