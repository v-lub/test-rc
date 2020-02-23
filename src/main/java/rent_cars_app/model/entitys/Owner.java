package rent_cars_app.model.entitys;

import lombok.Builder;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Owner {
	String first_name;
	String second_name;
	LocalDate registration_date;
}
