package com.sisllc.instaiml.repository.impl;

import com.azure.spring.data.cosmos.core.ReactiveCosmosTemplate;
import com.azure.spring.data.cosmos.exception.CosmosAccessException;
import com.sisllc.instaiml.model.User;
import com.sisllc.instaiml.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class CustomUserRepositoryImpl implements CustomUserRepository {
    private final ReactiveCosmosTemplate cosmosTemplate;

    @Autowired
    public CustomUserRepositoryImpl(ReactiveCosmosTemplate cosmosTemplate) {
        this.cosmosTemplate = cosmosTemplate;
    }

    @Override
    public Mono<User> saveUser(User user) {
        System.out.println("Saving user: " + user);

        return cosmosTemplate
                .upsert("users", user)
                // Convert any CosmosAccessException or other error into a RuntimeException
                .onErrorMap(CosmosAccessException.class, e -> {
                    System.err.println("Error saving user: " + e.getMessage());
                    return new RuntimeException();
                });
    }
}
