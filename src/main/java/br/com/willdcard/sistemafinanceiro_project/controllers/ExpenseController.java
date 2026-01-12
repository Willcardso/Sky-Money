package br.com.willdcard.sistemafinanceiro_project.controllers;

import br.com.willdcard.sistemafinanceiro_project.dto.ExpenseRequestDTO;
import br.com.willdcard.sistemafinanceiro_project.model.Expense;
import br.com.willdcard.sistemafinanceiro_project.service.ExpenseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> create(@RequestBody @Valid ExpenseRequestDTO dto) {
        Expense expense = expenseService.save(dto);
        return ResponseEntity.ok(expense);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> list() {
        return ResponseEntity.ok(expenseService.list());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> update(@PathVariable Long id,
                                          @RequestBody @Valid ExpenseRequestDTO dto) {
        Expense expense = expenseService.update(id, dto);
        return ResponseEntity.ok(expense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
