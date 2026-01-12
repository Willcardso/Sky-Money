package br.com.willdcard.sistemafinanceiro_project.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.willdcard.sistemafinanceiro_project.dto.DashboardResponseDTO;
import br.com.willdcard.sistemafinanceiro_project.model.User;
import br.com.willdcard.sistemafinanceiro_project.repository.ExpenseRepository;
import br.com.willdcard.sistemafinanceiro_project.repository.UserRepository;


@Service
public class DashboardService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public DashboardResponseDTO getDashboard() {

        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        BigDecimal income = expenseRepository.sumIncomeByUser(user);
        BigDecimal expense = expenseRepository.sumExpenseByUser(user);
        BigDecimal balance = income.subtract(expense);

        Map<String, BigDecimal> byCategory = new HashMap<>();
        for (Object[] row : expenseRepository.sumExpenseByCategory(user)) {
            byCategory.put(
                    (String) row[0],
                    (BigDecimal) row[1]
            );
        }

        return new DashboardResponseDTO(
                income,
                expense,
                balance,
                byCategory
        );
    }
}