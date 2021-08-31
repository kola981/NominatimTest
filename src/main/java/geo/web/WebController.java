package geo.web;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import geo.db.CoordinatesControllerService;
import geo.security.JwtAuthenticationController;
import geo.web.dto.CoordinatesDto;
import geo.web.dto.JwtRequestDto;
import geo.web.dto.UserDto;

@RestController
public class WebController {

	private final RequestDispatcher requestDispatcher;
	private final CoordinatesControllerService coordinatesController;
	private final JwtAuthenticationController authenticationController;
	
	@Autowired
	public WebController(RequestDispatcher requestDispatcher, CoordinatesControllerService coordinatesController, JwtAuthenticationController authenticationController) {
		this.requestDispatcher = requestDispatcher;
		this.coordinatesController = coordinatesController;
		this.authenticationController = authenticationController;
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
	
	
	@PostMapping(value="/auth/register", consumes= MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> register(@RequestBody UserDto user) {
		authenticationController.registerUser(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@PostMapping(value="/auth/token", consumes= MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> getToken(@RequestBody JwtRequestDto user) {
		String token = authenticationController.getToken(user.getUsername(), user.getPassword());
		return new ResponseEntity<>(token, HttpStatus.OK);
	}
	
}
