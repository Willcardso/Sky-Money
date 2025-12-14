package br.com.willdcard.sistemafinanceiro_project.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.willdcard.sistemafinanceiro_project.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
