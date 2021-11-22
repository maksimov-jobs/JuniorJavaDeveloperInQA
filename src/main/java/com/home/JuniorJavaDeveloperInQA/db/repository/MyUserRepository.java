package com.home.JuniorJavaDeveloperInQA.db.repository;

import com.home.JuniorJavaDeveloperInQA.db.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    MyUser findByLogin(String login);
}
