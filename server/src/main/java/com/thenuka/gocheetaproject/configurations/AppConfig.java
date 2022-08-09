package com.thenuka.gocheetaproject.configurations;

import com.thenuka.gocheetaproject.interfaces.ILocationService;
import com.thenuka.gocheetaproject.services.LocationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ILocationService iLocation () {
        return new LocationService();
    }
}
