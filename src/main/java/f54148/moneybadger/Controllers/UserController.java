package f54148.moneybadger.Controllers;

import f54148.moneybadger.DTOs.ChangePasswordDTO;
import f54148.moneybadger.DTOs.UpdateUserDTO;
import f54148.moneybadger.Entities.Expense;
import f54148.moneybadger.Entities.Income;
import f54148.moneybadger.Entities.User;
import f54148.moneybadger.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;

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

    @GetMapping(path = "/edit-user-profile/{id}")
    public String editUserProfilePage(Model model,@PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "user-profile";
    }

    @GetMapping(path = "/change-password/{id}")
    public String changePassword(Model model,@PathVariable("id") Long id) {

        model.addAttribute("password", new ChangePasswordDTO());
        return "change-password";
    }
    @PostMapping(path = "/changePassword/{id}")
    public String changeUserPassword(@PathVariable long id, @Valid @ModelAttribute("password") ChangePasswordDTO password,
                           BindingResult bindingResult,Model model) {

        if (bindingResult.hasErrors()) {
            return "change-password";
        }
        String result = userService.changePassword(id,password);
        if(!Objects.equals(result, "")){
            model.addAttribute("customErrorMessage", result);
            return "change-password";
        };
        return "redirect:/";
    }


    @PostMapping(path = "/editUser/{id}")
    public String editUser(@PathVariable long id, @Valid @ModelAttribute("user") UpdateUserDTO user,
                           BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            return "user-profile";
        }
        String result = userService.updateUser(id,user);
        if(!Objects.equals(result, "")){
            model.addAttribute("customErrorMessage", result);
            return "user-profile";
        };
        return "redirect:/";
    }
}
