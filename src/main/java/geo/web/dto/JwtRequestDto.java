package geo.web.dto;

import java.io.Serializable;

public class JwtRequestDto implements Serializable {

	private static final long serialVersionUID = 949302282954884983L;
	
	private String username;
	private String password;
	
	public JwtRequestDto() { super(); }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
