package com.turtlesltd.sothikbhara.fare.cng;

import com.turtlesltd.sothikbhara.fare.Fare;
import com.turtlesltd.sothikbhara.user.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CngFare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Embedded
    @Valid
    private Fare fare;

    @Enumerated(EnumType.STRING)
    private CngRideType rideType;

    private double Normalfare;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}