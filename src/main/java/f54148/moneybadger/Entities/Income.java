package f54148.moneybadger.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "income")
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private Double value;

    @Enumerated(EnumType.STRING)
    private Timeframe timeframe;

    private LocalDate dateAdded;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user_incomes")
    private User user;

    @Override
    public String toString() {
        return "Income{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", timeframe=" + timeframe +
                ", dateAdded=" + dateAdded +
                '}';
    }
}
