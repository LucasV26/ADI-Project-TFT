package com.adi.tftapi.Controller;

import com.adi.tftapi.Entity.Build;
import com.adi.tftapi.Entity.BuildConstruct;
import com.adi.tftapi.Repository.BuildConstructRepository;
import com.adi.tftapi.Repository.BuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/build")
public class BuildController {

    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private BuildConstructRepository buildConstructRepository;

    @PostMapping("/create")
    public ResponseEntity<Build> createBuild(@RequestBody Build build){

        return ResponseEntity.ok(buildRepository.save(build));
    }

    @PostMapping("/construct")
    public ResponseEntity<Build> construirBuild(@RequestBody BuildConstruct buildConstruct){
        BuildConstruct construct = buildConstructRepository.save(buildConstruct);

        return ResponseEntity.ok(construct.getBuild());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Build> buscarBuild(@PathVariable int id){
        if(buildRepository.findById(id).isPresent())
            return ResponseEntity.ok(buildRepository.findById(id).get());

        return ResponseEntity.notFound().build();
    }
}
