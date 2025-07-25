/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.config;

import static com.sisllc.instaiml.config.ProfileMockConfig.MOCK_PROFILE;
import java.io.Serializable;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(MOCK_PROFILE)
@Configuration
public class ProfileMockConfig implements Serializable {
    private static final long serialVersionUID = 35238717363L;
    public static final String MOCK_PROFILE = "mock";
    
}
