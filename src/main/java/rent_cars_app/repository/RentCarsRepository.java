package rent_cars_app.repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import rent_cars_app.controller.dto.SearchByFiltersDto;
import rent_cars_app.controller.dto.SearchDto;
import rent_cars_app.controller.dto.filter.FilterForFrontDto;
import rent_cars_app.model.entitys.Car;
import rent_cars_app.model.entitys.Comment;
import rent_cars_app.model.entitys.Reservation;
import rent_cars_app.model.entitys.User;
import rent_cars_app.repository.dao.BestBookedDao;
import rent_cars_app.repository.dao.CarDao;
import rent_cars_app.repository.dao.CommentDao;
import rent_cars_app.repository.dao.LatestCommentDao;
import rent_cars_app.repository.dao.ReservationDao;
import rent_cars_app.repository.dao.UserDao;
import rent_cars_app.repository.map_api.Response;
import rent_cars_app.repository.map_api.Result;

@Repository
public class RentCarsRepository implements IRentCarsRepository {
	String URL1 = "https://maps.googleapis.com/maps/api/place/details/json?place_id=";
	String URL2 = "&fields=address_component&key=AIzaSyAcwhWq3MBKjlSjxOibzDdLN1pvAstmUq4";
	@Autowired
	CarRepository cr;
	@Autowired
	BestBookedRepository bbr;
	@Autowired
	LatestCommentsRepository lcr;
	@Autowired
	FilterRepository fr;

	@Override
	public Optional<User> findUserById(String email) {
		return RepositoryMapper.mapUserDao(cr.findByOwnerEmail(email));
	}

	@Override
	public Optional<Car> findCarById(String serialNumber) {
		Optional<CarDao> c = cr.findById(serialNumber);
		if (c.isPresent()) {
			return RepositoryMapper.mapCarDao(c);
		}
		return Optional.empty();
	}

	@Override
	public void addCar(Car car, String email) {
		UserDao u = cr.findByOwnerEmail(email).stream().findAny().get().getOwner();
		CarDao c = RepositoryMapper.mapCar(car, u, getCity(car.getPick_up_place().getPlace_id()));
		cr.save(c);
		bbr.save(BestBookedDao.builder().carSerialNumber(car.getSerial_number()).bookedSum(0).build());
	}

	@Override
	public boolean deleteCar(String serialNumber) {
		cr.deleteById(serialNumber);
		bbr.deleteById(serialNumber);
		return !cr.existsById(serialNumber);
	}

	@Override
	public void updateCar(Car car, String serialNumber) {
		Optional<CarDao> carDao = cr.findById(serialNumber);
		if (carDao.isPresent()) {
			cr.save(updateCarDao(carDao.get(), car));
			// TODO add new serialNumber
		}
	}

	CarDao updateCarDao(CarDao carDao, Car car) {
		if (carDao.getSerialNumber().equals(car.getSerial_number())) {
			carDao.setMake(car.getMake());
			carDao.setModel(car.getModel());
			carDao.setYear(car.getYear());
			carDao.setEngine(car.getEngine());
			carDao.setFuel(car.getFuel());
			carDao.setGear(car.getGear());
			carDao.setWheelsDrive(car.getWheels_drive());
			carDao.setDoors(car.getDoors());
			carDao.setSeats(car.getSeats());
			carDao.setFuelConsumption(car.getFuel_consumption());
			carDao.setCarClass(car.getCar_class());
			carDao.setAbout(car.getAbout());
			carDao.setFeatures(car.getFeatures());
			carDao.setPricePerDay(car.getPrice_per_day());
			carDao.setDistanceIncluded(car.getDistance_included());
			carDao.setPickUpPlace(RepositoryMapper.mapPickUpPlase(car.getPick_up_place(),
					getCity(car.getPick_up_place().getPlace_id())));
			carDao.setImageUrl(car.getImage_url());
		}
		return carDao;
	}

	private String getCity(String place_id) {
		ResponseEntity<Response> response = new RestTemplate().exchange(URL1 + place_id + URL2, HttpMethod.GET,
				new HttpEntity<String>(new HttpHeaders()), Response.class);
		System.out.println(response.getBody().toString());
		Result result = response.getBody().getResult();
		return result.getAddress_components().stream().filter(a -> a.getTypes().contains("locality")).findFirst().get()
				.getShort_name();
	}

