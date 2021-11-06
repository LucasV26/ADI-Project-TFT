package com.adi.tftapi.Endpoint.Controller;

import com.adi.tftapi.Endpoint.Service.BuildService;
import com.adi.tftapi.Entity.Build;
import com.adi.tftapi.Entity.BuildConstruct;
import com.adi.tftapi.Model.Unit;
import com.adi.tftapi.Repository.BuildConstructRepository;
import com.adi.tftapi.Repository.BuildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/build")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BuildController {

    private final BuildService buildService;
    private final BuildRepository buildRepository;
    private final BuildConstructRepository buildConstructRepository;

    @PostMapping("/create")
    public ResponseEntity<Build> criarBuild(@RequestBody Build build){

        return ResponseEntity.ok(buildRepository.save(build));
    }

    @PatchMapping("/update")
    public ResponseEntity<Build> atualizarBuild(@RequestBody Build build){
        build.setBuildConstructs(new ArrayList<>());
        buildRepository.updateBuildById(build.getName(), build.getDescription(), build.getId());

        return ResponseEntity.ok(build);
    }

    @PostMapping("/construct")
    public ResponseEntity<Build> construirBuild(@RequestBody BuildConstruct buildConstruct){
        BuildConstruct construct = buildConstructRepository.save(buildConstruct);

        return ResponseEntity.ok(construct.getBuild());
    }

    @PostMapping("/favorite")
    public ResponseEntity<Build> favoritarBuild(@RequestBody List<Unit> build){
        int novaBuildId = buildService.preencherBuild(build);

        if(buildRepository.findById(novaBuildId).isPresent())
            return ResponseEntity.ok(buildRepository.findById(novaBuildId).get());

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Build> buscarBuild(@PathVariable int id){
        if(buildRepository.findById(id).isPresent())
            return ResponseEntity.ok(buildRepository.findById(id).get());

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarBuild(@PathVariable int id){

        if(buildRepository.findById(id).isPresent()) {
            buildRepository.delete(buildRepository.findById(id).get());
            return ResponseEntity.ok("Build excluida com sucesso");
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Build>> listarBuilds(){
        return ResponseEntity.ok(buildRepository.findAll());
    }

}
