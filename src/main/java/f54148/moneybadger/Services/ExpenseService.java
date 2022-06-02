package f54148.moneybadger.Services;

import f54148.moneybadger.DTOs.*;
import f54148.moneybadger.Entities.Expense;
import f54148.moneybadger.Entities.Income;

import java.util.Arrays;
import java.util.List;


public interface ExpenseService {
    Expense getExpenseById(Long expenseId);
    boolean addExpense(Long userId, AddExpenseDTO expenseDTO);
    boolean deleteExpense(Long expenseId);
    boolean editExpense(Long expenseId, EditExpenseDTO expenseDTO);
    List<Expense> getExpenses(long userId);
    DisplayExpenseDTO convertToDisplayExpenseDTO(Expense expense);
    EditExpenseDTO getEditExpenseDTO(Long expenseId);
}
