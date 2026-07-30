package com.turtlesltd.sothikbhara.fare;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Fare {

    @NotBlank(message = "Starting address is required")
    private String startAddress;

    @NotBlank(message = "Drop address is required")
    private String dropAddress;

    @Positive(message = "KM must be greater than 0")
    private double km;
}