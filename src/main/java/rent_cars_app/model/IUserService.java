package rent_cars_app.model;

import rent_cars_app.controller.dto.CarWithoutOwnerDto;
import rent_cars_app.controller.dto.ReservationDto;
import rent_cars_app.controller.dto.UserDto;
import rent_cars_app.controller.dto.UserUpdateOrNewDto;

public interface IUserService {
	UserDto addNewUser(UserUpdateOrNewDto userDto, String email, String password);

	UserDto loginUser(String email);

	UserDto updateUser(UserUpdateOrNewDto userDto, String password, String email);

	void deleteUser(String email);

	CarWithoutOwnerDto ownerGetCarById(String vin, String name);

	Iterable<ReservationDto> ownerGetBookedPeriodsByCarId(String vin, String name);

	Iterable<CarWithoutOwnerDto> ownerGetCars(String email);
}
