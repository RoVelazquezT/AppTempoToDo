package com.tempo.TempoToDo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.tempo.TempoToDo.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity //habilita y configura la seguridad web de la app
//autenticacion, autorizacion, personaliza politicas de seguridad, 
//Habilitar protección CSRF y manejo de sesiones 

public class SecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService; 
	public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
		this.customOAuth2UserService= customOAuth2UserService;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests( auth -> auth 
					.requestMatchers("tempoLogin", "/css/**", "/js/**").permitAll()
					.anyRequest().authenticated()
			)
			.oauth2Login( oauth2 -> oauth2 
					.loginPage("/tempoLogin")
					.userInfoEndpoint(userInfo -> userInfo
							.userService(customOAuth2UserService)
							)
					.defaultSuccessUrl("/tempoHome", true)
					.failureUrl("/login?error=true")
			)
			.logout (logout -> logout 
					.logoutUrl("/logout")
					.logoutSuccessUrl("/logout-success")
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.deleteCookies("JSESSIONID")
					.permitAll()
			);
			
		return http.build(); 
	}

	
}
