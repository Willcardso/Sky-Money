package br.com.willdcard.sistemafinanceiro_project.controllers;

import br.com.willdcard.sistemafinanceiro_project.dto.DashboardResponseDTO;
import br.com.willdcard.sistemafinanceiro_project.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public DashboardResponseDTO getDashboard() {
        return dashboardService.getDashboard();
    }
}
