package f54148.moneybadger.Services.Implementations;

import f54148.moneybadger.DTOs.AddUserDTO;
import f54148.moneybadger.DTOs.ChangePasswordDTO;
import f54148.moneybadger.DTOs.ReportDTO;
import f54148.moneybadger.DTOs.UpdateUserDTO;
import f54148.moneybadger.Entities.Expense;
import f54148.moneybadger.Entities.Income;
import f54148.moneybadger.Entities.User;
import f54148.moneybadger.Exceptions.UserNotFoundException;
import f54148.moneybadger.Repositories.UserRepository;
import f54148.moneybadger.Services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
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
        return opUser.orElse(null);
    }

    public User getUserByEmail(String email) {
        Optional<User> opUser = userRepository.findByEmail(email);
        return opUser.orElse(null);
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

    @Override
    public String updateUser(long id, UpdateUserDTO user) {
        User userFromDB = getUserById(id);
        if(getUserByUsername(user.getUsername())!=null && getUserByUsername(user.getUsername()).getId()!= id){
            return "Username already taken!";
        }
        if(getUserByEmail(user.getEmail())!= null && getUserByEmail(user.getEmail()).getId()!= id){
            return "Email already in use!";
        }
        try {
            userFromDB.setUsername(user.getUsername());
            userFromDB.setName(user.getName());
            userFromDB.setLastName(user.getLastName());
            userFromDB.setEmail(user.getEmail());
            userRepository.save(userFromDB);
            return "";
        }
        catch (Exception e){
            return e.getMessage();
        }

    }

    @Override
    public String changePassword(long id, ChangePasswordDTO password) {

        if(!Objects.equals(password.getNewPassword(), password.getRepeatNewPassword())){
            return "New passwords do not match!";
        }

        User userFromDB = getUserById(id);

        if(!encoder.matches(password.getCurrentPassword(),userFromDB.getPassword())){
            return "Incorrect current password!";
        }
        try {
            userFromDB.setPassword(encoder.encode(password.getNewPassword()));
            userRepository.save(userFromDB);
            return "";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    public List<ReportDTO> getReports(Long userId) {
        User user = getUserById(userId);
        List<ReportDTO> reports = new LinkedList<>();

        for(Income income: user.getIncomes()){
            ReportDTO report = new ReportDTO("income",income.getName(), income.getValue(), 1,income.getValue(),false);
            LocalDate now = LocalDate.now();
            switch (income.getTimeframe()){
                case DAILY:
                    report.setTimes(now.getDayOfMonth());

                    break;
                case WEEKLY:
                    DayOfWeek dayOfTheWeekAdded = income.getDateAdded().getDayOfWeek();
                    report.setTimes((now.getDayOfMonth()-dayOfTheWeekAdded.getValue())/7+1);
                    break;
                case BIWEEKLY:
                    report.setTimes(now.getDayOfMonth()>15?2:1);

                }
            report.setTotal(income.getValue()*report.getTimes());
            reports.add(report);
            }

        for(Expense expense: user.getExpenses()){
            ReportDTO report = new ReportDTO("expense",expense.getName(), expense.getValue(), 1,expense.getValue(),true);
            LocalDate now = LocalDate.now();
            switch (expense.getTimeframe()){
                case DAILY:
                    report.setTimes(now.getDayOfMonth());
                    break;
                case WEEKLY:
                    DayOfWeek dayOfTheWeekAdded = expense.getDateAdded().getDayOfWeek();
                    report.setTimes((now.getDayOfMonth()-dayOfTheWeekAdded.getValue())/7+1);
                    break;
                case BIWEEKLY:
                    report.setTimes(now.getDayOfMonth()>15?2:1);

            }
            report.setTotal(expense.getValue()*report.getTimes());
            reports.add(report);
        }

        return reports;
    }

    @Override
    public String addUser(AddUserDTO user) {

        if(getUserByUsername(user.getUsername())!=null){
            return "Username already taken!";
        }
        if(getUserByEmail(user.getEmail())!= null){
            return "Email already in use!";
        }

        try {
            User userEntity = modelMapper.map(user, User.class);
            userEntity.setAccountNonExpired(true);
            userEntity.setAccountNonLocked(true);
            userEntity.setCredentialsNonExpired(true);
            userEntity.setEnabled(true);
            addUser(userEntity);
            return "";
        }
        catch (Exception e){
            return e.getMessage();
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return user;
    }
}
