package f54148.moneybadger.Services;

import f54148.moneybadger.Entities.Expense;
import f54148.moneybadger.Entities.Income;
import f54148.moneybadger.Entities.User;

import java.util.List;


public interface UserService {
     List<User> getUsers();
     User getUserById(Long userId);
     User getUserByUsername(String username);
     User getUserByEmail(String email);
     boolean addUser(User user);
     void updateUser(User user);
     List<Expense> getAllExpenses(Long id);
     List<Income> getAllIncomes(Long id);

     void addIncome(Long userId,Income income);
     void addExpense(Long userId, Expense expense);
}
