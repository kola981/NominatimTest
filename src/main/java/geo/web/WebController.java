package geo.web;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import geo.db.CoordinatesControllerService;
import geo.web.dto.CoordinatesDto;

@RestController
public class WebController {

	private final RequestDispatcher requestDispatcher;
	private final CoordinatesControllerService coordinatesController;
	
	@Autowired
	public WebController(RequestDispatcher requestDispatcher, CoordinatesControllerService coordinatesController) {
		this.requestDispatcher = requestDispatcher;
		this.coordinatesController = coordinatesController;
	}
	
	@GetMapping(value="/api/search/{query}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody CoordinatesDto[] searchByAddress(@PathVariable String query) {
				
		CoordinatesDto[] dto = requestDispatcher.searchByAddress(query);
		if (dto.length > 0)
			for (CoordinatesDto item : dto)
				coordinatesController.save(item);
		
		return dto;
	}
	
	
	@GetMapping(value="/api/all", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody CoordinatesDto[] getAllAddressesByCoordinates() {
		
		List<CoordinatesDto> coordinates =  coordinatesController.getAllCoordinates();
		
		return coordinates.stream()
				   .map(item->requestDispatcher.getAddressByCoordinates(
						   													item.getLatitude(),
						   													item.getLongitude()))
				   .flatMap(Stream::of)
				   .toArray(CoordinatesDto[]::new);
				   	
	}
	
	
}
