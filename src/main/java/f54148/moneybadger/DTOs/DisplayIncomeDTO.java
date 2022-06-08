package f54148.moneybadger.DTOs;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class DisplayIncomeDTO {

    private Long id;
    private String name;
    private Double value;
    private String timeframe;
    private LocalDate dateAdded;

}
