package geo.security;

import java.security.Key;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import geo.db.entity.Role;
import geo.db.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {
	private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public String generateToken(UserDetails user) {
		Claims claims = Jwts.claims().setSubject(user.getUsername());
		claims.put("r", user.getAuthorities().iterator().next());
		
		return Jwts.builder()
				.setClaims(claims)
				.signWith(key)
				.compact();
		
	}
	
	
	public User parseToken(String token) {
		Claims claims = Jwts.parserBuilder()
				                 .setSigningKey(key)
				                 .build()
				                 .parseClaimsJws(token).getBody();
		
		User user =  new User();
		user.setName(claims.getSubject());
		user.setRole(parseRole(claims.get("r")));
		return user;
	}
	
	private Role parseRole(Object obj) {
		String role;
		Map<String, String> map = (Map<String, String>) obj;
		if (map.containsKey("authority"))
			role = map.get("authority");
		else 
			throw new RuntimeException("Incorrect roles");
		
		return role.equals("API") ? Role.API : null;
	}
	

}
