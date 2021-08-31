package geo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;

import geo.web.dto.UserDto;

@Controller
public class JwtAuthenticationController {

	private final AuthenticationManager authManager;
	private final JwtTokenUtil tokenUtil;
	private final InMemoryUserDetailsManager userDetailsManager;
	private final BCryptPasswordEncoder passEncoder;
	
	@Autowired
	public JwtAuthenticationController(AuthenticationManager manager, JwtTokenUtil tokenUtil,  InMemoryUserDetailsManager userDetailsManager,
			BCryptPasswordEncoder passEncoder) {
		this.authManager = manager;
		this.tokenUtil = tokenUtil;
		this.userDetailsManager = userDetailsManager;
		this.passEncoder = passEncoder;
	}

	
	public void registerUser(UserDto user) {
		String encodedPass = passEncoder.encode(user.getPassword());
		UserBuilder userBuilder = User.builder();
		userBuilder.username(user.getUsername());
		userBuilder.password(encodedPass);
		userBuilder.authorities("ROLE_API");
		userDetailsManager.createUser(userBuilder.build());
	}


	public String getToken(String username, String password) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(username, password); 
		authManager.authenticate(authentication);
		UserDetails userDetails = userDetailsManager.loadUserByUsername(username);		
		return tokenUtil.generateToken(userDetails);
	}
}
