package com.example.springwebflux_deepdive.Repositories;

import com.example.springwebflux_deepdive.Models.Anime;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AnimeRepository extends ReactiveMongoRepository<Anime, String> {


    Mono<Anime> deleteAnimeById(String id);
}
