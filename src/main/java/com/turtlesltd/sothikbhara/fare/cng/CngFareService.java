package com.turtlesltd.sothikbhara.fare.cng;

import com.turtlesltd.sothikbhara.user.User;
import com.turtlesltd.sothikbhara.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CngFareService {

    private final CngFareRepository cngFareRepository;
    private final UserRepository userRepository;

    private final double per_km_fare_shared = 20;
    private final double per_km_fare_reserved = 60;
    private final double min_fare = 30;

    public void calAndStore(CngFare cngFare, Long user_id) {

        double fare;

        if(cngFare.getRideType() == CngRideType.SHARED){
            fare  = per_km_fare_shared * cngFare.getFare().getKm();
        }else{
            fare = per_km_fare_reserved * cngFare.getFare().getKm();
        }

        fare = Math.max(min_fare, fare);

        User user = userRepository.findById(user_id).orElse(null);

        cngFare.setUser(user);

        cngFare.setNormalfare(fare);

        cngFareRepository.save(cngFare);
    }

    public List<CngFare> findAllByUserId(Long user_id) {
        return cngFareRepository.findAllByUserId(user_id);
    }

    public void deleteByIdAndUser_Id(Long id, Long user_id) {
        cngFareRepository.deleteByIdAndUser_Id(id, user_id);
    }

}
