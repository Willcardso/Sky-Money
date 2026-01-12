package br.com.willdcard.sistemafinanceiro_project.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.willdcard.sistemafinanceiro_project.model.Category;
import br.com.willdcard.sistemafinanceiro_project.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @GetMapping
    public List<Category> list() {
        return categoryService.list();
    }

}
