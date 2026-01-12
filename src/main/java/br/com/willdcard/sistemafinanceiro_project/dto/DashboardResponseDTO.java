package br.com.willdcard.sistemafinanceiro_project.dto;

import java.math.BigDecimal;
import java.util.Map;

public class DashboardResponseDTO {

    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal balance;
    private Map<String, BigDecimal> expensesByCategory;

    public DashboardResponseDTO(BigDecimal totalIncome,
                                BigDecimal totalExpenses,
                                BigDecimal balance,
                                Map<String, BigDecimal> expensesByCategory) {
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.balance = balance;
        this.expensesByCategory = expensesByCategory;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Map<String, BigDecimal> getExpensesByCategory() {
        return expensesByCategory;
    }
}
