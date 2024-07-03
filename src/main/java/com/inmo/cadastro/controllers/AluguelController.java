package com.inmo.cadastro.controllers;

import com.inmo.cadastro.models.*;
import com.inmo.cadastro.services.AluguelService;
import com.inmo.cadastro.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/aluguel")
public class AluguelController {

    @Autowired
    private AluguelService aluguelService;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/read")
    public ResponseEntity<List<Aluguel>> read() {
        return new ResponseEntity<>(aluguelService.findByAll(), HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<Aluguel> create(@RequestBody Aluguel p) {
        return new ResponseEntity<>(aluguelService.save(p), HttpStatus.CREATED);
    }

    @GetMapping("/getAluguelByInquilino")
    public ResponseEntity<Aluguel> getAluguelByInquilino(@RequestParam Long id) {
        return new ResponseEntity<>(aluguelService.getAluguelByInquilino(id), HttpStatus.OK);
    }

    @GetMapping("/cantidadAluguels")
    public int getCantidadAluguels() {
        return aluguelService.getCantidadAluguels();
    }

    @GetMapping("/findByAluId")
    public ResponseEntity<Aluguel> findByAluId(@RequestParam Long id) {
        return new ResponseEntity<>(aluguelService.findByAluId(id), HttpStatus.OK);
    }

    @GetMapping("/inquilinoUnico")
    public ResponseEntity<Boolean> inquilinoUnico(@RequestParam Long id) {
        return new ResponseEntity<>(aluguelService.inquilinoUnico(id), HttpStatus.OK);
    }

    @GetMapping("/searchAluguelData")
    public ResponseEntity<List<Aluguel>> searchAluguelData(@RequestParam String search, @RequestParam int est) {
        List<Object[]> aluguelData = aluguelService.searchAluguelData(search, est);
        List<Aluguel> aluguels = new ArrayList<>();

        for (Object[] data : aluguelData) {
            Aluguel aluguel = new Aluguel();
            aluguel.setAluId((Long) data[0]);

            aluguel.setAluValor((int) data[1]);

            aluguel.setAluEndereco((String) data[2]);

            Long propId = (Long) data[3];
            aluguel.setAluPropietario(usuarioService.findById(propId));

            Long inqId = (Long) data[4];
            aluguel.setAluInquilino(usuarioService.findById(inqId));

            aluguel.setAluExpiracao((Date) data[5]);

            aluguel.setAluDiaPago((int) data[6]);

            aluguel.setAluFotoEntrada((String) data[7]);

            aluguel.setAluFotoSaida((String) data[8]);

            aluguel.setAluContrato((String) data[9]);

            aluguel.setAluComprovante((String) data[10]);

            aluguels.add(aluguel);
        }

        return new ResponseEntity<>(aluguels, HttpStatus.OK);
    }

    @PutMapping("/updateComprovante")
    public ResponseEntity<Aluguel> updateComprovante(@RequestParam Long id, @RequestBody String com) {
        Aluguel aluguel = aluguelService.findById(id);
        if (aluguel != null) {
            try {
                aluguel.setAluComprovante(com);
                aluguelService.save(aluguel);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Aluguel> update(@RequestParam Long id, @RequestBody Aluguel a) {
        Aluguel aluguel = aluguelService.findById(id);
        if (aluguel != null) {
            try {

                aluguel.setAluEndereco(a.getAluEndereco());
                aluguel.setAluPropietario(a.getAluPropietario());
                aluguel.setAluInquilino(a.getAluInquilino());
                aluguel.setAluExpiracao(a.getAluExpiracao());
                aluguel.setAluFotoEntrada(a.getAluFotoEntrada());
                aluguel.setAluFotoSaida(a.getAluFotoSaida());
                aluguel.setAluExpiracao(a.getAluExpiracao());

                return new ResponseEntity<>(aluguelService.save(aluguel), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Aluguel> delete(@RequestParam Long id) {
        aluguelService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
