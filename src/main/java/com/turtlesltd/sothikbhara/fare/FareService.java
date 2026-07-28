package com.turtlesltd.sothikbhara.fare;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FareService {

    private final double per_km_fare = 2.53;
    private final double min_fare = 10;

    private final FareRepository fareRepository;

    public void calAndStore(Fare fare){

        double normalFare =  Math.max(per_km_fare * fare.getKm(), min_fare);
        double stuFare = Math.max(normalFare / 2, min_fare);

        fare.setNormalFare(normalFare);
        fare.setStudentFare(stuFare);

        fareRepository.save(fare);
    }

    public List<Fare> findAll(){
        return fareRepository.findAll();
    }

    public void deleteById(int id){
        fareRepository.deleteById(id);
    }


}
