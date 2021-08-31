package geo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import geo.BeanRegistry;


@EnableWebSecurity
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter  {
	
	private final BeanRegistry register;
	
	@Autowired
	public HttpSecurityConfig(BeanRegistry register) {
		this.register = register;
	}
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoderBean() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public InMemoryUserDetailsManager getInMemoryUserDetailsManager() {
		return new InMemoryUserDetailsManager();
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers(HttpMethod.POST, "/auth/**");
	}
	
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.cors().disable()
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/**").hasRole("API")
				.and()
				.exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(
												register.getBean(JwtTokenUtil.class),
												register.getBean(InMemoryUserDetailsManager.class));
		
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	}
}
