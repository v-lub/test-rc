package rent_cars_app.repository.dao.filter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(collection = "filters")
public class FilterForFrontDao {
	//@Id
	String make;
	Integer makeSum;
	String model;
	Integer modelSum;
	String year;
	Integer yearSum;
	String engine;
	Integer engineSum;
	String fuel;
	Integer fuelSum;
	String gear;
	Integer gearSum;
	String wheelsDrive;
	Integer wheelsDriveSum;
}
