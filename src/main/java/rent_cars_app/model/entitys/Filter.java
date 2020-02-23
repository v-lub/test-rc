package rent_cars_app.model.entitys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Filter {
	String make;
	String model;
	String year;
	String engine;
	String fuel;
	String gear;
	String wheels_drive;
	Double fuel_consumption;
}
