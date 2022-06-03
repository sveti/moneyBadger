package f54148.moneybadger.Controllers;

import f54148.moneybadger.DTOs.AddUserDTO;
import f54148.moneybadger.DTOs.UpdateUserDTO;
import f54148.moneybadger.Entities.User;
import f54148.moneybadger.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class IndexController {

    private final UserService userService;

    @GetMapping
    public String getIndex(Model model, Authentication authentication) {

        User principal = (User) authentication.getPrincipal();
        User dbInstance = userService.getUserById(principal.getId());
        model.addAttribute("userName", dbInstance.getName() + " " + dbInstance.getLastName());
        model.addAttribute("userId", principal.getId());

        return "index";
    }

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("loginError", false);
        return "login";
    }

    @GetMapping("registration-success")
    public String successfulRegistration(Model model) {
        return "registration-success";
    }

    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("user",new AddUserDTO());
        return "register";
    }

    @PostMapping("register")
    public String addUser(Model model, @Valid @ModelAttribute("user") AddUserDTO user,
                          BindingResult bindingResult) {
        model.addAttribute("user",new AddUserDTO());

        if (bindingResult.hasErrors()) {
            return "register";
        }
        String result = userService.addUser(user);
        if(!result.equals("")){
            model.addAttribute("customErrorMessage", result);
            model.addAttribute("customErrorMessageFlag", true);
            return "register";
        };
        return "registration-success";

    }



    @GetMapping("login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("unauthorized")
    public String unauthorized(Model model) {
        return "unauthorized";
    }

}