package com.wedoogift.identityprovider.repository;

import com.wedoogift.identityprovider.entity.CompanyEntity;
import com.wedoogift.identityprovider.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer>, PagingAndSortingRepository<UserEntity,Integer> {
    Optional<UserEntity> findByEmailAndPassword(String email, String password);

    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
