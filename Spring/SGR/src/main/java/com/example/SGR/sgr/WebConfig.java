package com.example.SGR.sgr;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permite todas las rutas
                .allowedOrigins("*") // Permite desde todos los origenes
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Permite los métodos HTTP especificados
                .allowedHeaders("*"); // Permite todos los headers
    }
}
