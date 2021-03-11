package com.bugcatcherweb.controller;

import com.bugcatcherweb.config.auth.PrincipalDetails;
import com.bugcatcherweb.entity.User;
import com.bugcatcherweb.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // 일반적인 로그인을하면 UserDetails 타입으로 Aunthentication 객체안으로 들어가고
    // Oauth 로그인을 하면 OAuth2User 타입으로 Aunthentication 객체안으로 들어감.(구글,페북로그인)

    @GetMapping("/test/login")
    public @ResponseBody String loginTest(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails) { //DI 의존성 주입
        logger.info("-----------------test/login---------------------");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        logger.info("authentication : " + principalDetails.getUser());
        logger.info("userDetails : " + userDetails.getUser());
        return  "세션 정보 확인";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String loginOauthTest(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) { //DI 의존성 주입
        logger.info("-----------------test/login---------------------");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        logger.info("authentication : " + oAuth2User.getAttributes());
        logger.info("oauth) : " + oauth.getAttributes());
        return  "Oauth 세션 정보 확인";
    }

    @GetMapping({"","/"})
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        logger.info("principalDetails : "+ principalDetails.getUser());
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }

    // SecurityConfig 작성후 낚아채기 없어짐.
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/doJoin")
    public String doJoin(User user) {
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        user.setRole("ROLE_USER");
        user.setStatus("Y");
        user.setPassword(encPassword);
        userRepository.save(user);
        logger.info("회원가입 성공!");
        return "redirect:/login";
    }

    @Secured("ROLE_ADMIN") // 한개만 권한 걸 때
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @PreAuthorize("haseRole('RoleMANAGER') or hasRole('ROLE_ADMIN')") // 한개만 권한 걸 때
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터정보";
    }
}
