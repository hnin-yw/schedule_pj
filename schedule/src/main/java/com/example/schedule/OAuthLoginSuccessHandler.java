package com.example.schedule;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.schedule.business.UserBusiness;
import com.example.schedule.entity.User;
import com.example.schedule.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserBusiness userBusiness;

	@Autowired
	private UserService userService;

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
		OAuth2AuthorizedClient client = authorizedClientService
				.loadAuthorizedClient(oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
		String clientName = client.getClientRegistration().getClientName();
		OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

		String userName = null;
		String userFirstName = null;
		String userLastName = null;
		if ("GOOGLE".equals(clientName)) {
			userName = oauth2User.getAttribute("email");
			userFirstName = oauth2User.getAttribute("given_name");
			userLastName = oauth2User.getAttribute("family_name");
		} else if ("LINE".equals(clientName)) {
			userName = oauth2User.getAttribute("userId");
			userFirstName = oauth2User.getAttribute("displayName");
		}

		User user = userService.findUserByUserName(userName);
		if (user == null) {
			user = new User();
			String userCode = userBusiness.getUserCode();
			user.setUserCode(userCode);
			user.setUserName(userName);
			user.setUserFirstName(userFirstName);
			user.setUserLastName(userLastName);
			user.setEmail(userName);
			user.setProvider(clientName);
			user.setDelFlg(false);
			user.setCreatedBy(userCode);
			user.setCreatedAt(LocalDateTime.now());
			user.setUpdatedBy(userCode);
			user.setUpdatedAt(LocalDateTime.now());
			userService.save(user);
		} else {
			user.setProvider(clientName); // Update provider using clientName
			user.setUpdatedAt(LocalDateTime.now());
			userService.updateProvider(userName, clientName);
		}

		User dbUser = userService.findUserByUserName(userName);
		Map<String, Object> attributes = new HashMap<>(oauth2User.getAttributes());
		attributes.put("userCode", dbUser.getUserCode());
		if (userLastName != null) {
			attributes.put("userName", userFirstName + " " + userLastName);
		} else {
			attributes.put("userName", userFirstName);
		}

		DefaultOAuth2User enhancedOAuth2User = new DefaultOAuth2User(oauth2User.getAuthorities(), attributes,
				"userCode");

		OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
		SecurityContextHolder.getContext().setAuthentication(new OAuth2AuthenticationToken(enhancedOAuth2User,
				authenticationToken.getAuthorities(), authenticationToken.getAuthorizedClientRegistrationId()));

		response.sendRedirect("/schedule/schedules");
	}
}
