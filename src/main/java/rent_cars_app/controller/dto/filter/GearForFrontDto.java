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
@EqualsAndHashCode(of="gear")
public class GearForFrontDto {
String gear;
/*@JsonIgnore
Integer sum;*/
Set<WheelsForFrontDto> wheels_drives;
}
