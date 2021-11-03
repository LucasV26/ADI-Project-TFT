package com.adi.tftapi.Endpoint.Controller;

import com.adi.tftapi.Entity.Item;
import com.adi.tftapi.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping("/create")
    public ResponseEntity<Item> criarItem(@RequestBody Item item){
        return ResponseEntity.ok(itemRepository.save(item));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> buscarItem(@PathVariable int id){
        if(itemRepository.findById(id).isPresent())
            return ResponseEntity.ok(itemRepository.findById(id).get());

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Item>> listarItems(){
        return ResponseEntity.ok(itemRepository.findAll());
    }

    //Rota utilizada em desenvolvimento para popular banco a partir de dados estruturados em JSON
    @PostMapping("/popular")
    public ResponseEntity<Object> popularSinergia(@RequestBody List<Item> items){
        List<Item> salvas = itemRepository.saveAll(items);
        if(salvas != null)
            return ResponseEntity.ok("Items inseridos com sucesso!");

        return ResponseEntity.unprocessableEntity().body("Deu errado, meu amigo ;-;");
    }
}
