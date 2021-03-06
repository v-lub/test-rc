package rent_cars_app.model.entitys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PicUpPlace {
	String place_id;
	Double latitude;
	Double longitude;
}