package com.marketplace.marketplace_api.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController { // Classe para testar se aplicação está OK

    @GetMapping("/api/health")
    public Map<String, String> health() {
        return Map.of(
                "status", "ok",
                "service", "marketplace-api"
        );
    }

}
