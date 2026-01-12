package br.com.willdcard.sistemafinanceiro_project.repository;

import br.com.willdcard.sistemafinanceiro_project.model.Expense;
import br.com.willdcard.sistemafinanceiro_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUser(User user);
    
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e " +
    	       "WHERE e.user = :user AND e.type = 'INCOME'")
    	BigDecimal sumIncomeByUser(@Param("user") User user);

    	@Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e " +
    	       "WHERE e.user = :user AND e.type = 'EXPENSE'")
    	BigDecimal sumExpenseByUser(@Param("user") User user);

    	@Query("SELECT e.category, SUM(e.amount) FROM Expense e " +
    	       "WHERE e.user = :user AND e.type = 'EXPENSE' " +
    	       "GROUP BY e.category")
    	List<Object[]> sumExpenseByCategory(@Param("user") User user);
    	
    	

}
