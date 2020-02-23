package rent_cars_app.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rent_cars_app.controller.dto.CarDto;
import rent_cars_app.controller.dto.ConfirmDto;
import rent_cars_app.controller.dto.NewReservationDto;
import rent_cars_app.controller.dto.ReservationDataDto;
import rent_cars_app.controller.dto.SearchByFiltersDto;
import rent_cars_app.controller.dto.SearchDto;
import rent_cars_app.controller.dto.filter.FilterForFrontDto;
import rent_cars_app.model.IRentCarsService;

@RestController
@CrossOrigin
public class RentCarsController {

	@Autowired
	IRentCarsService rcs;
	
	@GetMapping("/search/all")
	SearchByFiltersDto searchT(@RequestParam(required = false) Double latitude,
							@RequestParam(required = false) Double longitude,
							@RequestParam(required = false) Double radius,
							@RequestParam(required = false) String city,
							@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime start_date, 
							@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime end_date,
							@RequestParam(required = false) Double min_amount,
							@RequestParam(required = false) Double max_amount,
							@RequestParam(required = false) Boolean ascending,
							@RequestParam(required = false) String make, 
							@RequestParam(required = false) String model,
							@RequestParam(required = false) String year, 
							@RequestParam(required = false) String engine, 
							@RequestParam(required = false) String fuel,
							@RequestParam(required = false) String gear, 
							@RequestParam(required = false) String wheels_drive,
							@RequestParam(required = false) Integer items_on_page, 
							@RequestParam(required = false) Integer current_page) {
								
		return rcs.searchAll(latitude, longitude, radius, city, start_date, end_date,
				min_amount, max_amount, ascending,
				make, model, year, engine, fuel,gear, wheels_drive, items_on_page, current_page);
	}
	
	@GetMapping("/search")
	SearchDto searchCarWithoutFilters(@RequestParam("city") String city,
			@RequestParam("start_date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")LocalDateTime start_date_time, @RequestParam("end_date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime end_date_time,
			@RequestParam("min_amount") Double min_amount, @RequestParam("max_amount") Double max_amount,
			@RequestParam("ascending") Boolean ascending, @RequestParam("items_on_page") Integer items_on_page,
			@RequestParam("current_page") Integer current_page) {
		return rcs.search(city, start_date_time, end_date_time, min_amount, max_amount, ascending, items_on_page,
				current_page);
	}

	@GetMapping("/search/geo")
	SearchDto searchByCoordinates(@RequestParam("latitude") Double latitude,
			@RequestParam("longitude") Double longitude, @RequestParam("radius") Double radius,
			@RequestParam("items_on_page") Integer items_on_page, @RequestParam("current_page") Integer current_page) {
		return rcs.searchByCoordinates(latitude, longitude, radius, items_on_page, current_page);
	}

	@GetMapping("/search/filters")
	SearchByFiltersDto searchByFilters(@RequestParam("make") String make, @RequestParam("model") String model,
			@RequestParam("year") String year, @RequestParam("engine") String engine, @RequestParam("fuel") String fuel,
			@RequestParam("gear") String gear, @RequestParam("wheels_drive") String wheels_drive,
			@RequestParam("items_on_page") Integer items_on_page, @RequestParam("current_page") Integer current_page) {
		return rcs.searchByFilters(make, model, year, engine, fuel, gear, wheels_drive, items_on_page, current_page);
	}

	@PostMapping("/car/reservation")
	ReservationDataDto makeReservation(@RequestParam("serial_number") String serial_number,
			@RequestBody NewReservationDto reservationdto) {
		return rcs.makeReservation(serial_number, reservationdto);
	}

	@PostMapping("/reservation/confirm")
	void confirmReservation(@RequestBody ConfirmDto confirmdto) {
		rcs.confirmReservation(confirmdto);
	}

	@GetMapping("/car/best")
	Iterable<CarDto> bestBooked() {
		return rcs.bestBooked();
	}

	@GetMapping("/filters")
	Iterable<FilterForFrontDto> getFilters() {
		return rcs.getFilters();
	}
}
