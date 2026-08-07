package com.turtlesltd.sothikbhara.user;


import com.turtlesltd.sothikbhara.fare.bus.BusFare;
import com.turtlesltd.sothikbhara.fare.cng.CngFare;
import com.turtlesltd.sothikbhara.fare.train.TrainFare;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "First name is required")
    String firstName;

    @NotBlank(message = "Last name is required")
    String lastName;

    @NotBlank(message = "Phone number is required")
    String phnNumber;

    @NotBlank(message = "Email name is required")
    String email;

    @NotBlank(message = "Password is required")
    String password;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BusFare> busFares;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CngFare> cngFares;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TrainFare> trainFares;


}
