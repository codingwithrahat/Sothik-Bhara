package com.turtlesltd.sothikbhara.fare.train;

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
public class TrainFareController {

    private final TrainFareService trainFareService;

    @GetMapping("/train")
    public String showTrainForm(Model model) {
        model.addAttribute("fare", new TrainFare());
        model.addAttribute("classTypes", TrainClassType.values());
        return "train/calculate";
    }

    @PostMapping("/fare/train")
    public String calculate(@Valid @ModelAttribute("fare") TrainFare trainFare, BindingResult bindingResult, Model model) {
        log.info("Train fare request received: {}", trainFare);

        if (bindingResult.hasErrors()) {
            model.addAttribute("classTypes", TrainClassType.values());
            return "train/calculate";
        }

        trainFareService.calAndStore(trainFare);

        log.info("Train fare calculated and saved: {}", trainFare);

        model.addAttribute("fare", trainFare);
        model.addAttribute("classTypes", TrainClassType.values());
        model.addAttribute("calculated", true);

        return "train/calculate";
    }

    @GetMapping("/trainHistory")
    public String history(Model model) {
        model.addAttribute("history", trainFareService.findAll());
        return "train/history";
    }

    @GetMapping("/trainHistory/remove/{id}")
    public String remove(@PathVariable int id) {
        trainFareService.deleteById(id);
        return "redirect:/trainHistory";
    }
}
