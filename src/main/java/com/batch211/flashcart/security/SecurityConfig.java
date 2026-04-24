package com.batch211.flashcart.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;


@Component
@Configuration
public class SecurityConfig {
	
	@Autowired
	public JwtAuthFilter jwtAuthFilter;
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
		
		httpSecurity
		.csrf(csrf->csrf.disable())
		.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true);
            config.addAllowedOrigin("http://localhost:5173");
            config.addAllowedOrigin("http://127.0.0.1:5173");
            config.addAllowedHeader("*");
            config.addAllowedMethod("*");
            return config;
        }))
		.authorizeHttpRequests(req->req
		.requestMatchers(HttpMethod.POST,"/api/auth/register","/api/auth/login").permitAll()
		.requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
		.requestMatchers(HttpMethod.GET, "/api/brands/**").permitAll()
	    .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
	    .requestMatchers(HttpMethod.POST, "/api/payments/complete").permitAll()
		.anyRequest().authenticated())
		.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
		
	}	
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
	
}
