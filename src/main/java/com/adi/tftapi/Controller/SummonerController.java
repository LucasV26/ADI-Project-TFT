package com.adi.tftapi.Controller;

import com.adi.tftapi.Model.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/summoner")
public class SummonerController {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{name}")
    public Summoner getSummonerInfo(@PathVariable String name){
        Summoner summoner = restTemplate.getForObject("https://br1.api.riotgames.com/tft/summoner/v1/summoners/by-name/" + name + "?api_key=" + this.apiKey, Summoner.class);

        return summoner;
    }
}
