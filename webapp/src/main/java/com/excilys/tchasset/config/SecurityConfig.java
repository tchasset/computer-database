package com.excilys.tchasset.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public DataSource dataSource() {
		HikariConfig config = new HikariConfig("/hikari.properties");
		return new HikariDataSource(config);
	}
	
	@Autowired	
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.jdbcAuthentication()
			.dataSource(dataSource())
			.usersByUsernameQuery("select username,password,enabled "
			        + "from users "
			        + "where username = ?")
			.authoritiesByUsernameQuery("select username,authority "
			        + "from authorities "
			        + "where username = ?");
	}

	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/addComputer","/editComputer").hasAuthority("ADMIN")
				.antMatchers("/dashboard").hasAnyAuthority("USER", "ADMIN")
				.anyRequest().authenticated()
				.and()
			.formLogin()
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
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
//	@Override
//    protected void configure(HttpSecurity http) throws Exception {
//            http.addFilter(digestAuthenticationFilter())              // register digest entry point
//            .exceptionHandling().authenticationEntryPoint(digestEntryPoint())       // on exception ask for digest authentication
//            .and()
//            .httpBasic()                      // it indicate basic authentication is requires
//            .and()
//            .authorizeRequests()
//            .antMatchers( "/dashboar").permitAll() // /home will be accessible directly, no need of any authentication
//            .anyRequest().authenticated();
//    }
	
//    DigestAuthenticationFilter digestAuthenticationFilter() throws Exception {
//        DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
//        digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());
//        digestAuthenticationFilter.setAuthenticationEntryPoint(digestEntryPoint());
//        return digestAuthenticationFilter;
//    }
//    

    
    
//    
//    @Bean
//    DigestAuthenticationEntryPoint digestEntryPoint() {
//        DigestAuthenticationEntryPoint bauth = new DigestAuthenticationEntryPoint();
//        bauth.setRealmName("Digest WF Realm");
//        bauth.setKey("MySecureKey");
//        return bauth;
//    }
//    
//    @Bean
//    public AuthenticationManager customAuthenticationManager() throws Exception {
//        return authenticationManager();
//    }
}