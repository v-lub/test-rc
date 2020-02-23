package rent_cars_app.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rent_cars_app.controller.dto.CarDto;
import rent_cars_app.controller.dto.CarNewDto;
import rent_cars_app.model.ICarService;

@RestController
@CrossOrigin
public class CarController {
	@Autowired
	ICarService cs;

	@PostMapping("/car")
	CarDto addCar(@RequestBody CarNewDto carDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return cs.addCar(carDto, auth.getName());
	}

	@PutMapping("/car")
	CarDto updateCar(@RequestParam("serial_number") String serial_number,@RequestBody CarNewDto carDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return cs.updateCar(carDto, serial_number, auth.getName());
	}

	@DeleteMapping("/car")
	void deleteCar(@RequestParam("serial_number") String serial_number) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		cs.deleteCar(serial_number, auth.getName());
	}

	@GetMapping("/car")
	CarDto getCarById(@RequestParam("serial_number") String serial_number) {
		return cs.getCarById(serial_number);
	}

}
