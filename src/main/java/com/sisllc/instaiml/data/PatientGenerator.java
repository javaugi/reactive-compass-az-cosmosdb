/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.data;

import com.sisllc.instaiml.model.Patient;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PatientGenerator extends DataGeneratorBase {

    public static Patient generate() {
        return Patient.builder()
            .id(UUID.randomUUID().toString())
            .name(FAKER.name().fullName())
            .birthDate(FAKER.date().birthday().toInstant().atOffset(ZoneOffset.UTC))
            .email(FAKER.internet().emailAddress())
            .phone(FAKER.phoneNumber().phoneNumber())
            .address(FAKER.address().fullAddress())
            .createdDate(FAKER.date().past(FAKER.number().numberBetween(30, 90), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .updatedDate(FAKER.date().past(FAKER.number().numberBetween(1, 30), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .build();
    }    
}
