package com.example.springwebflux_deepdive.Repositories;

import com.example.springwebflux_deepdive.Models.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User,String> {

     Mono<User> findByEmail(String email);

    Mono<Boolean> existsByEmail(String email);

}
