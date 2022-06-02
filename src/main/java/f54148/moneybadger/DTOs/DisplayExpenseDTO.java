package f54148.moneybadger.DTOs;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class DisplayExpenseDTO {

    private Long id;
    private String name;
    private Double value;
    private String timeframe;
    private LocalDate dateAdded;
}
