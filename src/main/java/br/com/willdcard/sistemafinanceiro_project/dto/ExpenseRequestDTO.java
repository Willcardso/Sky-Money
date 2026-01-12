package br.com.willdcard.sistemafinanceiro_project.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import br.com.willdcard.sistemafinanceiro_project.model.ExpenseType;

public class ExpenseRequestDTO {

    @NotNull
    private String description;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotNull
    private ExpenseType type;

    @NotNull
    private Long categoryId;

    @NotNull
    private LocalDate date;
    
    @NotNull private String category;

    // ===== GETTERS =====

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public ExpenseType getType() {
        return type;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public LocalDate getDate() {
        return date;
    }
    public String getCategory() { return category; }

    // ===== SETTERS =====

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setType(ExpenseType type) {
        this.type = type;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setCategory(String category) { this.category = category; }
}

