package f54148.moneybadger.Controllers;

import f54148.moneybadger.Entities.Expense;
import f54148.moneybadger.Entities.Income;
import f54148.moneybadger.Entities.User;
import f54148.moneybadger.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @GetMapping(path = "/")
    public @ResponseBody
    List<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "/user/{id}")
    public @ResponseBody User getUser(@PathVariable("id") @Min(1) Long id) {
        return userService.getUserById(id);
    }

    @PostMapping(path = "/add")
    public @ResponseBody String addUser(@RequestBody User user) {

        if (userService.addUser(user)) {
            return "Saved user!";
        } else {
            return "A problem has occurred!";
        }
    }
    @GetMapping(path = "/{id}/expenses/all")
    public @ResponseBody
    List<Expense> getAllExpenses(@PathVariable("id") Long id) {
        return userService.getAllExpenses(id);
    }

    @GetMapping(path = "/{id}/incomes/all")
    public @ResponseBody
    List<Income> getAllIncomes(@PathVariable("id") Long id) {
        return userService.getAllIncomes(id);
    }

}
