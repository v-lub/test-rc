package rent_cars_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rent_cars_app.configuration.Security;
import rent_cars_app.controller.dto.CarWithoutOwnerDto;
import rent_cars_app.controller.dto.ReservationDto;
import rent_cars_app.controller.dto.UserDto;
import rent_cars_app.controller.dto.UserUpdateOrNewDto;
import rent_cars_app.model.IUserService;

@RestController
@CrossOrigin
public class UserController {
	@Autowired
	IUserService us;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostMapping("/registration")
	UserDto addNewUser(@RequestBody UserUpdateOrNewDto userDto, @RequestHeader("Authorization") String token) {
		String[] credentials = Security.credentials(token);
		return us.addNewUser(userDto, credentials[0], passwordEncoder.encode(credentials[1]));
	}

	@GetMapping("/user/login")
	UserDto loginUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return us.loginUser(auth.getName());
	}

	@PutMapping("/user")
	UserDto updateUser(@RequestBody UserUpdateOrNewDto userDto, @RequestHeader(value = "X-New-Password", required = false) String password) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (password != null && !password.isEmpty()) {
			password = passwordEncoder.encode(Security.passwordCode(password));
		}
		return us.updateUser(userDto, password, auth.getName());
	}

	@DeleteMapping("/user")
	void deleteUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		us.deleteUser(auth.getName());
	}

	@GetMapping("/user/cars")
	Iterable<CarWithoutOwnerDto> ownerGetCars() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return us.ownerGetCars(auth.getName());
	}

	@GetMapping("/user/cars/car")
	CarWithoutOwnerDto ownerGetCarById(@RequestParam("serial_number") String serial_number) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return us.ownerGetCarById(serial_number, auth.getName());
	}

	@GetMapping("/user/cars/periods")
	Iterable<ReservationDto> ownerGetBookedPeriodsByCarId(@RequestParam("serial_number") String serial_number) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return us.ownerGetBookedPeriodsByCarId(serial_number, auth.getName());
	}

}
