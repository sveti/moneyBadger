package f54148.moneybadger.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EditExpenseDTO {
    private String name;
    private Double value;
    private Integer timeframe;
}
