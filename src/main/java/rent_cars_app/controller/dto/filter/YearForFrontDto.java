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
@EqualsAndHashCode(of="year")
public class YearForFrontDto {
String year;
	/*
	 * @JsonIgnore Integer sum;
	 */
Set<EngineForFrontDto> engines;
}
