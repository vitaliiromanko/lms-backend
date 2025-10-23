package com.portfolio.lmsbackend.controller.general;

import com.portfolio.lmsbackend.dto.general.sectioncontent.response.GetSectionContentResponse;
import com.portfolio.lmsbackend.service.application.general.SectionContentService;
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
        name = "General / SectionContentController",
        description = "Endpoints for general operations with section contents"
)
@RestController
@RequestMapping("/section/content")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class SectionContentController {
    private final SectionContentService sectionContentService;

    @Operation(
            summary = "Get section content information",
            description = "Endpoint to retrieve information about a section content."
    )
    @GetMapping("/{sectionContentId}")
    @PreAuthorize("@sectionContentSecurity.canAccess(#authentication, #sectionContentId)")
    public ResponseEntity<MappingJacksonValue> getSectionContent(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
            @PathVariable UUID sectionContentId
    ) {
        GetSectionContentResponse response = sectionContentService.getSectionContent(
                UUID.fromString(authentication.getName()), sectionContentId);
        return ResponseEntity.ok().body(wrapResponseWithView(response, authentication));
    }
}
