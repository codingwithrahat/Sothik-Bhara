package com.turtlesltd.sothikbhara.fare.train;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainFareService {

    private final TrainFareRepository trainFareRepository;

    private final double per_km_fare_shovon = 1.20;
    private final double per_km_fare_shovon_chair = 1.50;
    private final double per_km_fare_snigdha = 2.20;
    private final double per_km_fare_ac_seat = 2.80;
    private final double per_km_fare_ac_cabin = 3.50;
    private final double min_fare = 15;

    public void calAndStore(TrainFare trainFare) {

        double fare;

        if(trainFare.getTrainClassType() == TrainClassType.SHOVON){
            fare = per_km_fare_shovon * trainFare.getFare().getKm();
        }else if(trainFare.getTrainClassType() == TrainClassType.SHOVON_CHAIR){
            fare = per_km_fare_shovon_chair * trainFare.getFare().getKm();
        }else if(trainFare.getTrainClassType() == TrainClassType.SNIGDHA){
            fare = per_km_fare_snigdha * trainFare.getFare().getKm();
        }else if(trainFare.getTrainClassType() == TrainClassType.AC_SEAT){
            fare = per_km_fare_ac_seat * trainFare.getFare().getKm();
        }else{
            fare = per_km_fare_ac_cabin * trainFare.getFare().getKm();
        }

        double normalFare = Math.max(fare, min_fare);
        double stuFare = Math.max(normalFare / 2, min_fare);

        trainFare.setNormalFare(normalFare);
        trainFare.setStudentFare(stuFare);

        trainFareRepository.save(trainFare);
    }

    public List<TrainFare> findAll() {
        return trainFareRepository.findAll();
    }

    public void deleteById(int id) {
        trainFareRepository.deleteById(id);
    }

}
