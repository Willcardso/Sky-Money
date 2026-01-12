package br.com.willdcard.sistemafinanceiro_project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import br.com.willdcard.sistemafinanceiro_project.dto.ExpenseRequestDTO;
import br.com.willdcard.sistemafinanceiro_project.model.Category;
import br.com.willdcard.sistemafinanceiro_project.model.Expense;
import br.com.willdcard.sistemafinanceiro_project.model.User;
import br.com.willdcard.sistemafinanceiro_project.repository.CategoryRepository;
import br.com.willdcard.sistemafinanceiro_project.repository.ExpenseRepository;
import br.com.willdcard.sistemafinanceiro_project.repository.UserRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CategoryRepository CategoryRepository;


    private User getAuthenticatedUser() {
        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Expense save(ExpenseRequestDTO dto) {
        User user = getAuthenticatedUser();

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
        User user = getAuthenticatedUser();
        return expenseRepository.findByUser(user);
    }

    public Expense update(Long id, ExpenseRequestDTO dto) {
        User user = getAuthenticatedUser();

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Acesso negado");
        }

        Category category = CategoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());
        expense.setType(dto.getType());
        expense.setCategory(category);
        expense.setDate(dto.getDate());

        return expenseRepository.save(expense);
    }



    public void delete(Long id) {
        User user = getAuthenticatedUser();

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Acesso negado");
        }

        expenseRepository.delete(expense);
    }
}


