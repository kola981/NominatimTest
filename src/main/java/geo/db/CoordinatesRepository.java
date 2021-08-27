package geo.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import geo.db.entity.Coordinates;

public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
	
	@Query(value = "select c from Coordinates c where c.latitude=:latitude and c.longitude=:longitude")
	Optional<Coordinates> findByLatitudeAndLongitude(double latitude, double longitude);

}
