package com.turtlesltd.sothikbhara.fare.bus;

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
public class BusFare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Embedded
    @Valid
    private Fare fare;

    private double normalFare;

    private double studentFare;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}