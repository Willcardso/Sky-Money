package br.com.willdcard.sistemafinanceiro_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.willdcard.sistemafinanceiro_project.model.Category;
import br.com.willdcard.sistemafinanceiro_project.model.User;
import br.com.willdcard.sistemafinanceiro_project.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> findByUser(User user) {
        return repository.findByUser(user);
    }

    public Category save(Category category) {
        return repository.save(category);
    }

	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Category> list() {
		// TODO Auto-generated method stub
		return null;
	}
}

