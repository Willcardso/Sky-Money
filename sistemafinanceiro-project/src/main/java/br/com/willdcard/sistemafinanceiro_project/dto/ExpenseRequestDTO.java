package br.com.willdcard.sistemafinanceiro_project.dto;

import br.com.willdcard.sistemafinanceiro_project.model.ExpenseType;
import br.com.willdcard.sistemafinanceiro_project.model.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseRequestDTO {

    @NotNull
    private String description;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private ExpenseType type;

    @NotNull
    private String category;

    @NotNull
    private LocalDate date;

	public void setUser(User user) {
		// TODO Auto-generated method stub
		
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getAmount() {
		// TODO Auto-generated method stub
		return null;
	}

	public ExpenseType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	public LocalDate getDate() {
		// TODO Auto-generated method stub
		return null;
	}

    // getters e setters
}
