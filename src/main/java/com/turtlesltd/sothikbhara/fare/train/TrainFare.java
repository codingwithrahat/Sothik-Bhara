package com.turtlesltd.sothikbhara.fare.train;

import com.turtlesltd.sothikbhara.fare.Fare;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainFare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Embedded
    @Valid
    private Fare fare;

    @Enumerated(EnumType.STRING)
    private TrainClassType trainClassType;

    private double normalFare;

    private double studentFare;

}