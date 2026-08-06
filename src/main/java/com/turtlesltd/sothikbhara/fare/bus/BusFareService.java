package com.turtlesltd.sothikbhara.fare.bus;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusFareService {

    private final BusFareRepository busFareRepository;

    private final double per_km_fare = 2.53;
    private final double min_fare = 10;

    public void calAndStore(BusFare busFare){
        double normalFare =  Math.max(per_km_fare * busFare.getFare().getKm(), min_fare);
        double stuFare = Math.max(normalFare / 2, min_fare);

        busFare.setNormalFare(normalFare);
        busFare.setStudentFare(stuFare);

        busFareRepository.save(busFare);
    }

    public List<BusFare> findAll(){
        return busFareRepository.findAll();
    }

    public void deleteById(int id){
        busFareRepository.deleteById(id);
    }


}