	@Override
	public void registerUser(@NotNull User user) {
		cr.save(CarDao.builder().serialNumber(user.getEmail()).owner(RepositoryMapper.mapUser(user)).build());
	}

	@Override
	public void updateUser(User user) {
		Set<CarDao> set = cr.findByOwnerEmail(user.getEmail());
		if (!set.isEmpty()) {
			cr.saveAll(set.stream().map(c -> updateUserDao(c, user)).collect(Collectors.toSet()));
		}
	}

	CarDao updateUserDao(CarDao c, User u) {
		c.setOwner(UserDao.builder().email(u.getEmail()).password(u.getPassword()).firstName(u.getFirst_name())
				.secondName(u.getSecond_name()).registrationDate(u.getRegistration_date())
				.comments(u.getComments().stream().map(comment -> RepositoryMapper.mapComment(comment))
						.collect(Collectors.toSet()))
				.bookedCars(u.getBooked_cars().stream().map(b -> RepositoryMapper.mapBookedCar(b))
						.collect(Collectors.toSet()))
				.history(u.getHistory_cars().stream().map(b -> RepositoryMapper.mapBookedCar(b))
						.collect(Collectors.toSet()))
				.build());
		return c;
	}

	@Override
	public boolean deleteUser(String email) {
		Set<CarDao> set = cr.findByOwnerEmail(email);
		if (!set.isEmpty()) {
			cr.deleteAll(set);
			return true;
		}
		return false;
	}

	@Override
	public void saveReservation(Reservation reservation) {
		Optional<CarDao> o = cr.findById(reservation.getCar().getSerial_number());
		if (o.isPresent()) {
			cr.save(setReservationToCar(o.get(), reservation));
		}
	}

