package com.portfolio.lmsbackend.controller.staff.instructor;

import com.portfolio.lmsbackend.dto.staff.instructor.management.section.request.CreateSectionRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.section.request.UpdateSectionPositionRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.section.request.UpdateSectionRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.section.request.UpdateSectionStatusRequest;
import com.portfolio.lmsbackend.service.application.staff.instructor.SectionManagementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.portfolio.lmsbackend.utils.StringsHelper.SUCCESS_MESSAGE;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/section/manage")
@PreAuthorize("hasRole('INSTRUCTOR')")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class SectionManagementController {
    private final SectionManagementService sectionManagementService;

    @PostMapping
    public ResponseEntity<String> create(
            @Valid @RequestBody CreateSectionRequest createSectionRequest
    ) {
        sectionManagementService.create(createSectionRequest);
        return ResponseEntity.status(CREATED).body(SUCCESS_MESSAGE);
    }

    @PutMapping
    public ResponseEntity<String> update(
            @Valid @RequestBody UpdateSectionRequest updateSectionRequest
    ) {
        sectionManagementService.update(updateSectionRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @PutMapping("/position")
    public ResponseEntity<String> updatePosition(
            @Valid @RequestBody UpdateSectionPositionRequest updateSectionPositionRequest
    ) {
        sectionManagementService.updatePosition(updateSectionPositionRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @PutMapping("/status")
    public ResponseEntity<String> updateStatus(
            @Valid @RequestBody UpdateSectionStatusRequest updateSectionStatusRequest
    ) {
        sectionManagementService.updateStatus(updateSectionStatusRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @DeleteMapping("/{sectionId}")
    public ResponseEntity<String> delete(
            @PathVariable UUID sectionId
    ) {
        sectionManagementService.delete(sectionId);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }
}
