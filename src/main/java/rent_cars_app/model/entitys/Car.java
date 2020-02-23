package rent_cars_app.model.entitys;

import java.util.HashSet;
import java.util.Set;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "serial_number")
public class Car {
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
	PicUpPlace pick_up_place;
	Owner owner;
	Set<String> image_url;
	Set<ReservationPeriod> reservation_periods;
	Set<Reservation> reservations;
	
	public Car(String serial_number, String make, String model, String year, String engine, String fuel,
			String gear, String wheels_drive, Integer doors, Integer seats, Double fuel_consumption, Set<String> features, String car_class,
			Double price_per_day, Double distance_included, String about, PicUpPlace pick_up_place, Owner owner,Set<String> image_url,
			Set <ReservationPeriod> reservation_periods, Set<Reservation> reservations) {
		this.serial_number = serial_number;
		this.make = make;
		this.model = model;
		this.year = year;
		this.engine = engine;
		this.fuel = fuel;
		this.gear = gear;
		this.wheels_drive = wheels_drive;
		this.doors = doors;
		this.seats = seats;
		this.fuel_consumption = fuel_consumption;
		this.features = features;
		this.car_class = car_class;
		this.price_per_day = price_per_day;
		this.distance_included = distance_included;
		this.about = about;
		this.pick_up_place = pick_up_place;
		this.owner = owner;
		this.image_url = image_url;
		this.reservation_periods = reservation_periods;
		this.reservations = reservations;
		
		if (this.features == null) {
			this.features = new HashSet<>();
		}
		if (this.image_url == null) {
			this.image_url = new HashSet<>();
		}
		if (this.reservation_periods == null) {
			this.reservation_periods = new HashSet<>();
		}
		if (this.reservations == null) {
			this.reservations = new HashSet<>();
		}
	}
}

