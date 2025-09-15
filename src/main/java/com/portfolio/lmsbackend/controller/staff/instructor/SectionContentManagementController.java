package com.portfolio.lmsbackend.controller.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request.UpdateSectionContentPositionRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request.UpdateSectionContentRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request.UpdateSectionContentSectionRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.sectioncontent.request.UpdateSectionContentStatusRequest;
import com.portfolio.lmsbackend.service.application.staff.instructor.SectionContentManagementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.portfolio.lmsbackend.utils.StringsHelper.SUCCESS_MESSAGE;

@RestController
@RequestMapping("/section/content/manage")
@PreAuthorize("hasRole('INSTRUCTOR')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class SectionContentManagementController {
    private final SectionContentManagementService sectionContentManagementService;

    @PutMapping
    public ResponseEntity<String> update(
            @Valid @RequestBody UpdateSectionContentRequest updateSectionContentRequest
    ) {
        sectionContentManagementService.update(updateSectionContentRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @PutMapping("/position")
    public ResponseEntity<String> updatePosition(
            @Valid @RequestBody UpdateSectionContentPositionRequest updateSectionContentPositionRequest
    ) {
        sectionContentManagementService.updatePosition(updateSectionContentPositionRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @PutMapping("/status")
    public ResponseEntity<String> updateStatus(
            @Valid @RequestBody UpdateSectionContentStatusRequest updateSectionContentStatusRequest
    ) {
        sectionContentManagementService.updateStatus(updateSectionContentStatusRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @PutMapping("/section")
    public ResponseEntity<String> updateSection(
            @Valid @RequestBody UpdateSectionContentSectionRequest updateSectionContentSectionRequest
    ) {
        sectionContentManagementService.updateSection(updateSectionContentSectionRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @DeleteMapping("/{sectionContentId}")
    public ResponseEntity<String> delete(
            @PathVariable UUID sectionContentId
    ) {
        sectionContentManagementService.delete(sectionContentId);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }
}
