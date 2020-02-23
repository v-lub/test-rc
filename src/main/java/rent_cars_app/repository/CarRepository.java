package rent_cars_app.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import rent_cars_app.repository.dao.CarDao;

public interface CarRepository extends MongoRepository<CarDao, String> {
	Set<CarDao> findByOwnerEmail(String email);

	Optional<CarDao> findByBookedPeriodsOrderId(String orderId);

	@Query("{'pickUpPlace.location': {$near: {$geometry:{type:'Point',coordinates: [ ?0 , ?1 ]},$maxDistance: ?2 }} }")
	Page<CarDao> findByPickUpPlaceLocationNear(Double longitude, Double latitude, Double radius, Pageable p);

	@Query("{$and:[{'pickUpPlace.city':{$regex:?0} }, "
			+ "{$or:[{'bookedPeriods': { $size: 0 }},{'bookedPeriods':{ $not:{$elemMatch:{'startDateTime': { $gt: ?1,$gt:?2 }}}}},{'bookedPeriods':{ $not:{$elemMatch:{'endDateTime': { $lt: ?1,$lt:?2}}}}}]},"
			+ "{'pricePerDay':{$gt:?3 }}," + "{'pricePerDay':{$lt:?4 }} ]}")
	Page<CarDao> serch(String city, LocalDateTime start_date_time, LocalDateTime end_date_time, Double min_amount,
			Double max_amount, Pageable p);

	@Query("{'make':{$regex:?0}, 'model':{$regex:?1}, 'year':{$regex:?2}, 'engine':{$regex:?3}, 'fuel':{$regex:?4}, 'gear':{$regex:?5}, 'wheelsDrive':{$regex:?6}}")
	Page<CarDao> findByFilters(String make, String model, String year, String engine, String fuel, String gear,
			String wheels_drive, Pageable p);

	@Query("{$and:[{'pickUpPlace.city':{$regex:?0} }, "
			+ "{$or:[{'bookedPeriods': { $size: 0 }},{'bookedPeriods':{ $not:{$elemMatch:{'startDateTime': { $gt: ?1,$gt:?2 }}}}},{'bookedPeriods':{ $not:{$elemMatch:{'endDateTime': { $lt: ?1,$lt:?2}}}}}]},"
			+ "{'pricePerDay':{$gt:?3 }}," + "{'pricePerDay':{$lt:?4 }}, "
			+ "{'make':{$regex:?5 }}, {'model':{$regex:?6 }}, {'year':{$regex:?7 }},{'engine':{$regex:?8 }}, {'fuel':{$regex:?9 }}, {'gear':{$regex:?10 }}, {'wheelsDrive':{$regex:?11 }}, "
			+ "{'pickUpPlace.location': {$near: {$geometry:{type:'Point',coordinates: [ ?12 , ?13 ]},$maxDistance: ?14 }} }]}")
	Page<CarDao> findByAll(String city, LocalDateTime start_date_time, LocalDateTime end_date_time, Double min_amount,
			Double max_amount, String make, String model, String year, String engine, String fuel, String gear,
			String wheels_drive, Double longitude, Double latitude, Double radius, Pageable p);

}
