package cibertec.pe.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("rest-employee", r -> r
                        .path("/api/employees/**")
                        .uri("lb://Rest-Employee"))
                .route("saludo-service", r -> r
                        .path("/api/saludo/**")
                        .uri("lb://Saludo-service"))
                .route("employees-api-docs", r -> r
                        .path("/employees/v3/api-docs")
                        .filters(f -> f.rewritePath("/employees/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://Rest-Employee"))
                .route("saludo-api-docs", r -> r
                        .path("/saludo/v3/api-docs")
                        .filters(f -> f.rewritePath("/saludo/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://Saludo-service"))
                .route("cuenta-service", r -> r
                        .path("/api/cuentas/**")
                        .uri("lb://Rest-Cuenta"))
                .route("cuenta-api-docs", r -> r
                        .path("/cuentas/v3/api-docs")
                        .filters(f -> f.rewritePath("/cuentas/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://Rest-Cuenta"))
                .route("identity-service", r -> r
                        .path("/api/auth/**")
                        .uri("lb://Identity-Service"))
                .route("identity-api-docs", r -> r
                        .path("/identity/v3/api-docs")
                        .filters(f -> f.rewritePath("/identity/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://Identity-Service"))
                .build();
    }
}
