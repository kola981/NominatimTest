package geo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.filter.OncePerRequestFilter;

import geo.db.entity.User;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenUtil tokenUtil;
	private final UserDetailsService userDetailsService;
	
	@Autowired
	public JwtAuthenticationFilter(JwtTokenUtil jwtUtil, InMemoryUserDetailsManager userDetailsService){
		this.tokenUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		User user = null;
		
		String requestHeader = request.getHeader("Authorization");
			
		if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
			
			String token = requestHeader.substring(7);
			
			try {
				user = tokenUtil.parseToken(token);
			}
			catch (Exception e) {
				//
			}
			
			if (user != null) {
			
				UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());
				
				if (SecurityContextHolder.getContext().getAuthentication() == null)
				{
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}
		filterChain.doFilter(request, response);		
		
	}

	
}
