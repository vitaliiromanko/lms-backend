package com.portfolio.lmsbackend.controller.general;

import com.portfolio.lmsbackend.dto.general.dashboard.response.GetDashboardResponse;
import com.portfolio.lmsbackend.service.application.general.DashboardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.portfolio.lmsbackend.controller.ControllerViewHelper.wrapResponseWithView;

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
        return ResponseEntity.ok().body(wrapResponseWithView(response, authentication));
    }
}
