package br.com.willdcard.sistemafinanceiro_project.dto;

import java.math.BigDecimal;
import java.util.Map;

public class DashboardResponseDTO {

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal balance;
    private Map<String, BigDecimal> expenseByCategory;

    public DashboardResponseDTO(
            BigDecimal totalIncome,
            BigDecimal totalExpense,
            BigDecimal balance,
            Map<String, BigDecimal> expenseByCategory) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = balance;
        this.expenseByCategory = expenseByCategory;
    }

    // getters
}
