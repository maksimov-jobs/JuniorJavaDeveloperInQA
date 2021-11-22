package com.home.JuniorJavaDeveloperInQA.db.repository;

import com.home.JuniorJavaDeveloperInQA.db.entity.UserMessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserMessageRepository extends JpaRepository<UserMessageEntity, Long> {
    List<UserMessageEntity> findAllByUserId(long userId, Pageable pageable);
}
