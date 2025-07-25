package com.sisllc.instaiml.controller;

import java.util.Date;
import org.springframework.boot.SpringBootVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/health")
public class HealthController {
    @GetMapping
    public Mono<String> index() {
        return Mono.just("Greetings from Spring Boot " + SpringBootVersion.getVersion() + " at the server and now it is " + new Date());
    }
}
