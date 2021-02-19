package com.bugcatcherweb.config.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipleOauth2UserService extends DefaultOAuth2UserService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    // 구글한테 받은 userRequest 데이터에 대한 후처리 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        logger.info("getClientRegistration : " + userRequest.getClientRegistration());
        logger.info("getTokenValue : " + userRequest.getAccessToken().getTokenValue());
        logger.info("getAttributes : " + super.loadUser(userRequest).getAttributes());
        return super.loadUser(userRequest);
    }
}
