package f54148.moneybadger.Controllers;

import f54148.moneybadger.DTOs.*;
import f54148.moneybadger.Entities.Timeframe;
import f54148.moneybadger.Services.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@AllArgsConstructor
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;


    @PostMapping(path = "/{userId}/add")
    public @ResponseBody
    String addUser(@PathVariable("userId") Long userId, @RequestBody AddExpenseDTO expenseDTO) {

        if (expenseService.addExpense(userId,expenseDTO)) {
            return "Added expense to user with id " + userId + ".";
        } else {
            return "A problem has occurred!";
        }
    }

    @DeleteMapping(path = "/{expenseId}/delete")
    public @ResponseBody
    String deleteExpense(@PathVariable("expenseId") Long expenseId) {
        if (expenseService.deleteExpense(expenseId)) {
            return "Deleted expense with " + expenseId + ".";
        } else {
            return "A problem has occurred!";
        }
    }

    @PutMapping(path = "/{expenseId}/edit")
    public @ResponseBody
    String editExpense(@PathVariable("expenseId") Long expenseId, @RequestBody EditExpenseDTO expenseDTO) {
        if (expenseService.editExpense(expenseId,expenseDTO)) {
            return "Edited expense with " + expenseId + ".";
        } else {
            return "A problem has occurred!";
        }
    }

    @GetMapping(path = "/{userId}/all")
    public String getExpenses(Model model, @PathVariable("userId") Long userId) {
        final List<DisplayExpenseDTO> expenses = expenseService.getExpenses(userId)
                .stream()
                .map(expenseService::convertToDisplayExpenseDTO)
                .collect(Collectors.toList());
        model.addAttribute("expenses", expenses);
        return "/expenses";
    }
    private List<String> getTimeFrames(){
        return List.of(Arrays.toString(Timeframe.values()).replace("[","").replace("]","").replace(" ","").split(","));
    }

    @GetMapping(path = "/add-expense/{userId}")
    public String addExpenseView(Model model,@PathVariable("userId") Long userId) {
        model.addAttribute("userId", userId);
        model.addAttribute("timeFrames", getTimeFrames());
        model.addAttribute("expense", new AddExpenseDTO());
        return "/add-expense";
    }

    @PostMapping(path = "/add-expense/{userId}")
    public String addExpenseFromView(@PathVariable("userId") Long userId, @Valid @ModelAttribute("expense") AddExpenseDTO expense,
                                    BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "add-expense";
        }
        if(!expenseService.addExpense(userId,expense)){
            model.addAttribute("customErrorMessage", "Error while adding this expense");
            return "add-expense";
        };
        return "redirect:/expenses/" + userId+ "/all";
    }

    @GetMapping(path = "/edit-expense/{userId}/{expenseId}")
    public String editExpenseView(Model model,@PathVariable("userId") Long userId, @PathVariable("expenseId") Long expenseId) {
        EditExpenseDTO dto = expenseService.getEditExpenseDTO(expenseId);
        model.addAttribute("userId", userId);
        model.addAttribute("expenseId", expenseId);
        model.addAttribute("timeFrames", getTimeFrames());
        model.addAttribute("expense", dto);
        model.addAttribute("selectedTimeFrame", dto.getTimeframe());
        return "/edit-expense";
    }

    @PostMapping(path = "/edit-expense/{userId}/{expenseId}")
    public String editExpenseFromView(@PathVariable("userId") Long userId,  @PathVariable("expenseId") Long expenseId, @Valid @ModelAttribute("expense") EditExpenseDTO expense,
                                     BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit-expense";
        }
        if(!expenseService.editExpense(expenseId,expense)){
            model.addAttribute("customErrorMessage", "Error while adding this expense");
            return "edit-expense";
        };
        return "redirect:/expenses/" + userId+"/all";
    }


    @GetMapping("/delete-expense/{userId}/{expenseId}")
    public String processProgramForm(Model model,@PathVariable("userId") Long userId, @PathVariable("expenseId") Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return "redirect:/expenses/" + userId+"/all";
    }
}
