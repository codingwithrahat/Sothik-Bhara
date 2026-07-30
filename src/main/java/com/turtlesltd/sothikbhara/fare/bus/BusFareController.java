package com.turtlesltd.sothikbhara.fare.bus;

import com.turtlesltd.sothikbhara.fare.Fare;
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
public class BusFareController {

    private final BusFareService busFareService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/bus")
    public String showBusForm(Model model) {
        model.addAttribute("busFare", new BusFare());
        return "bus/calculate";
    }

    @PostMapping("/fare/bus")
    public String calculate(@Valid @ModelAttribute BusFare busFare, BindingResult bindingResult, Model model) {
        log.info("Fare request received: {}", busFare);

        if (bindingResult.hasErrors()) {
            return "bus/calculate";
        }

        busFareService.calAndStore(busFare);

        log.info("Fare calculated and saved to database: {}", busFare);

        model.addAttribute("busFare", busFare);
        model.addAttribute("calculated", true);

        return "bus/calculate";
    }


    @GetMapping("/busHistory")
    public String history(Model model) {
        model.addAttribute("history", busFareService.findAll());
        return "bus/history";
    }

    @GetMapping("/busHistory/remove/{id}")
    public String remove(@PathVariable int id) {
        busFareService.deleteById(id);
        return "redirect:/busHistory";
    }
}