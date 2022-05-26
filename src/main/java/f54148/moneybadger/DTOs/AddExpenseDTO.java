package f54148.moneybadger.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AddExpenseDTO {

    private String name;
    private Double value;
    private Integer timeframe;
    private LocalDate dateAdded;

}
