package f54148.moneybadger.Controllers;

import f54148.moneybadger.DTOs.AddIncomeDTO;
import f54148.moneybadger.DTOs.DisplayIncomeDTO;
import f54148.moneybadger.DTOs.EditIncomeDTO;
import f54148.moneybadger.Entities.Timeframe;
import f54148.moneybadger.Services.IncomeService;
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
    @GetMapping(path = "/{userId}/all")
    public String getIncomes(Model model,@PathVariable("userId") Long userId) {
        final List<DisplayIncomeDTO> incomes = incomeService.getIncomes(userId)
                .stream()
                .map(incomeService::convertToDisplayIncomeDTO)
                .collect(Collectors.toList());
        model.addAttribute("incomes", incomes);
        return "/incomes";
    }

    @GetMapping(path = "/add-income/{userId}")
    public String addIncomeView(Model model,@PathVariable("userId") Long userId) {
        List<String> timeFrames = List.of(Arrays.toString(Timeframe.values()).replace("[","").replace("]","").replace(" ","").split(","));

        model.addAttribute("userId", userId);
        model.addAttribute("timeFrames", timeFrames);
        model.addAttribute("income", new AddIncomeDTO());
        return "/add-income";
    }

    @PostMapping(path = "/add-income/{userId}")
    public String addIncomeFromView(@PathVariable("userId") Long userId, @Valid @ModelAttribute("income") AddIncomeDTO income,
                                    BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "add-income";
        }
        if(!incomeService.addIncomeToUser(userId,income)){
            model.addAttribute("customErrorMessage", "Error while adding this income");
            return "add-income";
        };
        return "redirect:/incomes/" + userId+ "/all";
    }

    @GetMapping(path = "/edit-income/{userId}/{incomeId}")
    public String editIncomeView(Model model,@PathVariable("userId") Long userId, @PathVariable("incomeId") Long incomeId) {
        List<String> timeFrames = List.of(Arrays.toString(Timeframe.values()).replace("[","").replace("]","").replace(" ","").split(","));
        EditIncomeDTO dto = incomeService.getEditIncomeDTO(incomeId);
        model.addAttribute("userId", userId);
        model.addAttribute("incomeId", incomeId);
        model.addAttribute("timeFrames", timeFrames);
        model.addAttribute("income", dto);
        model.addAttribute("selectedTimeFrame", dto.getTimeframe());
        return "/edit-income";
    }

    @PostMapping(path = "/edit-income/{userId}/{incomeId}")
    public String editIncomeFromView(@PathVariable("userId") Long userId,  @PathVariable("incomeId") Long incomeId, @Valid @ModelAttribute("income") EditIncomeDTO income,
                                    BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit-income";
        }
        if(!incomeService.editIncome(incomeId,income)){
            model.addAttribute("customErrorMessage", "Error while adding this income");
            return "edit-income";
        };
        return "redirect:/incomes/" + userId+"/all";
    }


    @GetMapping("/delete-income/{userId}/{incomeId}")
    public String processProgramForm(Model model,@PathVariable("userId") Long userId, @PathVariable("incomeId") Long incomeId) {
        incomeService.deleteIncome(incomeId);
        return "redirect:/incomes/" + userId+"/all";
    }
}
