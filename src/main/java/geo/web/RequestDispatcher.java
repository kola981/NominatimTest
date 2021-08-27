package geo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import geo.web.dto.CoordinatesDto;

@Controller
public class RequestDispatcher {
	
	public CoordinatesDto[] searchByAddress(String query) {
		
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(URL.getSearchByAddressURL(query), CoordinatesDto[].class);
	
	}

	
	public CoordinatesDto getAddressByCoordinates(double latitude, double longitude) {
		
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(URL.getAddressByCoordinatesURL(latitude, longitude), CoordinatesDto.class);
		
	}
	
	static class URL {
		private static StringBuilder sb = new StringBuilder();
		
		private URL() {}
		
		private static String getSearchByAddressURL(String query) {
			sb.setLength(0);
			sb.append("https://nominatim.openstreetmap.org/search/");
			sb.append(query);
			sb.append("?format=json&addressdetails=1&polygon_svg=0");
			return sb.toString();
		}
		
		
		private static String getAddressByCoordinatesURL(double latitude, double longitude) {
			sb.setLength(0);
			sb.append("https://nominatim.openstreetmap.org/reverse?lat=");
			sb.append(latitude);
			sb.append("&lon=");
			sb.append(longitude);
			sb.append("&format=json&addressdetails=1");
			return sb.toString();
		}
	} 
}
