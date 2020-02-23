package rent_cars_app.model.tools;


import rent_cars_app.controller.dto.BookedPeriodDto;
import rent_cars_app.controller.dto.ReservationDataDto;
import rent_cars_app.controller.dto.ReservationDto;
import rent_cars_app.controller.dto.ReservationNotForOwnerDto;
import rent_cars_app.controller.dto.SearchByFiltersDto;
import rent_cars_app.controller.dto.SearchDto;
import rent_cars_app.controller.dto.filter.FilterForFrontDto;
import rent_cars_app.model.entitys.Reservation;
import rent_cars_app.model.entitys.ReservationPeriod;

public class RentTools {

	public static BookedPeriodDto mapToBookedPeriodDto(Reservation reservation) {
		return BookedPeriodDto.builder()
				.order_id(reservation.getOrder_id())
				.start_date_time(reservation.getStart_date_time())
				.end_date_time(reservation.getEnd_date_time())
				.paid(reservation.getPaid())
				.amount(reservation.getAmount())
				.booking_date(reservation.getBooking_date())
				.build();
	}

	public static ReservationDto mapToReservationDto(Reservation reservation) {
		return ReservationDto.builder()
				.order_id(reservation.getOrder_id())
				.start_date_time(reservation.getStart_date_time())
				.end_date_time(reservation.getEnd_date_time())
				.paid(reservation.getPaid())
				.amount(reservation.getAmount())
				.booking_date(reservation.getBooking_date())
				.person_who_booked(UserTools.mapToUnregisteredOrRegisteredUserDto(reservation.getPerson_who_reserved()))
				.build();
	}

	public static ReservationNotForOwnerDto mapToReservationNotForOwnerDto(ReservationPeriod rp) {
		return ReservationNotForOwnerDto.builder()
				.start_date_time(rp.getStart_date_time())
				.end_date_time(rp.getEnd_date_time())
				.build();
	}

	public static ReservationDataDto mapToReservationDataDto(Reservation reservation) {
		return ReservationDataDto.builder()
				.order_number(reservation.getOrder_id())
				.amount(reservation.getAmount())
				.booking_date(reservation.getBooking_date())
				.build();
				
	}
	
	public static SearchByFiltersDto createSearchByFilterDto(SearchDto searchDto, Iterable<FilterForFrontDto> filters) {
		return SearchByFiltersDto.builder()
				.current_page(searchDto.getCurrent_page())
				.items_on_page(searchDto.getItems_on_page())
				.items_total(searchDto.getItems_total())
				.cars(searchDto.getCars())
				.filters(filters)
				.build();
	}
}