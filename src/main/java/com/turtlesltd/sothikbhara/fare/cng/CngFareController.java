package com.turtlesltd.sothikbhara.fare.cng;

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
public class CngFareController {

    private final CngFareService cngFareService;

    @GetMapping("/cng")
    public String showCngForm(Model model) {
        model.addAttribute("fare", new CngFare());
        model.addAttribute("rideTypes", CngRideType.values());
        return "cng/calculate";
    }

    @PostMapping("/fare/cng")
    public String calculate(@Valid @ModelAttribute("fare") CngFare cngFare, BindingResult bindingResult, Model model) {
        log.info("CNG fare request received: {}", cngFare);

        if (bindingResult.hasErrors()) {
            model.addAttribute("rideTypes", CngRideType.values());
            return "cng/calculate";
        }

        cngFareService.calAndStore(cngFare);

        log.info("CNG fare calculated and saved: {}", cngFare);

        model.addAttribute("fare", cngFare);
        model.addAttribute("rideTypes", CngRideType.values());
        model.addAttribute("calculated", true);

        return "cng/calculate";
    }

    @GetMapping("/cngHistory")
    public String history(Model model) {
        model.addAttribute("history", cngFareService.findAll());
        return "cng/history";
    }

    @GetMapping("/cngHistory/remove/{id}")
    public String remove(@PathVariable int id) {
        cngFareService.deleteById(id);
        return "redirect:/cngHistory";
    }
}
