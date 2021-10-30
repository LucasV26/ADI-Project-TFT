package com.adi.tftapi.Controller;

import com.adi.tftapi.Entity.Unidade;
import com.adi.tftapi.Repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnidadeController {

    @Autowired
    private UnidadeRepository unidadeRepository;

    public ResponseEntity<Unidade> createUnidade(@RequestBody Unidade unidade){
        return ResponseEntity.ok(unidadeRepository.save(unidade));
    }
}
