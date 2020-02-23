package rent_cars_app.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rent_cars_app.controller.dto.CarDto;
import rent_cars_app.controller.dto.ConfirmDto;
import rent_cars_app.controller.dto.NewReservationDto;
import rent_cars_app.controller.dto.ReservationDataDto;
import rent_cars_app.controller.dto.SearchByFiltersDto;
import rent_cars_app.controller.dto.SearchDto;
import rent_cars_app.controller.dto.filter.FilterForFrontDto;
import rent_cars_app.exceptions.BadRequest;
import rent_cars_app.exceptions.ConflictException;
import rent_cars_app.exceptions.NotFound;
import rent_cars_app.model.entitys.*;
import rent_cars_app.model.tools.CarTools;
import rent_cars_app.model.tools.RentTools;
import rent_cars_app.repository.CustomRepository;
import rent_cars_app.repository.IRentCarsRepository;

@Service
public class RentCarsService implements IRentCarsService {
	@Autowired
	IRentCarsRepository rentCarRepository;
	
	@Autowired
	CustomRepository customRepository;
	
	@Override
	public SearchByFiltersDto searchAll(Double latitude, Double longitude, Double radius, String city, LocalDateTime start_date,
			LocalDateTime end_date, Double min_amount, Double max_amount,Boolean ascending, 
			String make, String model, String year, String engine, String fuel, String gear,
			String wheels_drive, Integer items_on_page, Integer current_page) {
		customRepository.updateFilters();
		if (latitude == null) {
			latitude = 0.0;
		}
		if (longitude == null) {
			longitude = 0.0;
		}
		if (radius == null) {
			radius = Double.MAX_VALUE;
		}
		if (city == null || city.equals("")) {
			city = "";
		}
		
		if (min_amount == null) {
			min_amount = 0.0;
		}
		
		if (max_amount == null) {
			max_amount = Double.MAX_VALUE;
		}
		
		if (ascending == null) {
			ascending = true;
		}
		
		if (start_date == null) {
			start_date = LocalDateTime.now().minusYears(10);
		}
		if (end_date == null) {
			end_date = LocalDateTime.now().minusYears(9);
		}
		if (make == null || make.equals("")) {
			make = "";
		}
		if (model == null || model.equals("")) {
			model = "";
		}
		if (year == null || year.equals("")) {
			year = "";
		}
		if (engine == null || engine.equals("")) {
			engine = "";
		}
		if (fuel == null || fuel.equals("")) {
			fuel = "";
		}
		if (gear == null || gear.equals("")) {
			gear = "";
		}
		if (wheels_drive == null || wheels_drive.equals("")) {
			wheels_drive = "";
		}
		if (current_page == null) {
			current_page = 0;
		}
		if (items_on_page == null || items_on_page == 0) {
			items_on_page = 10;
		}
		
		return rentCarRepository.searchByAll(city, start_date, end_date, min_amount, max_amount, ascending, 
				make, model, year, engine, fuel, gear, wheels_drive, 
				longitude, latitude, radius, 
				items_on_page, current_page);
	}
	
	@Override
	public Iterable<FilterForFrontDto> getFilters() {
		return customRepository.getFilters();
	}
	
	@Override
	public SearchDto search(String city, LocalDateTime start_date_time, LocalDateTime end_date_time, Double min_amount,
			Double max_amount, Boolean ascending, Integer items_on_page, Integer current_page) {
		return rentCarRepository.search(city, start_date_time, end_date_time, min_amount, max_amount, ascending, items_on_page, current_page);
	}

	@Override
	public SearchDto searchByCoordinates(Double latitude, Double longitude, Double radius, Integer items_on_page,
			Integer current_page) {
		return rentCarRepository.searchByCoordinates(latitude, longitude, radius, items_on_page, current_page);
	}

	@Override
	public SearchByFiltersDto searchByFilters(String make, String model, String year, String engine, String fuel,
			String gear, String wheels_drive, Integer items_on_page, Integer current_page) {
		
		return rentCarRepository.searchByFilters(make, model, year, engine, fuel, gear, wheels_drive, items_on_page,
						current_page);
	}

