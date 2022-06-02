package f54148.moneybadger.Services;

import f54148.moneybadger.DTOs.AddIncomeDTO;
import f54148.moneybadger.DTOs.DisplayIncomeDTO;
import f54148.moneybadger.DTOs.EditIncomeDTO;
import f54148.moneybadger.Entities.Income;

import java.util.List;

public interface IncomeService {
    Income getIncomeById(Long incomeId);
    List<Income> getIncomes(long userId);
    boolean addIncomeToUser(Long userId, AddIncomeDTO income);
    boolean deleteIncome(Long incomeId);
    boolean editIncome(Long incomeId, EditIncomeDTO income);
    DisplayIncomeDTO convertToDisplayIncomeDTO(Income income);
    EditIncomeDTO getEditIncomeDTO(Long incomeId);
}
