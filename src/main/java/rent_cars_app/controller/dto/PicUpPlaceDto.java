package rent_cars_app.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PicUpPlaceDto {
	String place_id;
	Double latitude;
	Double longitude;
	
}
