package com.bugcatcherweb.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // 고유값
    @Column
    private String id; // 아이디
    @Column
    private String password; // 비밀번호
    @Column
    private String name; //이름
    @Column
    private String gender; // 성별 Male:남자, Female:여자
    @Column
    private String phone; // 전화번호
    @Column
    private String email; // 이메일
    @Column
    private String role; // 권한 레벨 관리자  Master - 1:관리자  Member - 2:사용자
    @Column
    private String status; // 사용자 상태 Y:활성, N:탈퇴
    @Column
    private String login_ip; //로그인 ip
    @Column
    private String provider;
    @Column
    private String providerId;
    @Column
    @CreationTimestamp
    private Timestamp signup_date; // 회원가입 날짜
    @Column
    @CreationTimestamp
    private Timestamp login_date; // 로그인 날짜
    @Column
    @CreationTimestamp
    private Timestamp password_change_date; // 패스워드 변경 날짜
}
