package rent_cars_app.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import rent_cars_app.controller.dto.SearchByFiltersDto;
import rent_cars_app.controller.dto.SearchDto;
import rent_cars_app.controller.dto.filter.FilterForFrontDto;
import rent_cars_app.model.entitys.Car;
import rent_cars_app.model.entitys.Comment;
import rent_cars_app.model.entitys.Reservation;
import rent_cars_app.model.entitys.User;

public interface IRentCarsRepository {
	Optional<User> findUserById(String email);

	Optional<Car> findCarById(String serialNumber);

	void addCar(Car car, String email);

	boolean deleteCar(String serialNumber);

	void updateCar(Car car, String serialNumber);

	void registerUser(User user);

	void updateUser(User user);

	boolean deleteUser(String email);

	void saveReservation(Reservation reservation);

	Optional<Reservation> findReservationById(String reservationId);

	void saveComment(String email, Comment comment);

	Optional<Set<Comment>> findCommentsLatest();

	void updateReservation(Reservation reservation);

	Optional<Set<Car>> findBestBooked();

	Optional<User> findUserByCarId(String serial_number);

	SearchDto search(String city, LocalDateTime start_date_time, LocalDateTime end_date_time, Double min_amount,
			Double max_amount, Boolean ascending, Integer items_on_page, Integer current_page);

	SearchDto searchByCoordinates(Double latitude, Double longitude, Double radius, Integer items_on_page,
			Integer current_page);

	SearchByFiltersDto searchByFilters(String make, String model, String year, String engine, String fuel, String gear,
			String wheels_drive, Integer items_on_page, Integer current_page);

	SearchByFiltersDto searchByAll(String city, LocalDateTime start_date_time, LocalDateTime end_date_time,
			Double min_amount, Double max_amount,Boolean ascending, String make, String model, String year, String engine, String fuel,
			String gear, String wheels_drive, Double longitude, Double latitude, Double radius, Integer items_on_page,
			Integer current_page);

	Iterable<FilterForFrontDto> getFilters();
}
