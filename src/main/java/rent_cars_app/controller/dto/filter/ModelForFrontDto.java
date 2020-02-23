package rent_cars_app.controller.dto.filter;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "model")
public class ModelForFrontDto {
	String model;
	/*
	 * @JsonIgnore Integer sum;
	 */
	// Set<YearForFrontDto> years;
	Set<YearForFrontDto> years;
}
