package com.thenuka.gocheetaproject.configurations;

import com.thenuka.gocheetaproject.interfaces.ILocation;
import com.thenuka.gocheetaproject.services.LocationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ILocation iLocation () {
        return new LocationService();
    }
}
