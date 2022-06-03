package f54148.moneybadger.DTOs;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReportDTO {

    private String type;
    private String description;
    private Double amount;
    private Integer times;
    private Double total;
    private Boolean isRed;

}
