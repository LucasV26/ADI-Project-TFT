package com.adi.tftapi.Endpoint.Controller;

import com.adi.tftapi.Entity.Unidade;
import com.adi.tftapi.Repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/unidade")
public class UnidadeController {

    @Autowired
    private UnidadeRepository unidadeRepository;

    @PostMapping("/create")
    public ResponseEntity<Unidade> criarUnidade(@RequestBody Unidade unidade){

        return ResponseEntity.ok(unidadeRepository.save(unidade));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Unidade> buscarUnidade(@PathVariable String id){
        if(unidadeRepository.findById(id).isPresent())
            return ResponseEntity.ok(unidadeRepository.findById(id).get());

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Unidade>> listarUnidades(){
        return ResponseEntity.ok(unidadeRepository.findAll());
    }

    //Rota utilizada em desenvolvimento para popular banco a partir de dados estruturados em JSON
    @PostMapping("/popular")
    public ResponseEntity<Object> popularSinergia(@RequestBody List<Unidade> unidades){
        List<Unidade> salvas = unidadeRepository.saveAll(unidades);
        if(salvas != null)
            return ResponseEntity.ok("Unidades inseridas com sucesso!");

        return ResponseEntity.unprocessableEntity().body("Deu errado, meu amigo ;-;");
    }
}
