package com.portfolio.lmsbackend.service.application.general;

import com.portfolio.lmsbackend.dto.general.dashboard.response.GetDashboardResponse;

import java.util.UUID;

public interface DashboardService {
    GetDashboardResponse getDashboard(UUID userId);
}
