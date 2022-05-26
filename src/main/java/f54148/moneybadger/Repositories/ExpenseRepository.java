package f54148.moneybadger.Repositories;

import f54148.moneybadger.Entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
