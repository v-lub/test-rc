package rent_cars_app.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import rent_cars_app.controller.dto.CarDto;
import rent_cars_app.controller.dto.OwnerDto;
import rent_cars_app.controller.dto.PicUpPlaceDto;
import rent_cars_app.controller.dto.ReservationNotForOwnerDto;
import rent_cars_app.controller.dto.SearchByFiltersDto;
import rent_cars_app.controller.dto.SearchDto;
import rent_cars_app.controller.dto.filter.FilterForFrontDto;
import rent_cars_app.model.entitys.BookedCar;
import rent_cars_app.model.entitys.Car;
import rent_cars_app.model.entitys.Comment;
import rent_cars_app.model.entitys.Owner;
import rent_cars_app.model.entitys.PicUpPlace;
import rent_cars_app.model.entitys.Reservation;
import rent_cars_app.model.entitys.ReservationPeriod;
import rent_cars_app.model.entitys.UnregisteredOrRegisteredUser;
import rent_cars_app.model.entitys.User;
import rent_cars_app.repository.dao.BestBookedDao;
import rent_cars_app.repository.dao.BookedCarDao;
import rent_cars_app.repository.dao.BookedPeriodDao;
import rent_cars_app.repository.dao.CarDao;
import rent_cars_app.repository.dao.CommentDao;
import rent_cars_app.repository.dao.LatestCommentDao;
import rent_cars_app.repository.dao.LocationDao;
import rent_cars_app.repository.dao.PicUpPlaceDao;
import rent_cars_app.repository.dao.ReservationDao;
import rent_cars_app.repository.dao.UnregisteredOrRegisteredUserDao;
import rent_cars_app.repository.dao.UserDao;

public class RepositoryMapper {

	public static Optional<User> mapUserDao(Set<CarDao> cars) {
		if (!cars.isEmpty()) {
			UserDao user = cars.stream().findFirst().get().getOwner();
			cars.removeIf(c -> c.getSerialNumber().equals(c.getOwner().getEmail()));
			Set<Car> ownCars = new HashSet<>();
			if (!cars.isEmpty()) {
				ownCars = cars.stream().map(c -> mapCarDaoNonOptional(c)).collect(Collectors.toSet());
			}
			return Optional.of(
					User.builder().email(user.getEmail()).password(user.getPassword()).first_name(user.getFirstName())
							.second_name(user.getSecondName()).registration_date(user.getRegistrationDate())
							.comments(mapSetCommentDao(user.getComments()).get()).own_cars(ownCars)
							.booked_cars(user.getBookedCars().stream().map(c -> RepositoryMapper.mapBookedCarDao(c))
									.collect(Collectors.toSet()))
							.history_cars(user.getHistory().stream().map(c -> RepositoryMapper.mapBookedCarDao(c))
									.collect(Collectors.toSet()))
							.build());
		}
		return Optional.empty();
	}

	public static BookedCar mapBookedCarDao(BookedCarDao c) {
		BookedPeriodDao p = c.getBookedPeriod();
		;
		return BookedCar.builder().serial_number(c.getSerialNumber())
				.reservation(Reservation.builder().order_id(p.getOrderId()).start_date_time(p.getStartDateTime())
						.end_date_time(p.getEndDateTime()).paid(p.getPaid()).amount(p.getAmount())
						.booking_date(p.getBookingDate()).build())
				.build();
	}

	public static BookedCarDao mapBookedCar(BookedCar c) {
		Reservation r = c.getReservation();
		return BookedCarDao.builder().serialNumber(c.getSerial_number())
				.bookedPeriod(BookedPeriodDao.builder().orderId(r.getOrder_id()).startDateTime(r.getStart_date_time())
						.endDateTime(r.getEnd_date_time()).paid(r.getPaid()).amount(r.getAmount())
						.bookingDate(r.getBooking_date()).build())
				.build();
	}

	public static Car mapCarDaoNonOptional(CarDao c) {
		return Car.builder().serial_number(c.getSerialNumber()).make(c.getMake()).model(c.getModel()).year(c.getYear())
				.engine(c.getEngine()).fuel(c.getFuel()).gear(c.getGear()).wheels_drive(c.getWheelsDrive())
				.doors(c.getDoors()).seats(c.getSeats()).fuel_consumption(c.getFuelConsumption())
				.features(c.getFeatures()).car_class(c.getCarClass()).price_per_day(c.getPricePerDay())
				.distance_included(c.getDistanceIncluded()).about(c.getAbout())
				.pick_up_place(mapPickUpPlaseDao(c.getPickUpPlace()))
				.owner(Owner.builder().first_name(c.getOwner().getFirstName()).second_name(c.getOwner().getSecondName())
						.registration_date(c.getOwner().getRegistrationDate()).build())
				.image_url(c.getImageUrl())
				.reservations(c.getBookedPeriods().stream()
						.map(p -> RepositoryMapper.mapReservationDao(Optional.of(p), c.getSerialNumber()).get())
						.collect(Collectors.toSet()))
				.reservation_periods(c.getBookedPeriods().stream()
						.map(p -> RepositoryMapper.mapReservationDaoToReservationPeriod(p)).collect(Collectors.toSet()))
				.build();
	}

