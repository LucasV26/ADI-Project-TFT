package com.adi.tftapi.Controller;

import com.adi.tftapi.Model.Match;
import com.adi.tftapi.Model.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequestMapping("/match")
public class MatchController {

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

        return match;
    }
}
