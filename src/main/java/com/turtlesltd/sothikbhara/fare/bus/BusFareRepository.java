package com.turtlesltd.sothikbhara.fare.bus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface BusFareRepository extends JpaRepository<BusFare, Long> {
    void deleteByIdAndUser_Id(Long id, Long userId);
    List<BusFare> findAllByUserId(Long userId);
}