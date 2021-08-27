package geo.db;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import geo.db.entity.Coordinates;
import geo.web.dto.CoordinatesDto;

@Service
public class CoordinatesControllerService {

	private final CoordinatesDataService dataService;
	
	@Autowired
	public CoordinatesControllerService(CoordinatesDataService dataService) {
		this.dataService = dataService;
	}
	
	

	public void save(CoordinatesDto dto) {
		Coordinates entity = CoordinatesConverter.convert(dto);
		dataService.save(entity);
	}
	
	
	public List<CoordinatesDto> getAllCoordinates() {
		List<Coordinates> entities = dataService.findAll();
			
		return entities.stream()
						.map(CoordinatesConverter::convert)
						.collect(Collectors.toList());
		
	}
	
}
