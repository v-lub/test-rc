package rent_cars_app.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PicUpPlaceDao {
	String placeId;
	String city;
	LocationDao location;
	}
