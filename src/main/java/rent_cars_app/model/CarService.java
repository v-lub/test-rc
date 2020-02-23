package rent_cars_app.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rent_cars_app.controller.dto.CarDto;
import rent_cars_app.controller.dto.CarNewDto;
import rent_cars_app.exceptions.BadRequest;
import rent_cars_app.exceptions.ConflictException;
import rent_cars_app.exceptions.Unauthorized;
import rent_cars_app.model.entitys.Car;
import rent_cars_app.model.entitys.Owner;
import rent_cars_app.model.entitys.User;
import rent_cars_app.model.tools.CarTools;
import rent_cars_app.repository.CustomRepository;
import rent_cars_app.repository.IRentCarsRepository;

@Service
public class CarService implements ICarService {
	@Autowired
	IRentCarsRepository rentCarRepository;
	
	@Autowired
	CustomRepository customRepository;
	
	@Override
	public CarDto addCar(CarNewDto carDto, String email) {
		User user = rentCarRepository.findUserById(email).orElseThrow(() -> new Unauthorized("Bad user"));
		if (rentCarRepository.findCarById(carDto.getSerial_number()).orElse(null) != null) {
			throw new ConflictException("Car exists");
		}
		if (carDto.getSerial_number() == null || carDto.getSerial_number().equals("")) {
			throw new BadRequest("Serial number must be set");
		}
		if (carDto.getMake() == null || carDto.getMake().equals("")) {
			throw new BadRequest("Make must be set");
		}
		if (carDto.getModel() == null || carDto.getModel().equals("")) {
			throw new BadRequest("Model must be set");
		}
		if (carDto.getYear() == null || carDto.getYear().equals("")) {
			throw new BadRequest("Year must be set");
		}
		if (carDto.getEngine() == null || carDto.getEngine().equals("")) {
			throw new BadRequest("Engine must be set");
		}
		if (carDto.getFuel() == null || carDto.getFuel().equals("")) {
			throw new BadRequest("Fuel must be set");
		}
		if (carDto.getGear() == null || carDto.getGear().equals("")) {
			throw new BadRequest("Gear must be set");
		}
		if (carDto.getWheels_drive() == null || carDto.getWheels_drive().equals("")) {
			throw new BadRequest("Wheels drive must be set");
		}
		if (carDto.getPick_up_place().getPlace_id() == null || carDto.getPick_up_place().getPlace_id() == "") {
			throw new BadRequest("Place_id must be set");
		}
		if (carDto.getPick_up_place().getLatitude() == null) {
			throw new BadRequest("Latitude must be set");
		}
		if (carDto.getPick_up_place().getLongitude() == null) {
			throw new BadRequest("Longitude must be set");
		}
		Car newCar = CarTools.mapToCarNew(carDto);
		newCar.setOwner(Owner.builder()
							.first_name(user.getFirst_name())
							.second_name(user.getSecond_name())
							.registration_date(user.getRegistration_date())
							.build());
		rentCarRepository.addCar(newCar, email);
		customRepository.updateFilters();
		return CarTools.mapToCarDto(newCar);
	}
	
	@Override
	public CarDto updateCar(CarNewDto carDto, String serial_number, String email) {
		User user = rentCarRepository.findUserById(email).orElseThrow(() -> new Unauthorized("Bad user"));
		Car car = rentCarRepository.findCarById(serial_number).orElseThrow(() -> new BadRequest("Car not found"));
		boolean changed = false;
		if (user.getOwn_cars() == null || !user.getOwn_cars().contains(car)) {
			throw new Unauthorized("This car is not your");
		}
		
		if (carDto.getMake() != null) {
			car.setMake(carDto.getMake());
			changed = true;
		}
		if (carDto.getModel() != null) {
			car.setModel(carDto.getModel());
			changed = true;
		}
		if (carDto.getYear() != null) {
			car.setYear(carDto.getYear());
			changed = true;
		}
		if (carDto.getEngine() != null) {
			car.setEngine(carDto.getEngine());
			changed = true;
		}
		if (carDto.getFuel() != null) {
			car.setFuel(carDto.getFuel());
			changed = true;
		}
		if (carDto.getGear() != null) {
			car.setGear(carDto.getGear());
			changed = true;
		}
		if (carDto.getWheels_drive() != null) {
			car.setWheels_drive(carDto.getWheels_drive());
			changed = true;
		}
		if (carDto.getDoors() != null) {
			car.setDoors(carDto.getDoors());
			changed = true;
		}		
		if (carDto.getSeats() != null) {
			car.setSeats(carDto.getSeats());
			changed = true;
		}
		if (carDto.getFuel_consumption() != null) {
			car.setFuel_consumption(carDto.getFuel_consumption());
			changed = true;
		}
		if (carDto.getFeatures() != null) {
			car.setFeatures(carDto.getFeatures());
			changed = true;
		}
		if (carDto.getCar_class() != null) {
			car.setCar_class(carDto.getCar_class());
			changed = true;
		}
		if (carDto.getPrice_per_day() != null) {
			car.setPrice_per_day(carDto.getPrice_per_day());
			changed = true;
		}
		if (carDto.getDistance_included() != null) {
			car.setDistance_included(carDto.getDistance_included());
			changed = true;
		}
		if (carDto.getAbout() != null) {
			car.setAbout(carDto.getAbout());
			changed = true;
		}
		if (carDto.getPick_up_place() != null) {
			car.setPick_up_place(CarTools.mapToPicUpPlace(carDto.getPick_up_place()));
			changed = true;
		}
		if (carDto.getImage_url() != null) {
			car.setImage_url(carDto.getImage_url());
			changed = true;
		}
		if (changed == true) {
			rentCarRepository.updateCar(car, car.getSerial_number());
			customRepository.updateFilters();
		}
		return CarTools.mapToCarDto(car);
	}

	@Override
	public void deleteCar(String serial_number, String email) {
		User user = rentCarRepository.findUserById(email).orElseThrow(() -> new Unauthorized("Bad user"));
		Car car = rentCarRepository.findCarById(serial_number).orElseThrow(() -> new BadRequest("Car not found"));
		if (user.getOwn_cars() == null || !user.getOwn_cars().contains(car)) {
			throw new Unauthorized("This car is not your");
		}
		rentCarRepository.deleteCar(serial_number);
		customRepository.updateFilters();
	}

	@Override
	public CarDto getCarById(String serial_number) {
		Car car = rentCarRepository.findCarById(serial_number).orElseThrow(() -> new BadRequest("Car not found"));
		return CarTools.mapToCarDto(car);
	}
}
