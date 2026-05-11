package com.example;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@ApplicationPath("/")
@OpenAPIDefinition(
        info = @Info(
                title = "Demo OpenAPI — Fruits",
                version = "1.0.0",
                description = "Ejemplo Quarkus + SmallRye OpenAPI + Swagger UI"))
public class FruitApplication extends Application {
}
