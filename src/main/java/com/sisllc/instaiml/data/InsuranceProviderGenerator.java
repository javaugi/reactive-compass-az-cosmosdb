/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.data;

import com.sisllc.instaiml.model.InsuranceProvider;
import java.util.UUID;

public class InsuranceProviderGenerator extends DataGeneratorBase {

    public static InsuranceProvider generate() {
        return InsuranceProvider.builder()
            .id(UUID.randomUUID().toString())
            .providerName(FAKER.company().name())
            .specialty(NET_FAKER.company().name())
            .networkStatus(NET_FAKER.options().option("In-Network", "Out-of-Network"))
            .build();
    }    
}
