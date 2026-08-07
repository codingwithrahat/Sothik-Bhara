package com.turtlesltd.sothikbhara.fare.bus;

import com.turtlesltd.sothikbhara.user.User;
import com.turtlesltd.sothikbhara.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusFareService {

    private final BusFareRepository busFareRepository;
    private final UserRepository userRepository;

    private final double per_km_fare = 2.53;
    private final double min_fare = 10;

    public void calAndStore(BusFare busFare, Long userId){
        double normalFare =  Math.max(per_km_fare * busFare.getFare().getKm(), min_fare);
        double stuFare = Math.max(normalFare / 2, min_fare);

        busFare.setNormalFare(normalFare);
        busFare.setStudentFare(stuFare);

        User user = userRepository.findById(userId).orElse(null);

        busFare.setUser(user);

        busFareRepository.save(busFare);
    }

    public List<BusFare> findByUserId(Long userId){
        return busFareRepository.findAllByUserId(userId);
    }
    
    public void deleteByIdAndUser_Id(Long id, Long userId){
        busFareRepository.deleteByIdAndUser_Id(id, userId);
    }

}
