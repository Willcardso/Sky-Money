package br.com.willdcard.sistemafinanceiro_project.service;

import br.com.willdcard.sistemafinanceiro_project.dto.ExpenseRequestDTO;
import br.com.willdcard.sistemafinanceiro_project.model.Expense;
import br.com.willdcard.sistemafinanceiro_project.model.User;
import br.com.willdcard.sistemafinanceiro_project.repository.ExpenseRepository;
import br.com.willdcard.sistemafinanceiro_project.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public Expense save(ExpenseRequestDTO dto) {

        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Expense expense = new Expense();
        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());
        expense.setType(dto.getType());
        expense.setCategory(dto.getCategory());
        expense.setDate(dto.getDate());
        expense.setUser(user);

        return expenseRepository.save(expense);
    }

	public List<Expense> list() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
