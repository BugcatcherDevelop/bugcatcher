package com.bugcatcherweb.repository;

import com.bugcatcherweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository가 없어도 IoC가능 JpaRepository를 상속했기 때문에 자동 빈 등록
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
