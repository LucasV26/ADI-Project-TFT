package com.adi.tftapi.Endpoint.Controller;

import com.adi.tftapi.Entity.Sinergia;
import com.adi.tftapi.Repository.SinergiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/sinergia")
public class SinergiaController {

    @Autowired
    private SinergiaRepository sinergiaRepository;

    @PostMapping("/create")
    public ResponseEntity<Sinergia> criarSinergia(@RequestBody Sinergia sinergia){
        return ResponseEntity.ok(sinergiaRepository.save(sinergia));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sinergia> buscarSinergia(@PathVariable String id){
        if(sinergiaRepository.findById(id).isPresent())
            return ResponseEntity.ok(sinergiaRepository.findById(id).get());

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Sinergia>> listarSinergias(){
        return ResponseEntity.ok(sinergiaRepository.findAll());
    }

    //Rota utilizada em desenvolvimento para popular banco a partir de dados estruturados em JSON
    @PostMapping("/popular")
    public ResponseEntity<Object> popularSinergia(@RequestBody List<Sinergia> sinergias){
        List<Sinergia> salvas = sinergiaRepository.saveAll(sinergias);
        if(salvas != null)
            return ResponseEntity.ok("Sinergias inseridas com sucesso!");

        return ResponseEntity.unprocessableEntity().body("Deu errado, meu amigo ;-;");
    }
}
