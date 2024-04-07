package com.example.demo.model;

import java.time.LocalDate;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner (StudentRepository repository) {
        return args -> {
            Student Mio = new Student("Mio","Mio.@gmail.com",LocalDate.now(),18);
            Student MiYa = new Student("MiYa","MiYa.@163.com",LocalDate.of(1999, 12, 14),20);
            repository.saveAll(List.of(Mio,MiYa));
        };
        
    }
}
