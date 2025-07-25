/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.data;

import com.sisllc.instaiml.model.Member;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MemberGenerator extends DataGeneratorBase {

    public static Member generate(String insurancePlanId) {
        return Member.builder()
            .id(UUID.randomUUID().toString())
            .insurancePlanId(insurancePlanId)
            .name(JAVA_FAKER.name().fullName())
            .gender(JAVA_FAKER.demographic().sex())
            .tobaccoUser(JAVA_FAKER.bool().bool())
            .birthDate(NET_FAKER.date().birthday(20, 90).toInstant().atOffset(ZoneOffset.UTC))
            .enrollmentDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(1, 365), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .terminationDate(JAVA_FAKER.date().future(JAVA_FAKER.number().numberBetween(1, 365), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .build();
    }    
}
