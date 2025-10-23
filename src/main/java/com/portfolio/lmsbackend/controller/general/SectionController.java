package com.portfolio.lmsbackend.controller.general;

import com.portfolio.lmsbackend.dto.general.section.response.GetSectionResponse;
import com.portfolio.lmsbackend.service.application.general.SectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.portfolio.lmsbackend.controller.ControllerViewHelper.wrapResponseWithView;

@Tag(
        name = "General / SectionController",
        description = "Endpoints for general operations with sections"
)
@RestController
@RequestMapping("/section")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class SectionController {
    private final SectionService sectionService;

    @Operation(
            summary = "Get section information",
            description = "Endpoint to retrieve information about a section."
    )
    @GetMapping("/{sectionId}")
    @PreAuthorize("@sectionSecurity.canAccess(#authentication, #sectionId)")
    public ResponseEntity<MappingJacksonValue> getSection(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
            @PathVariable UUID sectionId
    ) {
        GetSectionResponse response = sectionService.getSection(UUID.fromString(authentication.getName()), sectionId);
        return ResponseEntity.ok().body(wrapResponseWithView(response, authentication));
    }
}
