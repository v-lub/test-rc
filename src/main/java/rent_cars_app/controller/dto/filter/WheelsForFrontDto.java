package rent_cars_app.controller.dto.filter;

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
@EqualsAndHashCode(of="wheels_drive")
public class WheelsForFrontDto {
	String wheels_drive;
	/*@JsonIgnore
	Integer sum;*/
}
