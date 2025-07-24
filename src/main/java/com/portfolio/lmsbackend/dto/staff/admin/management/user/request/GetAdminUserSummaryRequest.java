package com.portfolio.lmsbackend.dto.staff.admin.management.user.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.dto.SearchRequest;
import com.portfolio.lmsbackend.enums.user.StaffRole;
import com.portfolio.lmsbackend.enums.user.UserSortField;
import com.portfolio.lmsbackend.enums.user.UserStatus;
import com.portfolio.lmsbackend.enums.user.UserType;
import com.portfolio.lmsbackend.validation.annotation.StaffRoleRequiresStaffType;
import com.portfolio.lmsbackend.validation.annotation.StaffRoleSortFieldRequiresStaffType;
import org.springframework.data.domain.Sort;

@StaffRoleRequiresStaffType
@StaffRoleSortFieldRequiresStaffType
public class GetAdminUserSummaryRequest extends SearchRequest {
    private final UserSortField sortField;
    private final UserType type;
    private final Boolean emailVerified;
    private final UserStatus status;
    private final StaffRole staffRole;

    @JsonCreator
    public GetAdminUserSummaryRequest(
            @JsonProperty("page_number") Integer pageNumber,
            @JsonProperty("page_size") Integer pageSize,
            @JsonProperty("sort_field") UserSortField sortField,
            @JsonProperty("sort_direction") Sort.Direction sortDirection,
            @JsonProperty("search") String search,
            @JsonProperty("type") UserType type,
            @JsonProperty("email_verified") Boolean emailVerified,
            @JsonProperty("status") UserStatus status,
            @JsonProperty("staff_role") StaffRole staffRole
    ) {
        super(pageNumber, pageSize, sortDirection, search);
        this.sortField = sortField != null ? sortField : UserSortField.CREATED_AT;
        this.type = type;
        this.emailVerified = emailVerified;
        this.status = status;
        this.staffRole = staffRole;
    }

    @JsonProperty("sort_field")
    public UserSortField sortField() {
        return sortField;
    }

    @JsonProperty("type")
    public UserType type() {
        return type;
    }

    @JsonProperty("email_verified")
    public Boolean emailVerified() {
        return emailVerified;
    }

    @JsonProperty("status")
    public UserStatus status() {
        return status;
    }

    @JsonProperty("staff_role")
    public StaffRole staffRole() {
        return staffRole;
    }
}
