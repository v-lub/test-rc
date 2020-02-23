package rent_cars_app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import rent_cars_app.controller.dto.filter.FilterForFrontDto;
import rent_cars_app.repository.dao.CarDao;

@Repository
public class CustomRepository {
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	public void updateFilters() {
		TypedAggregation<CarDao> filtersAggregation = 
				Aggregation.newAggregation(CarDao.class,
						Aggregation.match(Criteria.where("make").ne(null)),
						
						Aggregation.group("$make", "$model", "$year", "$engine", "$fuel", "$gear")
									.addToSet("$wheelsDrive").as("wheels_drives"),
									
						Aggregation.group("$make", "$model", "$year", "$engine", "$fuel")
									.addToSet(new BasicDBObject() {
							/**
							 * 
							 */
							private static final long serialVersionUID = 1L;

							{
							put("gear", "$_id.gear");
							put("wheels_drives", "$wheels_drives");
							}
						}).as("gears"),
									
						Aggregation.group("$make", "$model", "$year", "$engine")
									.addToSet(new BasicDBObject() {
							/**
							 * 
							 */
							private static final long serialVersionUID = 1L;

							{
								put("fuel", "$_id.fuel");
								put("gears", "$gears");
							}
						}).as("fuels"),
									
						Aggregation.group("$make", "$model", "$year")
									.addToSet(new BasicDBObject() {
							/**
							 * 
							 */
							private static final long serialVersionUID = 1L;

							{
								put("engine", "$_id.engine");
								put("fuels", "$fuels");
							}
						}).as("engines"),
						
						Aggregation.group("$make", "$model")
									.addToSet(new BasicDBObject() {
							/**
							 * 
							 */
							private static final long serialVersionUID = 1L;

							{
								put("year", "$_id.year");
								put("engines", "$engines");
							}
						}).as("years"),
						
						Aggregation.group("$make")
									.addToSet(new BasicDBObject() {
							/**
							 * 
							 */
							private static final long serialVersionUID = 1L;

							{
								put("model", "$_id.model");
								put("years", "$years");
							}
						}).as("models"),
						Aggregation.out("filters")
						);
		
		mongoTemplate.aggregate(filtersAggregation, FilterForFrontDto.class);
	}
	
	public Iterable<FilterForFrontDto> getFilters() {
		return mongoTemplate.findAll(FilterForFrontDto.class, "filters");
	}
}
