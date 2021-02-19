package com.bugcatcherweb.config;

import com.bugcatcherweb.config.oauth.PrincipleOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 시큐리티 필터가 필터체인에 등록이 되어서 활성화
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipleOauth2UserService principleOauth2UserService;

    //두번째 작성
    //해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //처음 작성
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/member/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("id")
                .loginProcessingUrl("/doLogin") // login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인 진행. => Controller의 doLogin메서드 안만들어도됨.
                .defaultSuccessUrl("/")
        .and()
                .oauth2Login()
                .loginPage("/login") // 구글 로그인 완료후 후처리 필요. 1.코드받기(인증), 2.액세스토큰(권한), 3.사용자 프로필정보 가져오기 4. 정보로 회원가입 자동 진행
                .userInfoEndpoint()
                .userService(principleOauth2UserService);// 구글 로그인은 (액세스토큰 + 사용자프로필정보를 받음)
    }
}
