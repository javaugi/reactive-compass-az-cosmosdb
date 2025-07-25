/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.data;

import com.sisllc.instaiml.model.User;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;


public class UserGenerator extends DataGeneratorBase {
    
    public static User generate(String username, PasswordEncoder passwordEncoder) {
        String firstName = JAVA_FAKER.name().firstName();
        String lastName = JAVA_FAKER.name().lastName();
        return User.builder()
            .id(UUID.randomUUID().toString())
            .name(firstName + " " + lastName)
            .username(username)
            .password(passwordEncoder.encode(username))
            .roles("ROLE_USER,ROLE_ADMIN")
            .age(JAVA_FAKER.number().numberBetween(18, 70))
            .city(JAVA_FAKER.address().city())
            .email(JAVA_FAKER.internet().emailAddress())
            .phone(JAVA_FAKER.phoneNumber().phoneNumber())
            .firstName(firstName)
            .lastName(lastName)
            .build();
    }
}
