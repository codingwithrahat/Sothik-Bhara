package com.turtlesltd.sothikbhara.fare.cng;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CngFareRepository extends JpaRepository<CngFare, Integer> {
    List<CngFare> findAllByUserId(Long user_id);
    void deleteByIdAndUser_Id(Long id, Long user_id);
}
