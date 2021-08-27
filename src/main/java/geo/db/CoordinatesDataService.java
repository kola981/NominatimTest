package geo.db;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import geo.db.entity.Coordinates;


public class CoordinatesDataService {

	@Autowired
	private final CoordinatesRepository repository;
	
	public CoordinatesDataService(CoordinatesRepository repository) {
		this.repository = repository;
	}
	
	
	@Cacheable(cacheNames="data", key="#entity.toString()")
	@Transactional
	public void save(Coordinates entity) {
		Optional<Coordinates> coordinatesData = repository.findByLatitudeAndLongitude(entity.getLatitude(), entity.getLongitude());
		
		if (!coordinatesData.isPresent())
			repository.save(entity);
	}
	
	
	public List<Coordinates> findAll() {
		return repository.findAll();
	}
}
