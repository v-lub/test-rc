package rent_cars_app.model.entitys;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class User {
	String email;
	String password;
	String first_name;
	String second_name;
	String phone;
	LocalDate registration_date;
	Set<Comment> comments;
	Set<Car> own_cars;
	Set<BookedCar> booked_cars;
	Set<BookedCar> history_cars;

	public User(String email, String password, String first_name, String second_name,
			String phone, LocalDate registration_date, Set<Comment> comments, Set<Car> own_cars, Set<BookedCar> booked_cars, Set<BookedCar> history_cars) {
		this.email = email;
		this.password = password;
		this.first_name = first_name;
		this.second_name = second_name;
		this.phone = phone;
		this.registration_date = LocalDate.now();
		this.comments = comments;
		this.own_cars = own_cars;
		this.booked_cars = booked_cars;
		this.history_cars = history_cars;
		if (this.comments == null) {
			this.comments = new HashSet<>();
		}
		if (this.own_cars == null) {
			this.own_cars = new HashSet<>();
		}
		if (this.booked_cars == null) {
			this.booked_cars = new HashSet<>();
		}
		if (this.history_cars == null) {
			this.history_cars = new HashSet<>();
		}
	}
}
