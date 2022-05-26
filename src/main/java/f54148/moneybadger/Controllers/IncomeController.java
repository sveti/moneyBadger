package f54148.moneybadger.Controllers;

import f54148.moneybadger.DTOs.AddIncomeDTO;
import f54148.moneybadger.DTOs.EditIncomeDTO;
import f54148.moneybadger.Entities.Income;
import f54148.moneybadger.Entities.User;
import f54148.moneybadger.Services.IncomeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    @PostMapping(path = "/{userId}/add")
    public @ResponseBody
    String addIncome(@PathVariable("userId") Long userId, @RequestBody AddIncomeDTO income) {

        if (incomeService.addIncomeToUser(userId,income)) {
            return "Added income to user with id " + userId + ".";
        } else {
            return "A problem has occurred!";
        }
    }

    @DeleteMapping(path = "/{incomeId}/delete")
    public @ResponseBody
    String deleteIncome(@PathVariable("incomeId") Long incomeId) {
        if (incomeService.deleteIncome(incomeId)) {
            return "Deleted income with " + incomeId + ".";
        } else {
            return "A problem has occurred!";
        }
    }

    @PutMapping(path = "/{incomeId}/edit")
    public @ResponseBody
    String editIncome(@PathVariable("incomeId") Long incomeId, @RequestBody EditIncomeDTO income) {
        if (incomeService.editIncome(incomeId,income)) {
            return "Edited income with " + incomeId + ".";
        } else {
            return "A problem has occurred!";
        }
    }
}
