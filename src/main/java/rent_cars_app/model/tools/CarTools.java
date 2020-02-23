package rent_cars_app.model.tools;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import rent_cars_app.controller.dto.BookedCarDto;
import rent_cars_app.controller.dto.CarDto;
import rent_cars_app.controller.dto.CarNewDto;
import rent_cars_app.controller.dto.CarWithoutOwnerDto;
import rent_cars_app.controller.dto.OwnerDto;
import rent_cars_app.controller.dto.PicUpPlaceDto;
import rent_cars_app.controller.dto.ReservationDto;
import rent_cars_app.model.entitys.BookedCar;
import rent_cars_app.model.entitys.Car;
import rent_cars_app.model.entitys.PicUpPlace;

public class CarTools {
	
	public static CarWithoutOwnerDto mapToCarWithoutOwnerDto(Car car) {
		Set<ReservationDto> reservationsDto = new HashSet<>();
		if (car.getReservations() != null && !car.getReservations().isEmpty()){
			reservationsDto = car.getReservations().stream()
													.map(RentTools::mapToReservationDto)
													.collect(Collectors.toSet());
		}
		return CarWithoutOwnerDto.builder()
				.serial_number(car.getSerial_number())
				.make(car.getMake())
				.model(car.getModel())
				.year(car.getYear())
				.engine(car.getEngine())
				.fuel(car.getFuel())
				.gear(car.getGear())
				.wheels_drive(car.getWheels_drive())
				.doors(car.getDoors())
				.seats(car.getSeats())
				.fuel_consumption(car.getFuel_consumption())
				.features(car.getFeatures())
				.car_class(car.getCar_class())
				.price_per_day(car.getPrice_per_day())
				.about(car.getAbout())
				.distance_included(car.getDistance_included())
				.pick_up_place(CarTools.mapToPicUpPlaceDto(car.getPick_up_place()))
				.image_url(car.getImage_url())
				.booked_periods(reservationsDto)
				.build();
	}
	
	
	
	public static PicUpPlaceDto mapToPicUpPlaceDto(PicUpPlace pick_up_place) {
		return PicUpPlaceDto.builder()
				.place_id(pick_up_place.getPlace_id())
				.latitude(pick_up_place.getLatitude())
				.longitude(pick_up_place.getLongitude())
				.build();
	}

	public static BookedCarDto mapToBookedCarDto(BookedCar bookedCar) {
		return BookedCarDto.builder()
				.serial_number(bookedCar.getSerial_number())
				.booked_period(RentTools.mapToBookedPeriodDto(bookedCar.getReservation()))
				.build();
	}



	public static Car mapToCarNew(CarNewDto carDto) {
		return Car.builder()
				.serial_number(carDto.getSerial_number())
				.make(carDto.getMake())
				.model(carDto.getModel())
				.year(carDto.getYear())
				.engine(carDto.getEngine())
				.fuel(carDto.getFuel())
				.gear(carDto.getGear())
				.wheels_drive(carDto.getWheels_drive())
				.doors(carDto.getDoors())
				.seats(carDto.getSeats())
				.fuel_consumption(carDto.getFuel_consumption())
				.features(carDto.getFeatures())
				.car_class(carDto.getCar_class())
				.price_per_day(carDto.getPrice_per_day())
				.about(carDto.getAbout())
				.distance_included(carDto.getDistance_included())
				.pick_up_place(CarTools.mapToPicUpPlace(carDto.getPick_up_place()))
				.image_url(carDto.getImage_url())
				.build();
	}


	public static PicUpPlace mapToPicUpPlace(PicUpPlaceDto pick_up_place) {
		return PicUpPlace.builder()
				.place_id(pick_up_place.getPlace_id())
				.latitude(pick_up_place.getLatitude())
				.longitude(pick_up_place.getLongitude())
				.build();
	}



	public static CarDto mapToCarDto(Car car) {
		return CarDto.builder()
				.serial_number(car.getSerial_number())
				.make(car.getMake())
				.model(car.getModel())
				.year(car.getYear())
				.engine(car.getEngine())
				.fuel(car.getFuel())
				.gear(car.getGear())
				.wheels_drive(car.getWheels_drive())
				.doors(car.getDoors())
				.seats(car.getSeats())
				.fuel_consumption(car.getFuel_consumption())
				.features(car.getFeatures())
				.car_class(car.getCar_class())
				.price_per_day(car.getPrice_per_day())
				.distance_included(car.getDistance_included())
				.about(car.getAbout())
				.pick_up_place(CarTools.mapToPicUpPlaceDto(car.getPick_up_place()))
				.image_url(car.getImage_url())
				.owner(OwnerDto.builder()
						.first_name(car.getOwner().getFirst_name())
						.second_name(car.getOwner().getSecond_name())
						.registration_date(car.getOwner().getRegistration_date())
						.build())
				.booked_periods(car.getReservation_periods().stream()
															.map(RentTools::mapToReservationNotForOwnerDto)
															.collect(Collectors.toSet()))
				.build();
	}
}