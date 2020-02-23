package rent_cars_app.model;


import java.time.LocalDateTime;

import rent_cars_app.controller.dto.CarDto;
import rent_cars_app.controller.dto.ConfirmDto;
import rent_cars_app.controller.dto.NewReservationDto;
import rent_cars_app.controller.dto.ReservationDataDto;
import rent_cars_app.controller.dto.SearchByFiltersDto;
import rent_cars_app.controller.dto.SearchDto;
import rent_cars_app.controller.dto.filter.FilterForFrontDto;

public interface IRentCarsService {

	SearchDto search(String city, LocalDateTime start_date_time, LocalDateTime end_date_time, Double min_amount,
			Double max_amount, Boolean ascending, Integer items_on_page, Integer current_page);

	SearchDto searchByCoordinates(Double latitude, Double longitude, Double radius, Integer items_on_page,
			Integer current_page);

	SearchByFiltersDto searchByFilters(String make, String model, String year, String engine, String fuel, String gear,
			String wheels_drive, Integer items_on_page, Integer current_page);

	ReservationDataDto makeReservation(String serial_number, NewReservationDto reservationdto);

	void confirmReservation(ConfirmDto confirmdto);

	Iterable<CarDto> bestBooked();

	Iterable<FilterForFrontDto> getFilters();

	SearchByFiltersDto searchAll(Double latitude, Double longitude, Double radius, String city, LocalDateTime start_date,
			LocalDateTime end_date, Double min_amount, Double max_amount, Boolean ascending,
			String make, String model, String year, String engine, String fuel, String gear,
			String wheels_drive, Integer items_on_page, Integer current_page);


}
