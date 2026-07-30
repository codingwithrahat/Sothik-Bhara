package com.turtlesltd.sothikbhara.fare.cng;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CngFareService {

    private final CngFareRepository cngFareRepository;

    private final double per_km_fare_shared = 20;
    private final double per_km_fare_reserved = 60;
    private final double min_fare = 30;

    public void calAndStore(CngFare cngFare) {

        double fare;

        if(cngFare.getRideType() == CngRideType.SHARED){
            fare  = per_km_fare_shared * cngFare.getFare().getKm();
        }else{
            fare = per_km_fare_reserved * cngFare.getFare().getKm();
        }

        fare = Math.max(min_fare, fare);

        cngFare.setNormalfare(fare);

        cngFareRepository.save(cngFare);
    }

    public List<CngFare> findAll() {
        return cngFareRepository.findAll();
    }

    public void deleteById(int id) {
        cngFareRepository.deleteById(id);
    }

}
