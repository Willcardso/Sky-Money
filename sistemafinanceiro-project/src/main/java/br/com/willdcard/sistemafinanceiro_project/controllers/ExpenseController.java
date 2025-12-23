package br.com.willdcard.sistemafinanceiro_project.controllers;

import br.com.willdcard.sistemafinanceiro_project.model.Expense;
import br.com.willdcard.sistemafinanceiro_project.service.ExpenseService;
import br.com.willdcard.sistemafinanceiro_project.dto.ExpenseRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public Expense create(@RequestBody @Valid ExpenseRequestDTO dto) {
        return expenseService.save(dto);
    
    }

    @GetMapping
    public List<Expense> list() {
        return expenseService.list();
    }
}
