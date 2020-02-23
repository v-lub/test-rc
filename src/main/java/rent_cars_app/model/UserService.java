package rent_cars_app.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rent_cars_app.controller.dto.CarWithoutOwnerDto;
import rent_cars_app.controller.dto.ReservationDto;
import rent_cars_app.controller.dto.UserDto;
import rent_cars_app.controller.dto.UserUpdateOrNewDto;
import rent_cars_app.exceptions.BadRequest;
import rent_cars_app.exceptions.ConflictException;
import rent_cars_app.exceptions.NotFound;
import rent_cars_app.exceptions.Unauthorized;
import rent_cars_app.model.entitys.Car;
import rent_cars_app.model.entitys.Reservation;
import rent_cars_app.model.entitys.User;
import rent_cars_app.model.tools.CarTools;
import rent_cars_app.model.tools.RentTools;
import rent_cars_app.model.tools.UserTools;
import rent_cars_app.repository.IRentCarsRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService{
	@Autowired
	IRentCarsRepository rentCarRepository;
	
	@Override
	public UserDto addNewUser(UserUpdateOrNewDto userDto, String email, String password) {
		if (rentCarRepository.findUserById(email).orElse(null) != null) {
			throw new ConflictException("User exists");
		}
		if (email == null || email.equals("") || password == null || password.equals("")) {
			throw new BadRequest("Email and password must be set");
		}

		if (userDto.getFirst_name() == null || userDto.getFirst_name().equals("")) {
			throw new BadRequest("First name must be set");
		}
		if (userDto.getSecond_name() == null || userDto.getSecond_name().equals("")) {
			throw new BadRequest("Second name must be set");
		}
		User newUser = UserTools.addNewUserMapToUser(userDto, email, password);
		rentCarRepository.registerUser(newUser);
		return UserTools.mapToUserDtoNew(newUser);
	}

	@Override
	public UserDto loginUser(String email) {
		User user = getUser(email);
		return UserTools.mapToUserDto(user);
	}

	@Override
	public UserDto updateUser(UserUpdateOrNewDto userDto, String password, String email) {
		User user = getUser(email);
		if (password != null) {
			if (UserTools.cheackPassword(password)) {
				user.setPassword(password);
			}
		}
		if (userDto.getFirst_name() != null) {
			if (UserTools.cheackFirstName(userDto.getFirst_name())) {
				user.setFirst_name(userDto.getFirst_name());
			}
		}
		if (userDto.getSecond_name() != null) {
			if (UserTools.cheackSecondName(userDto.getSecond_name())) {
				user.setSecond_name(userDto.getSecond_name());
			}
		}
		rentCarRepository.updateUser(user);
		return UserTools.mapToUserDto(user);
	}

	@Override
	public void deleteUser(String email) {
		User user = getUser(email);
		if (user.getOwn_cars() != null && !user.getOwn_cars().isEmpty()) {
			user.getOwn_cars().forEach(c -> rentCarRepository.deleteCar(c.getSerial_number()));
		}
		rentCarRepository.deleteUser(email);
	}

	@Override
	public CarWithoutOwnerDto ownerGetCarById(String serial_number, String email) {
		User user = getUser(email);
		Set<Car> cars = user.getOwn_cars();
		if (cars == null || cars.isEmpty()) {
			throw new ConflictException("Cars not found");
		}
		Car car = cars.stream()
				.filter(c -> c.getSerial_number().equals(serial_number))
				.findFirst()
				.orElseThrow(() -> new NotFound("Car not found"));
		return CarTools.mapToCarWithoutOwnerDto(car);
	}

	@Override
	public Iterable<ReservationDto> ownerGetBookedPeriodsByCarId(String serial_number, String email) {
		User user = getUser(email);
		Car car = rentCarRepository.findCarById(serial_number).orElseThrow(() -> new BadRequest("Car not found"));
		if (!user.getOwn_cars().contains(car)){
			throw new Unauthorized("You don't have permission for this");
		}
		Set<Reservation> reservations = car.getReservations();
		if (reservations == null || reservations.isEmpty()){
			return null;
		}
		return reservations.stream()
							.map(RentTools::mapToReservationDto)
							.collect(Collectors.toSet());
	}

	private User getUser(String email){
		return rentCarRepository.findUserById(email).orElseThrow(() -> new Unauthorized("Bad user"));
	}

	@Override
	public Iterable<CarWithoutOwnerDto> ownerGetCars(String email) {
		User user = getUser(email);
		Set<Car> cars = user.getOwn_cars();
		if (cars == null || cars.isEmpty()) {
			return null;
		}
		return cars.stream()
				.map(CarTools::mapToCarWithoutOwnerDto)
				.collect(Collectors.toSet());
	}
}
