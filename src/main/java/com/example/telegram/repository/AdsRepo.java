package com.example.telegram.repository;

import com.example.telegram.entity.Ads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdsRepo extends JpaRepository<Ads, Long> {
}
