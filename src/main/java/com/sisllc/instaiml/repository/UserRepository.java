package com.sisllc.instaiml.repository;

import com.azure.spring.data.cosmos.repository.Query;
import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import com.sisllc.instaiml.model.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCosmosRepository<User, String>, CustomUserRepository {
    // Return a Flux of string IDs
    @Query("SELECT VALUE c.id FROM c")
    Flux<String> getUserIds();

    @Query("SELECT * FROM c")
    Flux<User> getAllUsers();

    Mono<User> findByUsername(String username);
}
