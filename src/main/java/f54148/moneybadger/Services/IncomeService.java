package f54148.moneybadger.Services;

import f54148.moneybadger.DTOs.AddIncomeDTO;
import f54148.moneybadger.DTOs.EditIncomeDTO;
import f54148.moneybadger.Entities.Income;

public interface IncomeService {
    Income getIncomeById(Long incomeId);
    boolean addIncomeToUser(Long userId, AddIncomeDTO income);
    boolean deleteIncome(Long incomeId);
    boolean editIncome(Long incomeId, EditIncomeDTO income);
}
