package com.portfolio.lmsbackend.controller.general;

import com.portfolio.lmsbackend.dto.Views;
import com.portfolio.lmsbackend.dto.general.dashboard.response.GetDashboardResponse;
import com.portfolio.lmsbackend.service.application.general.DashboardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<MappingJacksonValue> getDashboard(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication
    ) {
        GetDashboardResponse response = dashboardService.getDashboard(UUID.fromString(authentication.getName()));

        MappingJacksonValue wrapper = new MappingJacksonValue(response);
        wrapper.setSerializationView(resolveView(authentication));

        return ResponseEntity.ok(wrapper);
    }

    private Class<?> resolveView(Authentication authentication) {
        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        if (roles.contains("ROLE_ADMINISTRATOR")) {
            return Views.Detailed.class;
        }
        if (roles.contains("ROLE_INSTRUCTOR")) {
            return Views.General.class;
        }
        return Views.Basic.class;
    }
}
