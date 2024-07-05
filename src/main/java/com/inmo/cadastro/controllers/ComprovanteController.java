package com.inmo.cadastro.controllers;

import com.inmo.cadastro.models.*;
import com.inmo.cadastro.services.ComprovanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/comprovante")
public class ComprovanteController {

    @Autowired
    private ComprovanteService comprovanteService;

    @GetMapping("/findComprovantesByFechaPartesAndInquilino")
    public ResponseEntity<List<Comprovante>> findComprovantesByFechaPartesAndInquilino(@RequestParam int dia, @RequestParam int mes, @RequestParam int ano) {
        return new ResponseEntity<>(comprovanteService.findComprovantesByFechaPartesAndInquilino(dia, mes, ano), HttpStatus.OK);
    }

    @GetMapping("/read")
    public ResponseEntity<List<Comprovante>> read() {
        return new ResponseEntity<>(comprovanteService.findByAll(), HttpStatus.OK);
    }

    @GetMapping("/getComprovanteByAluId")
    public ResponseEntity<Comprovante> getComprovanteByAluId(@RequestParam Long id) {
        return new ResponseEntity<>(comprovanteService.getComprovanteById(id), HttpStatus.OK);
    }

    @GetMapping("/getComprovanteByComFechaRegistro")
    public ResponseEntity<Comprovante> getComprovanteByComFechaRegistro(@RequestParam Long id) {
        return new ResponseEntity<>(comprovanteService.getComprovanteByComFechaRegistro(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Comprovante> create(@RequestBody Comprovante c) {
        return new ResponseEntity<>(comprovanteService.save(c), HttpStatus.CREATED);
    }

    @PutMapping("/updateComprovante")
    public ResponseEntity<Comprovante> updateComprovante(@RequestParam Long id, @RequestBody String com) {
        Comprovante Comprovante = comprovanteService.findById(id);
        if (Comprovante != null) {
            try {
                Comprovante.setComComprovante(com);
                comprovanteService.save(Comprovante);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
