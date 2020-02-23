package rent_cars_app.controller.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CarNewDto {
	String serial_number;
	String make;
	String model;
	String year;
	String engine;
	String fuel;
	String gear;
	String wheels_drive;
	Integer doors;
	Integer seats;
	Double fuel_consumption;
	Set<String> features;
	String car_class;
	Double price_per_day;
	Double distance_included;
	String about;
	PicUpPlaceDto pick_up_place;
	Set<String> image_url;
}
