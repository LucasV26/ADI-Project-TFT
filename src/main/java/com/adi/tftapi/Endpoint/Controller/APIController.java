package com.adi.tftapi.Endpoint.Controller;

import com.adi.tftapi.Model.Match;
import com.adi.tftapi.Model.Participant;
import com.adi.tftapi.Model.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class APIController {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{name}/{count}")
    public List<String> getUserMatchHistory(@PathVariable String name, @PathVariable int count) {
        Summoner summoner = restTemplate.getForObject("https://br1.api.riotgames.com/tft/summoner/v1/summoners/by-name/" + name + "?api_key=" + this.apiKey, Summoner.class);

        List<String> matchHistory = restTemplate.getForObject("https://americas.api.riotgames.com/tft/match/v1/matches/by-puuid/" + summoner.getPuuid() + "/ids?count=" + count + "&api_key=" + this.apiKey, List.class);

        return matchHistory;
    }

    @GetMapping("/{matchId}")
    public Match getMatchInfo(@PathVariable String matchId) {
        Match match = restTemplate.getForObject("https://americas.api.riotgames.com/tft/match/v1/matches/" + matchId + "?api_key=" + this.apiKey, Match.class);

        for(Participant p : match.getInfo().getParticipants()){
            Summoner summoner = restTemplate.getForObject("https://br1.api.riotgames.com/tft/summoner/v1/summoners/by-puuid/" + p.getPuuid() + "?api_key=" + this.apiKey, Summoner.class);
            p.setName(summoner.getName());
        }

        return match;
    }
}
