package rent_cars_app.model;

import rent_cars_app.controller.dto.CarDto;
import rent_cars_app.controller.dto.CarNewDto;

public interface ICarService {
	CarDto addCar(CarNewDto carDto, String email);
	
	CarDto updateCar(CarNewDto carDto, String serial_number, String email);
	
	void deleteCar(String serial_number, String email);
	
	CarDto getCarById(String serial_number);
}