	private static PicUpPlace mapPickUpPlaseDao(PicUpPlaceDao p) {
		return PicUpPlace.builder().place_id(p.getPlaceId())
				.longitude(p.getLocation().getCoordinates().get(0))
				.latitude(p.getLocation().getCoordinates().get(1)).build();
	}

	public static Optional<Car> mapCarDao(Optional<CarDao> cDao) {
		if (cDao.isPresent()) {
			return Optional.ofNullable(mapCarDaoNonOptional(cDao.get()));
		}
		return Optional.empty();
	}

	public static UserDao mapUser(User u) {

		return UserDao.builder().email(u.getEmail()).password(u.getPassword()).firstName(u.getFirst_name())
				.secondName(u.getSecond_name()).comments(new HashSet<CommentDao>())
				.registrationDate(u.getRegistration_date()).bookedCars(new HashSet<BookedCarDao>())
				.history(new HashSet<BookedCarDao>()).build();
	}

	public static CarDao mapCar(Car c, UserDao u, String city) {
		return CarDao.builder().serialNumber(c.getSerial_number()).make(c.getMake()).model(c.getModel())
				.year(c.getYear()).engine(c.getEngine()).fuel(c.getFuel()).gear(c.getGear())
				.wheelsDrive(c.getWheels_drive()).doors(c.getDoors()).seats(c.getSeats())
				.fuelConsumption(c.getFuel_consumption()).features(c.getFeatures()).carClass(c.getCar_class())
				.pricePerDay(c.getPrice_per_day()).distanceIncluded(c.getDistance_included()).about(c.getAbout())
				.pickUpPlace(mapPickUpPlase(c.getPick_up_place(), city)).owner(u).imageUrl(c.getImage_url())
				.bookedPeriods(new HashSet<ReservationDao>()).build();
	}

	public static PicUpPlaceDao mapPickUpPlase(PicUpPlace p, String city) {
		ArrayList<Double> list = new ArrayList<>();
		list.add(0, p.getLongitude());
		list.add(1, p.getLatitude());
		return PicUpPlaceDao.builder().placeId(p.getPlace_id()).city(city)
				.location(LocationDao.builder().coordinates(list).type("Point").build()).build();

	}

	public static Optional<Reservation> mapReservationDao(Optional<ReservationDao> reservation, String serialNumber) {
		ReservationDao r = reservation.get();
		return Optional.ofNullable(Reservation.builder().order_id(r.getOrderId()).start_date_time(r.getStartDateTime())
				.end_date_time(r.getEndDateTime()).paid(r.getPaid())
				.car(Car.builder().serial_number(serialNumber).build()).amount(r.getAmount())
				.booking_date(r.getBookingDate())
				.person_who_reserved(UnregisteredOrRegisteredUser.builder().email(r.getPersonWhoBooked().getEmail())
						.first_name(r.getPersonWhoBooked().getFirstName())
						.second_name(r.getPersonWhoBooked().getSecondName()).phone(r.getPersonWhoBooked().getPhone())
						.build())
				.build());
	}

	public static Optional<Reservation> mapUserDaoToReservation(Optional<CarDao> cDao, String reservationId) {
		if (cDao.isPresent()) {
			CarDao car = cDao.get();
			ReservationDao r = car.getBookedPeriods().stream()
					.filter(reservation -> reservation.getOrderId().equals(reservationId)).findAny().get();
			return Optional.ofNullable(Reservation.builder().order_id(r.getOrderId())
					.start_date_time(r.getStartDateTime()).end_date_time(r.getEndDateTime()).paid(r.getPaid())
					.car(Car.builder().serial_number(car.getSerialNumber()).build()).amount(r.getAmount())
					.booking_date(r.getBookingDate())
					.person_who_reserved(UnregisteredOrRegisteredUser.builder().email(r.getPersonWhoBooked().getEmail())
							.first_name(r.getPersonWhoBooked().getFirstName())
							.second_name(r.getPersonWhoBooked().getSecondName())
							.phone(r.getPersonWhoBooked().getPhone()).build())
					.build());
		}
		return Optional.empty();
	}

	public static ReservationPeriod mapReservationDaoToReservationPeriod(ReservationDao r) {
		return ReservationPeriod.builder().bookedId(r.getOrderId()).start_date_time(r.getStartDateTime())
				.end_date_time(r.getEndDateTime()).build();
	}

