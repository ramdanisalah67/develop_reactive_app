package com.example.springwebflux_deepdive.Controllers;


import com.example.springwebflux_deepdive.Models.Anime;
import com.example.springwebflux_deepdive.Repositories.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.Instant;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/anime/")
public class AnimeController {


    private final AnimeRepository animeRepository ;


    //list of all Anime



    @GetMapping("all")
    public Flux<Anime> findAll() {

        return animeRepository.findAll();
    }


    @PostMapping("save")
    public Mono<Anime> addNewAnime(@RequestBody Anime anime) {

        anime.setCreatedAt(Instant.now());
        return animeRepository.save(anime);
    }


    @DeleteMapping("delete/{id}")
    public Mono<Anime> deleteAnime(@PathVariable String id) {
        return animeRepository.deleteAnimeById(id);
    }


    @PutMapping("update/{id}")
    public Mono<Anime> updateAnime(@PathVariable String id, @RequestBody Anime anime) {
        anime.setId(id);
        anime.setCreatedAt(Instant.now());
        return animeRepository.save(anime);
    }

}

