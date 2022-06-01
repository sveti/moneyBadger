package f54148.moneybadger.Services;

import f54148.moneybadger.DTOs.UpdateUserDTO;
import f54148.moneybadger.Entities.Expense;
import f54148.moneybadger.Entities.Income;
import f54148.moneybadger.Entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {
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

    String updateUser(long id, UpdateUserDTO user);
}
