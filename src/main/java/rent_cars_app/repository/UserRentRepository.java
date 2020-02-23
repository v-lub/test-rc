package rent_cars_app.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import rent_cars_app.repository.dao.UserDao;

//@Repository
public interface UserRentRepository {//extends MongoRepository<UserDao, String>   {

	Optional<UserDao> findByOwnCarsSerialNumber(String serialNamber);

	Optional<UserDao> findByOwnCarsBookedPeriodsOrderId(String reservationId);
	/*
	 * Set<CommentDao> findLatestComments(Class<CommentDao> commentClass);
	 * 
	 * Set<UserDao> findCarsWithoutFilters(String country, String city, Long
	 * start_date_time, Long end_date_time, Double min_amount, Double max_amount);
	 * 
	 * Set<UserDao> findBestBooked();
	 */
}
