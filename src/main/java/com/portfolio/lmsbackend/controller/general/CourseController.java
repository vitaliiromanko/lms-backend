package com.portfolio.lmsbackend.controller.general;

import com.portfolio.lmsbackend.dto.general.course.response.GetCourseResponse;
import com.portfolio.lmsbackend.service.application.general.CourseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/{courseId}")
    @PreAuthorize("@courseSecurity.canAccess(#authentication, #courseId)")
    public ResponseEntity<MappingJacksonValue> getCourse(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
            @PathVariable UUID courseId
    ) {
        GetCourseResponse response = courseService.getCourse(UUID.fromString(authentication.getName()), courseId);
        return ResponseEntity.ok().body(wrapResponseWithView(response, authentication));
    }
}
