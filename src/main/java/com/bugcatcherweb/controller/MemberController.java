package com.bugcatcherweb.controller;

import com.bugcatcherweb.entity.Member;
import com.bugcatcherweb.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"","/"})
    public String index() {
        return "index";
    }

    @GetMapping("/member")
    public @ResponseBody String member() {
        return "member";
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
    public String doJoin(Member member) {
        String rawPassword = member.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        member.setRole("ROLE_USER");
        member.setStatus("Y");
        member.setPassword(encPassword);
        memberRepository.save(member);
        logger.info(member());
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
