package f54148.moneybadger.Services.Implementations;

import f54148.moneybadger.DTOs.AddExpenseDTO;
import f54148.moneybadger.DTOs.EditExpenseDTO;
import f54148.moneybadger.Entities.Expense;
import f54148.moneybadger.Entities.User;
import f54148.moneybadger.Repositories.ExpenseRepository;
import f54148.moneybadger.Services.ExpenseService;
import f54148.moneybadger.Services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ExpenseServiceImplementation implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;


    @Override
    public Expense getExpenseById(Long expenseId) {
        return expenseRepository.getById(expenseId);
    }

    @Override
    public boolean addExpense(Long userId, AddExpenseDTO expenseDTO) {
        Expense convertedEntry = modelMapper.map(expenseDTO,Expense.class);
        User user = userService.getUserById(userId);
        convertedEntry.setUser(user);
        if(user != null){
            Expense savedEntity = expenseRepository.save(convertedEntry);
            userService.addExpense(userId,savedEntity);
            return true;

        }

        return false;
    }

    @Override
    public boolean deleteExpense(Long expenseId) {
        try {
            expenseRepository.delete(getExpenseById(expenseId));
            return true;
        }
        catch (Exception e){

            return false;
        }
    }

    @Override
    public boolean editExpense(Long expenseId, EditExpenseDTO expenseDTO) {

        Expense convertedEntity = modelMapper.map(expenseDTO,Expense.class);
        convertedEntity.setId(expenseId);
        convertedEntity.setDateAdded(getExpenseById(expenseId).getDateAdded());
        convertedEntity.setUser(getExpenseById(expenseId).getUser());
        try{
            expenseRepository.save(convertedEntity);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
