package f54148.moneybadger.Controllers;

import f54148.moneybadger.Entities.User;
import f54148.moneybadger.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("unauthorized")
    public String unauthorized(Model model) {
        return "unauthorized";
    }

}