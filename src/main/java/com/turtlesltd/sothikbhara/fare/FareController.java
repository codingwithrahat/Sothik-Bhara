package com.turtlesltd.sothikbhara.fare;

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

    private final FareService fareService;

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

        fareService.calAndStore(fare);

        log.info("Fare calculated and saved to database: {}", fare);

        model.addAttribute("fare", fare);
        model.addAttribute("calculated", true);

        return "bus";
    }


    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("history", fareService.findAll());
        return "history";
    }

    @GetMapping("/history/remove/{id}")
    public String remove(@PathVariable int id) {
        fareService.deleteById(id);
        return "redirect:/history";
    }
}