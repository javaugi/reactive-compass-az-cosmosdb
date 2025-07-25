/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sisllc.instaiml.config;

import static com.sisllc.instaiml.config.ProfileDevEtcConfig.DEV_PROFILE;
import java.io.Serializable;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(DEV_PROFILE)
public class ProfileDevEtcConfig implements Serializable {

    private static final long serialVersionUID = 321357244048L;
    protected static final String DEV_PROFILE = "dev";
    
}
