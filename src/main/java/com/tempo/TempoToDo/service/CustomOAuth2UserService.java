package com.tempo.TempoToDo.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.tempo.TempoToDo.model.User;

import jakarta.transaction.Transactional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
  private final UserService userService;

  public CustomOAuth2UserService(UserService userService) {
    this.userService = userService;
  }

  @Transactional
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oauth2User = super.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    if (!"google".equalsIgnoreCase(registrationId)) {
      throw new OAuth2AuthenticationException("Solo se permite autenticación con Google");
    }

    Map<String, Object> attrs = oauth2User.getAttributes();
    String email = (String) attrs.get("email");
    String providerId = (String) attrs.get("sub");
    String name = (String) attrs.get("name");

    if (email == null || providerId == null) {
      throw new OAuth2AuthenticationException("Datos de Google incompletos");
    }

    // crea/actualiza
    User u = userService.findOrCreateByGoogle(email, providerId, name);
    u.setUsername(name);
    userService.save(u);

    // principalKey = "sub" en Google
    return new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
        attrs,
        "sub"
    );
  }
}















