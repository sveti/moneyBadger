package f54148.moneybadger.Services;

import f54148.moneybadger.DTOs.AddExpenseDTO;
import f54148.moneybadger.DTOs.EditExpenseDTO;
import f54148.moneybadger.Entities.Expense;


public interface ExpenseService {
    Expense getExpenseById(Long expenseId);
    boolean addExpense(Long userId, AddExpenseDTO expenseDTO);
    boolean deleteExpense(Long expenseId);
    boolean editExpense(Long expenseId, EditExpenseDTO expenseDTO);
}
