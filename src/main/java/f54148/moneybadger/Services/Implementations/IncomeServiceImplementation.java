package f54148.moneybadger.Services.Implementations;

import f54148.moneybadger.DTOs.AddIncomeDTO;
import f54148.moneybadger.DTOs.EditIncomeDTO;
import f54148.moneybadger.Entities.Income;
import f54148.moneybadger.Entities.User;
import f54148.moneybadger.Repositories.IncomeRepository;
import f54148.moneybadger.Services.IncomeService;
import f54148.moneybadger.Services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class IncomeServiceImplementation implements IncomeService {
    private final IncomeRepository incomeRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;


    @Override
    public Income getIncomeById(Long incomeId) {
        return incomeRepository.getById(incomeId);
    }

    @Override
    public boolean addIncomeToUser(Long userId, AddIncomeDTO income){
        Income convertedEntity = modelMapper.map(income,Income.class);
        User user = userService.getUserById(userId);
        if(user != null){
            convertedEntity.setUser(user);
            Income savedEntity = incomeRepository.save(convertedEntity);
            userService.addIncome(userId,savedEntity);
            return true;

        }

        return false;
    }

    @Override
    public boolean deleteIncome(Long incomeId) {
        try {
            incomeRepository.delete(getIncomeById(incomeId));
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean editIncome(Long incomeId, EditIncomeDTO income) {
        Income convertedEntity = modelMapper.map(income,Income.class);
        convertedEntity.setId(incomeId);
        convertedEntity.setDateAdded(getIncomeById(incomeId).getDateAdded());
        convertedEntity.setUser(getIncomeById(incomeId).getUser());
        try{
            incomeRepository.save(convertedEntity);
            return true;
        }
        catch (Exception e){
            return false;
        }

    }
}
