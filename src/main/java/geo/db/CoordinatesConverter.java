package geo.db;

import geo.db.entity.Coordinates;
import geo.web.dto.CoordinatesDto;

public class CoordinatesConverter {

	private CoordinatesConverter() {}
	
	
	
	public static Coordinates convert(CoordinatesDto dto) {
		Coordinates entity = new Coordinates();
		
		entity.setLatitude(dto.getLatitude());
		entity.setLongitude(dto.getLongitude());
		
		return entity;
	}
	
	public static CoordinatesDto convert(Coordinates entity) {
		CoordinatesDto dto = new CoordinatesDto();
		
		dto.setLatitude(entity.getLatitude());
		dto.setLongitude(entity.getLongitude());
		
		return dto;
	}
	
}