	private CarDao setReservationToCar(CarDao carDao, Reservation reservation) {
		if (carDao.getSerialNumber().equals(reservation.getCar().getSerial_number())) {
			Set<ReservationDao> setCarsReservationDaos = carDao.getBookedPeriods();
			setCarsReservationDaos.add(RepositoryMapper.mapReservation(reservation));
			carDao.setBookedPeriods(setCarsReservationDaos);
		}
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				BestBookedDao b = bbr.findById(carDao.getSerialNumber()).get();
				b.setBookedSum(b.getBookedSum() + 1);
				bbr.save(b);
			}
		});
		thread.run();
		return carDao;

	}

	@Override
	public Optional<Reservation> findReservationById(String reservationId) {

		return RepositoryMapper.mapUserDaoToReservation(cr.findByBookedPeriodsOrderId(reservationId), reservationId);
	}

	@Override
	public Optional<Set<Comment>> findCommentsLatest() {
		return Optional.ofNullable(
				lcr.findAll().stream().map(c -> RepositoryMapper.mapLatestCommentDao(c)).collect(Collectors.toSet()));
	}

	@Override
	public void updateReservation(Reservation reservation) {
		Optional<CarDao> o = cr.findById(reservation.getCar().getSerial_number());
		if (o.isPresent()) {
			cr.save(updateReservationToCar(o.get(), reservation));
		}
	}

	private CarDao updateReservationToCar(CarDao c, Reservation reservation) {
		if (c.getSerialNumber().equals(reservation.getCar().getSerial_number())) {
			Set<ReservationDao> setCarsReservationDaos = c.getBookedPeriods();
			setCarsReservationDaos.forEach(r -> updateReservationDao(r, reservation.getOrder_id()));
			c.setBookedPeriods(setCarsReservationDaos);
		}
		return c;
	}

	private void updateReservationDao(ReservationDao r, String order_id) {
		if (r.getOrderId().equals(order_id)) {
			r.setPaid(true);
		}
	}

	@Override
	public Optional<Set<Car>> findBestBooked() {

		Iterable<CarDao> set = cr.findAllById(bbr.findAll(PageRequest.of(0, 5)).getContent().stream()
				.map(bbd -> RepositoryMapper.mapBestBookedDao(bbd)).collect(Collectors.toSet()));
		return Optional.ofNullable(StreamSupport.stream(set.spliterator(), false)
				.map(c -> RepositoryMapper.mapCarDao(Optional.ofNullable(c)).get()).collect(Collectors.toSet()));

		/*
		 * return Optional.ofNullable(StreamSupport.stream(cr.findAll().spliterator(),
		 * false) .filter(c -> !c.getSerialNumber().equals(c.getOwner().getEmail()))
		 * .map(c ->
		 * RepositoryMapper.mapCarDaoNonOptional(c)).collect(Collectors.toSet()));
		 */
	}

	@Override
	public Optional<User> findUserByCarId(String serial_number) {
		return RepositoryMapper.mapUserDao(cr.findByOwnerEmail(cr.findById(serial_number).get().getOwner().getEmail()));
	}

	@Override
	public SearchDto search(String city, LocalDateTime start_date_time, LocalDateTime end_date_time, Double min_amount,
			Double max_amount, Boolean ascending, Integer items_on_page, Integer current_page) {
		Direction d;
		if (ascending) {
			d = Direction.ASC;
		} else {
			d = Direction.DESC;
		}
		Pageable p = PageRequest.of(current_page, items_on_page, Sort.by(d, "pricePerDay"));
		Page<CarDao> page = cr.serch(city, LocalDateTime.parse(start_date_time.format(DateTimeFormatter.ISO_DATE_TIME)),
				LocalDateTime.parse(end_date_time.format(DateTimeFormatter.ISO_DATE_TIME)), min_amount, max_amount, p);
		return RepositoryMapper.mapToSearchDto(page, items_on_page, current_page);
	}

	@Override
	public SearchDto searchByCoordinates(Double latitude, Double longitude, Double radius, Integer items_on_page,
			Integer current_page) {
		Pageable pageable = PageRequest.of(current_page, items_on_page, Sort.by("pricePerDay"));
		Page<CarDao> page = cr.findByPickUpPlaceLocationNear(longitude, latitude, radius, pageable);
		return RepositoryMapper.mapToSearchDto(page, items_on_page, current_page);
	}

	@Override
	public SearchByFiltersDto searchByFilters(String make, String model, String year, String engine, String fuel,
			String gear, String wheels_drive, Integer items_on_page, Integer current_page) {
		Pageable p = PageRequest.of(current_page, items_on_page, Sort.by("pricePerDay"));
		Page<CarDao> page = cr.findByFilters(make, model, year, engine, fuel, gear, wheels_drive, p);
		Iterable<FilterForFrontDto> filters = getFilters();
		return RepositoryMapper.mapToSearchByFiltersDto(page, items_on_page, current_page, filters);
	}

	@Override
	public void saveComment(String email, Comment comment) {
		Set<CarDao> set = cr.findByOwnerEmail(email);
		if (!set.isEmpty()) {
			cr.saveAll(set.stream().map(c -> addCommentDao(c, comment)).collect(Collectors.toSet()));
		}
		lcr.save(LatestCommentDao.builder().firstName(comment.getUser().getFirst_name())
				.secondName(comment.getUser().getSecond_name()).postDate(comment.getPost_date()).post(comment.getPost())
				.build());
		if (lcr.count() > 3) {
			lcr.delete(lcr.findAll().get(3));
		}
	}

	CarDao addCommentDao(CarDao c, Comment comment) {
		UserDao u = c.getOwner();
		Set<CommentDao> comments = u.getComments();
		comments.add(RepositoryMapper.mapComment(comment));
		u.setComments(comments);
		c.setOwner(u);
		return c;

	}

	@Override
	public Iterable<FilterForFrontDto> getFilters() {
		return fr.findAll();
	}

	@Override
	public SearchByFiltersDto searchByAll(String city, LocalDateTime start_date_time, LocalDateTime end_date_time,
			Double min_amount, Double max_amount, Boolean ascending, String make, String model, String year,
			String engine, String fuel, String gear, String wheels_drive, Double longitude, Double latitude,
			Double radius, Integer items_on_page, Integer current_page) {
		Direction d;
		if (ascending) {
			d = Direction.ASC;
		} else {
			d = Direction.DESC;
		}
		Pageable p = PageRequest.of(current_page, items_on_page, Sort.by(d, "pricePerDay"));
		Page<CarDao> page = cr.findByAll(city, start_date_time, end_date_time, min_amount, max_amount, make, model,
				year, engine, fuel, gear, wheels_drive, longitude, latitude, radius, p);
		return RepositoryMapper.mapToSearchByFiltersDto(page, items_on_page, current_page, getFilters());
	}

}
