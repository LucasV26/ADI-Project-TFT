package com.adi.tftapi.Controller;

import com.adi.tftapi.Entity.Sinergia;
import com.adi.tftapi.Repository.SinergiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SinergiaController {

    @Autowired
    private SinergiaRepository sinergiaRepository;

    @PostMapping("/sinergia/create")
    public ResponseEntity<Sinergia> createSinergia(@RequestBody Sinergia sinergia){
        return ResponseEntity.ok(sinergiaRepository.save(sinergia));
    }

    @GetMapping("/sinergia/{id}")
    public ResponseEntity<Sinergia> buscarSinergia(@PathVariable String id){
        if(sinergiaRepository.findById(id).isPresent())
            return ResponseEntity.ok(sinergiaRepository.findById(id).get());

        return ResponseEntity.notFound().build();
    }

    //Rota utilizada em desenvolvimento para popular banco a partir de dados estruturados em JSON
//    @PostMapping("/sinergia/popular")
//    public ResponseEntity<Object> popularSinergia(@RequestBody List<Sinergia> sinergias){
//        List<Sinergia> salvas = sinergiaRepository.saveAll(sinergias);
//        if(salvas != null)
//            return ResponseEntity.ok("Sinergias inseridas com sucesso!");
//
//        return ResponseEntity.unprocessableEntity().body("Deu errado, meu amigo ;-;");
//    }
}
