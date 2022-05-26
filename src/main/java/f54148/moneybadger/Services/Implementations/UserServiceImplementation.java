package f54148.moneybadger.Services.Implementations;

import f54148.moneybadger.Entities.Expense;
import f54148.moneybadger.Entities.Income;
import f54148.moneybadger.Entities.User;
import f54148.moneybadger.Exceptions.UserNotFoundException;
import f54148.moneybadger.Repositories.UserRepository;
import f54148.moneybadger.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder  = new BCryptPasswordEncoder();

    public List<User> getUsers() {
        return new ArrayList<>(userRepository.findAll());
    }

    public User getUserById(Long userId) {
        Optional<User> opUser = userRepository.findById(userId);
        if (opUser.isPresent()) {
            return opUser.get();
        } else {
            throw new UserNotFoundException("Invalid user id " + userId);
        }
    }

    public User getUserByUsername(String username) {
        Optional<User> opUser = userRepository.findByUsername(username);
        if (opUser.isPresent()) {
            return opUser.get();
        } else {
            throw new UserNotFoundException("Invalid username " + username);
        }
    }

    public User getUserByEmail(String email) {
        Optional<User> opUser = userRepository.findByEmail(email);
        if (opUser.isPresent()) {
            return opUser.get();
        } else {
            throw new UserNotFoundException("Invalid email " + email);
        }
    }

    public boolean addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public void updateUser(User user) {

        userRepository.save(user);
    }

    @Override
    public List<Expense> getAllExpenses(Long id) {
        Optional<User> opUser = userRepository.findById(id);
        if (opUser.isPresent()) {
            return opUser.get().getExpenses();
        } else {
            throw new UserNotFoundException("Invalid user id " + id);
        }
    }

    @Override
    public List<Income> getAllIncomes(Long id) {
        Optional<User> opUser = userRepository.findById(id);
        if (opUser.isPresent()) {
            return opUser.get().getIncomes();
        } else {
            throw new UserNotFoundException("Invalid user id " + id);
        }
    }

    @Override
    public void addIncome(Long userId, Income income) {
        User user = getUserById(userId);
        user.getIncomes().add(income);
        userRepository.save(user);
    }

    @Override
    public void addExpense(Long userId, Expense expense) {
        User user = getUserById(userId);
        user.getExpenses().add(expense);
        userRepository.save(user);
    }

}
