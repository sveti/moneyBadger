package f54148.moneybadger.Controllers;

import f54148.moneybadger.DTOs.AddExpenseDTO;
import f54148.moneybadger.DTOs.EditExpenseDTO;
import f54148.moneybadger.Services.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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

}
