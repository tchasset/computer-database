package com.excilys.tchasset.config;

import com.excilys.tchasset.log.Logging;
import com.excilys.tchasset.token.JwtAuthenticationEntryPoint;
import com.excilys.tchasset.token.JwtRequestFilter;

import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.excilys.tchasset")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailService;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.cors()
					.and()
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
					.and()
				.addFilterBefore(jwtRequestFilter, (Class<? extends Filter>) UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
					.antMatchers(HttpMethod.GET, "/computers/**").hasAnyRole("USER", "ADMIN")
					.antMatchers(HttpMethod.POST, "/computers/**").hasRole("ADMIN")
					.antMatchers(HttpMethod.PUT, "/computers/**").hasRole("ADMIN")
					.antMatchers(HttpMethod.DELETE, "/computers/**").hasRole("ADMIN")
					.and()
				.sessionManagement() .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		Logging.info(userDetailService.toString(), SecurityConfig.class);
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/*@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD","GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/
}
