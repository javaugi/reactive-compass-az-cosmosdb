/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.data;

import com.sisllc.instaiml.model.Pharmacy;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PharmacyGenerator extends DataGeneratorBase {

    public static Pharmacy generate() {
        return Pharmacy.builder()
            .id(UUID.randomUUID().toString())
            .pharmacyCode("PHAR-" + JAVA_FAKER.number().digits(6))
            .name("PHARMA " + JAVA_FAKER.address().streetName())
            .email(JAVA_FAKER.internet().emailAddress())
            .phone(JAVA_FAKER.phoneNumber().phoneNumber())
            .address(JAVA_FAKER.address().fullAddress())
            .createdDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(30, 90), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .updatedDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(1, 30), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .build();
    }    
}
