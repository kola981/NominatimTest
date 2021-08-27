package geo.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class CoordinatesDto {
	
	@JsonProperty("lat")
	private double latitude;
	@JsonProperty("lon")
	private double longitude;
	
	@JsonProperty("address")
	private AddressDto address;
	
	
	public CoordinatesDto() {}
	
	public AddressDto getAddress() {
		return address;
	}
	
	public void setAddress(AddressDto address) {
		this.address = address;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
}
