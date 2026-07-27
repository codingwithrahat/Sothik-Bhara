package com.turtlesltd.sothikbhara;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FareController {

    private static final double PER_KM_FARE = 2.53;

    private final FareRepository fareRepository;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/bus")
    public String showBusForm(Model model) {
        model.addAttribute("fare", new Fare());
        return "bus";
    }

    @PostMapping("/bus/calculate")
    public String calculate(@Valid @ModelAttribute Fare fare, BindingResult bindingResult, Model model) {
        log.info("Fare request received: {}", fare);

        if (bindingResult.hasErrors()) {
            return "bus";
        }

        double normalFare = fare.getKm() * PER_KM_FARE;
        double studentFare = normalFare / 2;

        if(normalFare < 10){
            normalFare = 10;
        }

        if(studentFare < 10){
            studentFare = 10;
        }

        fare.setNormalFare(normalFare);
        fare.setStudentFare(studentFare);

        fareRepository.save(fare);   // database e save hocche
        log.info("Fare calculated and saved to database: {}", fare);

        model.addAttribute("fare", fare);
        model.addAttribute("calculated", true);

        return "bus";
    }


    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("history", fareRepository.findAll());
        return "history";
    }

    @GetMapping("/history/remove/{id}")
    public String remove(@PathVariable Long id) {
        fareRepository.deleteById(id);
        return "redirect:/history";
    }
}