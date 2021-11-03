package com.adi.tftapi.Endpoint.Service;

import com.adi.tftapi.Embeddable.BuildConstructKey;
import com.adi.tftapi.Entity.Build;
import com.adi.tftapi.Entity.BuildConstruct;
import com.adi.tftapi.Entity.Item;
import com.adi.tftapi.Entity.Unidade;
import com.adi.tftapi.Model.Unit;
import com.adi.tftapi.Repository.BuildConstructRepository;
import com.adi.tftapi.Repository.BuildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BuildService {

    private final BuildRepository buildRepository;
    private final BuildConstructRepository buildConstructRepository;

    //Salvando no banco a Build recebida da API (lista de unidades)
    public int preencherBuild(List<Unit> build){
        Build novaBuild = new Build("Nova Build", "Build recem criada");

        novaBuild = buildRepository.save(novaBuild);

        List<BuildConstruct> conexoes = new ArrayList<BuildConstruct>();

        //Instanciando uma conexao entre Build e Unidade para cada unidade da lista recebida
        for(Unit u : build) {
            BuildConstruct conexao = new BuildConstruct();
            List<Item> items = new ArrayList<Item>();

            for(int i : u.getItems()){
                items.add(new Item(i));
            }

            conexao.setId(new BuildConstructKey());
            conexao.getId().setChampion(u.getCharacter_id());
            conexao.getId().setBuild(novaBuild.getId());
            conexao.setUnidade(new Unidade(u.getCharacter_id()));
            conexao.setBuild(new Build(novaBuild.getId()));
            conexao.setItems(items);

            conexoes.add(conexao);
        }

        buildConstructRepository.saveAll(conexoes);

        return novaBuild.getId();
    }
}
