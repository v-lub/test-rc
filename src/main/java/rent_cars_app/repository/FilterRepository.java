package rent_cars_app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import rent_cars_app.controller.dto.filter.FilterForFrontDto;

public interface FilterRepository extends MongoRepository<FilterForFrontDto, String> {
	
	/*@Query(value="{'make':?0,"
			+ "'models.model':?1,"
			+ "'models.years.year':?2,"
			+ "'models.years.engines.engine':?3,"
			+ "'models.years.engines.fuels.fuel':?4,"
			+ "'models.years.engines.fuels.gears.gear':?5,"
			+ "'models.years.engines.fuels.gears.wheels_drives.wheels_drive':?6}")
	Optional<FilterForFrontDto> findByAllFilds(String make, String model,
			String year, String engine, String fuel, String gear, String wheelsDrive);
	@Query(value="{'make':?0,"
			+ "'models.model':?1,"
			+ "'models.years.year':?2,"
			+ "'models.years.engines.engine':?3,"
			+ "'models.years.engines.fuels.fuel':?4,"
			+ "'models.years.engines.fuels.gears.gear':?5}")
	Optional<FilterForFrontDto> findByMakeModelYearEngineFuelGear(String make, String model,
			String year, String engine, String fuel, String gear);
	@Query(value="{'make':?0,"
			+ "'models.model':?1,"
			+ "'models.years.year':?2,"
			+ "'models.years.engines.engine':?3,"
			+ "'models.years.engines.fuels.fuel':?4}")
	Optional<FilterForFrontDto> findByMakeModelYearEngineFuel(String make, String model,
			String year, String engine, String fuel);
	@Query(value="{'make':?0,"
			+ "'models.model':?1,"
			+ "'models.years.year':?2,"
			+ "'models.years.engines.engine':?3}")
	Optional<FilterForFrontDto> findByMakeModelYearEngine(String make, String model,
			String year, String engine);
	@Query(value="{'make':?0,"
			+ "'models.model':?1,"
			+ "'models.years.year':?2}")
	Optional<FilterForFrontDto> findByMakeModelYear(String make, String model,
			String year);
	@Query(value="{'make':?0,"
			+ "'models.model':?1}")
	Optional<FilterForFrontDto> findByMakeModel(String make, String model);

	Optional<FilterForFrontDto> findByMake(String make);*/

}
