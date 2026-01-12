package br.com.willdcard.sistemafinanceiro_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.willdcard.sistemafinanceiro_project.model.Category;
import br.com.willdcard.sistemafinanceiro_project.model.User;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUser(User user);
}
