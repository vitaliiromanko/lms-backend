package com.portfolio.lmsbackend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Sort;

public abstract class SearchRequest {
    @Min(0)
    private final Integer pageNumber;

    @Min(1)
    private final Integer pageSize;

    private final Sort.Direction sortDirection;

    private final String search;

    @JsonCreator
    protected SearchRequest(
            @JsonProperty("page_number") Integer pageNumber,
            @JsonProperty("page_size") Integer pageSize,
            @JsonProperty("sort_direction") Sort.Direction sortDirection,
            @JsonProperty("search") String search
    ) {
        this.pageNumber = pageNumber != null ? pageNumber : 0;
        this.pageSize = pageSize != null ? pageSize : 10;
        this.sortDirection = sortDirection != null ? sortDirection : Sort.Direction.DESC;
        this.search = search;
    }

    @JsonProperty("page_number")
    public Integer pageNumber() {
        return pageNumber;
    }

    @JsonProperty("page_size")
    public Integer pageSize() {
        return pageSize;
    }

    @JsonProperty("sort_direction")
    public Sort.Direction sortDirection() {
        return sortDirection;
    }

    @JsonProperty("search")
    public String search() {
        return search;
    }
}
