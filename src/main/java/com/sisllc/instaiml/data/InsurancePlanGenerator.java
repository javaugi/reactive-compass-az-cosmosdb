/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.data;

import com.sisllc.instaiml.model.InsurancePlan;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class InsurancePlanGenerator extends DataGeneratorBase {

    public static InsurancePlan generate(String insuranceCompanyId) {
        return InsurancePlan.builder()
            .id(UUID.randomUUID().toString())
            .insuranceCompanyId(insuranceCompanyId)
            .planName("PLAN-NAME-" + JAVA_FAKER.number().digits(6))
            .planType(NET_FAKER.options().option("HMO", "PPO", "EPO", "POS", "HDHP"))
            .networkType(NET_FAKER.options().option("In-Network", "Out-of-Network"))
            .tierLevel(NET_FAKER.options().option("Bronze", "Silver", "Gold", "Platinum", "Catastrophic"))
            .active(JAVA_FAKER.bool().bool())
            .effectiveDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(30, 90), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .expirationDate(JAVA_FAKER.date().future(JAVA_FAKER.number().numberBetween(100, 300), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .createdDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(30, 90), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .updatedDate(JAVA_FAKER.date().past(JAVA_FAKER.number().numberBetween(1, 30), TimeUnit.DAYS).toInstant().atOffset(ZoneOffset.UTC))
            .build();
    }    
}
