package com.turtlesltd.sothikbhara.fare.train;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainFareRepository extends JpaRepository<TrainFare, Integer> {
    List<TrainFare> findAllByUserId(Long user_id);
    void deleteByIdAndUser_Id(Long id, Long user_id);
}
