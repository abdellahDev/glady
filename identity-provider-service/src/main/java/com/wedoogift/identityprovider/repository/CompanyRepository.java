package com.wedoogift.identityprovider.repository;

import com.wedoogift.identityprovider.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyEntity,Integer>, PagingAndSortingRepository<CompanyEntity,Integer> {

    public Optional<CompanyEntity> findByCompanyName(String name);
}