	public static Optional<Set<Comment>> mapSetCommentDao(Set<CommentDao> findLatestComments) {
		if (findLatestComments != null) {
			return Optional
					.ofNullable(findLatestComments.stream().map(c -> mapCommentDao(c)).collect(Collectors.toSet()));
		}
		return Optional.empty();
	}

	public static Comment mapCommentDao(CommentDao commentDao) {
		return Comment
				.builder().user(Owner.builder().first_name(commentDao.getFirstName())
						.second_name(commentDao.getSecondName()).build())
				.post_date(commentDao.getPostDate()).post(commentDao.getPost()).build();
	}

	public static Optional<Set<Car>> mapSetCarDao(Optional<Set<CarDao>> setCars) {
		if (setCars.isPresent()) {
			return Optional
					.ofNullable(setCars.get().stream().map(c -> mapCarDaoNonOptional(c)).collect(Collectors.toSet()));
		}
		return Optional.empty();
	}

	public static ReservationDao mapReservation(Reservation reservation) {
		return ReservationDao.builder().orderId(reservation.getOrder_id())
				.startDateTime(reservation.getStart_date_time()).endDateTime(reservation.getEnd_date_time())
				.paid(reservation.getPaid()).amount(reservation.getAmount()).bookingDate(reservation.getBooking_date())
				.personWhoBooked(
						UnregisteredOrRegisteredUserDao.builder().email(reservation.getPerson_who_reserved().getEmail())
								.firstName(reservation.getPerson_who_reserved().getFirst_name())
								.secondName(reservation.getPerson_who_reserved().getSecond_name())
								.phone(reservation.getPerson_who_reserved().getPhone()).build())
				.build();
	}

	public static CommentDao mapComment(Comment comment) {
		return CommentDao.builder().firstName(comment.getUser().getFirst_name())
				.secondName(comment.getUser().getSecond_name()).postDate(comment.getPost_date()).post(comment.getPost())
				.build();
	}

	public static Comment mapLatestCommentDao(LatestCommentDao c) {
		return Comment.builder()
				.user(Owner.builder().first_name(c.getFirstName()).second_name(c.getSecondName()).build())
				.post_date(c.getPostDate()).post(c.getPost()).build();
	}

	public static String mapBestBookedDao(BestBookedDao bbd) {
		return bbd.getCarSerialNumber();
	}

	public static SearchDto mapToSearchDto(Page<CarDao> page, Integer items_on_page, Integer current_page) {
		return SearchDto.builder().current_page(current_page).items_on_page(items_on_page)
				.items_total((int) page.getTotalElements())
				.cars(page.get().map(c -> mapCarDaoToCarDto(c)).collect(Collectors.toSet())).build();
	}

	private static CarDto mapCarDaoToCarDto(CarDao car) {
		return CarDto.builder().serial_number(car.getSerialNumber()).make(car.getMake()).model(car.getModel())
				.year(car.getYear()).engine(car.getEngine()).fuel(car.getFuel()).gear(car.getGear())
				.wheels_drive(car.getWheelsDrive()).doors(car.getDoors()).seats(car.getSeats())
				.fuel_consumption(car.getFuelConsumption()).features(car.getFeatures()).car_class(car.getCarClass())
				.price_per_day(car.getPricePerDay()).distance_included(car.getDistanceIncluded()).about(car.getAbout())
				.pick_up_place(PicUpPlaceDto.builder().place_id(car.getPickUpPlace().getPlaceId())
						.latitude(car.getPickUpPlace().getLocation().getCoordinates().get(1))
						.longitude(car.getPickUpPlace().getLocation().getCoordinates().get(0)).build())
				.image_url(car.getImageUrl())
				.owner(OwnerDto.builder().first_name(car.getOwner().getFirstName())
						.second_name(car.getOwner().getSecondName())
						.registration_date(car.getOwner().getRegistrationDate()).build())
				.booked_periods(car.getBookedPeriods().stream().map(RepositoryMapper::mapReservationDaoToReservationDto)
						.collect(Collectors.toSet()))
				.build();
	}

	public static ReservationNotForOwnerDto mapReservationDaoToReservationDto(ReservationDao reservation) {
		return ReservationNotForOwnerDto.builder().start_date_time(reservation.getStartDateTime())
				.end_date_time(reservation.getEndDateTime()).build();
	}

	public static SearchByFiltersDto mapToSearchByFiltersDto(Page<CarDao> page, Integer items_on_page,
			Integer current_page,Iterable<FilterForFrontDto> filters) {
		return SearchByFiltersDto.builder().current_page(current_page).items_on_page(items_on_page)
				.items_total((int) page.getTotalElements())
				.cars(page.get().map(c -> mapCarDaoToCarDto(c)).collect(Collectors.toSet()))
				.filters(filters).build();
	}
}
