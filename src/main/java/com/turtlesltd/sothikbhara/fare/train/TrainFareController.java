package com.turtlesltd.sothikbhara.fare.train;

import jakarta.servlet.http.HttpSession;
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
    public String showTrainForm(HttpSession session, Model model) {

        Long user_id = (Long) session.getAttribute("logged_user_id");

        if(user_id == null){
            return "redirect:/login";
        }

        model.addAttribute("fare", new TrainFare());
        model.addAttribute("classTypes", TrainClassType.values());
        return "train/calculate";
    }

    @PostMapping("/fare/train")
    public String calculate(@Valid @ModelAttribute("fare") TrainFare trainFare, BindingResult bindingResult, HttpSession session, Model model) {

        Long user_id = (Long) session.getAttribute("logged_user_id");

        if(user_id == null){
            return "redirect:/login";
        }


        log.info("Train fare request received: {}", trainFare);

        if (bindingResult.hasErrors()) {
            model.addAttribute("classTypes", TrainClassType.values());
            return "train/calculate";
        }

        trainFareService.calAndStore(trainFare, user_id);

        log.info("Train fare calculated and saved: {}", trainFare);

        model.addAttribute("fare", trainFare);
        model.addAttribute("classTypes", TrainClassType.values());
        model.addAttribute("calculated", true);

        return "train/calculate";
    }

    @GetMapping("/trainHistory")
    public String history(HttpSession session, Model model) {

        Long user_id = (Long) session.getAttribute("logged_user_id");

        if(user_id == null){
            return "redirect:/login";
        }

        model.addAttribute("history", trainFareService.findAllByUserId(user_id));
        return "train/history";
    }

    @GetMapping("/trainHistory/remove/{id}")
    public String remove(@PathVariable Long id, HttpSession session) {

        Long user_id = (Long) session.getAttribute("logged_user_id");

        if(user_id == null){
            return "redirect:/login";
        }

        trainFareService.deleteByIdAndUser_Id(id, user_id);
        return "redirect:/trainHistory";
    }
}
