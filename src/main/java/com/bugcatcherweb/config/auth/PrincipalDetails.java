package com.bugcatcherweb.config.auth;

// 시큐리티가 /doLogin주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어준다.(Security ContextHolder)
// 오브젝트 => Authentication 타입 객체
// Authentication 안에 Member 정보가 있어야 됨.
// Member 오브젝트타입 => UserDetails 타입 객체
// Security Session => Authentication => UserDetails(PrincipalDetails)

import com.bugcatcherweb.entity.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private Member member; // 콤포지션
    public PrincipalDetails(Member member) {
        this.member = member;
    }

    //해당 Member의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                logger.info("로그인 성공.");
                return member.getRole();
            }
        });
        logger.info("로그인 실패.");
        return collection;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override // 계정 만료
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override // 계정 잠금
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        //우리 사이트 1년동안 회원 로그인을 안하면 휴먼계정으로
        //현재시간 - 로그인시간 => 1년을 초과하면 return false
        return true;
    }
}
