/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.data;

import com.sisllc.instaiml.model.Patient;
import java.util.UUID;

public class PatientGenerator extends DataGeneratorBase {

    public static Patient generate() {
        return Patient.builder()
            .id(UUID.randomUUID().toString())
            .name(JAVA_FAKER.name().fullName())
            .birthDate(JAVA_FAKER.date().birthday().toInstant())
            .email(JAVA_FAKER.internet().emailAddress())
            .phone(JAVA_FAKER.phoneNumber().phoneNumber())
            .address(JAVA_FAKER.address().fullAddress())
            //.createdDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(30, 90), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            //.updatedDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(1, 30), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .build();
    }    
}
