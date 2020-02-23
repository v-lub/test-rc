package rent_cars_app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import rent_cars_app.repository.dao.LatestCommentDao;

public interface LatestCommentsRepository extends MongoRepository<LatestCommentDao, String> {
	
}