	@Override
	public ReservationDataDto makeReservation(String serial_number, NewReservationDto reservationdto) {
		if (reservationdto.getPerson_who_booked() == null) {
			throw new BadRequest("Person who booked must be set");
		}
		User user = rentCarRepository.findUserById(reservationdto.getPerson_who_booked().getEmail()).orElse(null);
		Car car = rentCarRepository.findCarById(serial_number).orElseThrow(() -> new BadRequest("Car not found"));
		if (user == null) {
			//FIXME
			user = new User(reservationdto.getPerson_who_booked().getEmail(),
					Base64.getEncoder().encodeToString("NOPASSWORDUSER".getBytes()),
					reservationdto.getPerson_who_booked().getFirst_name(),
					reservationdto.getPerson_who_booked().getSecond_name(),
					reservationdto.getPerson_who_booked().getPhone(), null, null, null, null, null);
			rentCarRepository.registerUser(user);
		}
		if (reservationdto.getStart_date_time() == null || reservationdto.getEnd_date_time() == null) {
			throw new BadRequest("Wrong dates");
		}
		if (!carIsFreeForReservation(car.getReservation_periods(), reservationdto.getStart_date_time(), reservationdto.getEnd_date_time())) {
			throw new ConflictException("This car is busy for these dates");
		}
		Reservation reservation = Reservation.builder().start_date_time(reservationdto.getStart_date_time())
				.end_date_time(reservationdto.getEnd_date_time()).paid(false).car(car)
				.amount(amount(car.getPrice_per_day(), reservationdto.getStart_date_time(),
						reservationdto.getEnd_date_time()))
				.booking_date(LocalDateTime.now())
				.person_who_reserved(
						UnregisteredOrRegisteredUser.builder().email(user.getEmail()).first_name(user.getFirst_name())
								.second_name(user.getSecond_name()).phone(user.getPhone()).build())
				.build();
		rentCarRepository.saveReservation(reservation);
		return RentTools.mapToReservationDataDto(reservation);
	}

	private boolean carIsFreeForReservation(Set<ReservationPeriod> reservation_periods, LocalDateTime start_date_time,
			LocalDateTime end_date_time) {
		if(reservation_periods == null || reservation_periods.isEmpty()) {
			return true;
		}
		LocalDate start_date = start_date_time.toLocalDate();
		LocalDate end_date = end_date_time.toLocalDate();

		for (ReservationPeriod rp : reservation_periods) {
			LocalDate rpStartDate = rp.getStart_date_time().toLocalDate();
			LocalDate rpEndDate = rp.getEnd_date_time().toLocalDate();

			if ((start_date.isAfter(rpStartDate) || start_date.isEqual(rpStartDate)) && 
					(start_date.isBefore(rpEndDate) || start_date.isEqual(rpEndDate))) {
				return false;
			}
			if ((end_date.isAfter(rpStartDate) || end_date.isEqual(rpStartDate)) && 
					(end_date.isBefore(rpEndDate) || end_date.isEqual(rpEndDate))) {
				return false;
			}
		}
		return true;
	}
	private Double amount(Double price_per_day, LocalDateTime start_date_time, LocalDateTime end_date_time) {
		LocalDate start_date = start_date_time.toLocalDate();
		LocalDate end_date = end_date_time.toLocalDate();
		if (start_date.equals(end_date)) {
			return price_per_day;
		}
		return Math.abs(ChronoUnit.DAYS.between(start_date, end_date)) * price_per_day;
	}

	@Override
	public void confirmReservation(ConfirmDto confirmdto) {
		if (confirmdto == null) {
			throw new BadRequest("Confirmation data must be not empty");
		}
		Reservation reservation = rentCarRepository.findReservationById(confirmdto.getOrder_number())
				.orElseThrow(() -> new NotFound("Order not found"));
		reservation.setPaid(true);
		rentCarRepository.updateReservation(reservation);
	}

	@Override
	public Iterable<CarDto> bestBooked() {
		Set<Car> cars = rentCarRepository.findBestBooked().orElseThrow(() -> new NotFound("Any booked cars not found"));
		return cars.stream().map(CarTools::mapToCarDto).collect(Collectors.toSet());
	}	
}
