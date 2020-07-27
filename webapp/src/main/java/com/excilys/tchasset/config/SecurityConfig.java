package com.excilys.tchasset.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.excilys.tchasset.log.Logging;
import com.google.common.collect.ImmutableList;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.cors().and()
		/*.exceptionHandling()
	        .authenticationEntryPoint(digestEntryPoint())
	        .and()
		.addFilter(digestAuthenticationFilter())*/
		.authorizeRequests()
			.antMatchers("/addComputer","/editComputer").hasAuthority("ADMIN")
			.antMatchers("/dashboard").hasAnyAuthority("USER", "ADMIN")
			.anyRequest().permitAll()//.authenticated()
			.and()
		.formLogin()
			.loginPage("/login")
		//disconnect user after 60 seconds if inactive
			.successHandler(new AuthenticationSuccessHandler() {
				@Override
				public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
						Authentication authentication) throws IOException, ServletException {
					response.sendRedirect("/computer-database/dashboard");
					request.getSession().setMaxInactiveInterval(60);
				}
			})
			.permitAll()
			.and()
		.logout().logoutUrl("/logout").logoutSuccessUrl("/login")
			.and()
		.sessionManagement().maximumSessions(1).expiredUrl("/login");
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
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD","GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
	/*
	 * Uncomment for digest config (but don't do it)
	 */
	/*@Bean
	public DigestAuthenticationEntryPoint digestEntryPoint () {
		DigestAuthenticationEntryPoint digestAuthenticationEntryPoint = new DigestAuthenticationEntryPoint();
		digestAuthenticationEntryPoint.setRealmName("Digest WF Realm");
		digestAuthenticationEntryPoint.setKey("keh");
		digestAuthenticationEntryPoint.setNonceValiditySeconds(300);
		return digestAuthenticationEntryPoint;
	}

	@Bean
	public DigestAuthenticationFilter digestAuthenticationFilter () {
		DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
		digestAuthenticationFilter.setAuthenticationEntryPoint(digestEntryPoint());
		digestAuthenticationFilter.setUserDetailsService(userDetailsService());
		return digestAuthenticationFilter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new PasswordEncoder() {
	        @Override
	        public String encode(CharSequence rawPassword) {
	            return rawPassword.toString();
	        }
	        @Override
	        public boolean matches(CharSequence rawPassword, String encodedPassword) {
	            return rawPassword.toString().equals(encodedPassword);
	        }
	    };
	}*/
}