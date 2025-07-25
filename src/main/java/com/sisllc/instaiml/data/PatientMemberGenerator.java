/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.data;

import com.sisllc.instaiml.model.PatientMember;
import java.util.UUID;

public class PatientMemberGenerator extends DataGeneratorBase {

    public static PatientMember generate(String patientId, String memberId) {
        return PatientMember.builder()
            .id(UUID.randomUUID().toString())
            .patientId(patientId)
            .memberId(memberId)
            .build();
    }    
}
