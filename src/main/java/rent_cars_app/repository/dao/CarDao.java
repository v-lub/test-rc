
package rent_cars_app.repository.dao;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@EqualsAndHashCode(of = "serialNumber")
@ToString
@Document(collection = "cars")
public class CarDao {
	@Id
	String serialNumber;
	String make;
	String model;
	String year;
	String engine;
	String fuel;
	String gear;
	String wheelsDrive;
	Integer doors;
	Integer seats;
	Double fuelConsumption;
	Set<String> features;
	String carClass;
	Double pricePerDay;
	Double distanceIncluded;
	String about;
	PicUpPlaceDao pickUpPlace;
	Set<String> imageUrl;
	Set<ReservationDao> bookedPeriods;
	@Indexed
	UserDao owner;
}