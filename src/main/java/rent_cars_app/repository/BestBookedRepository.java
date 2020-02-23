package rent_cars_app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import rent_cars_app.repository.dao.BestBookedDao;

public interface BestBookedRepository extends MongoRepository<BestBookedDao, String> {

}
