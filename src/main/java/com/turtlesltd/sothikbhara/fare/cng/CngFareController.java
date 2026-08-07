package com.turtlesltd.sothikbhara.fare.cng;

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
public class CngFareController {

    private final CngFareService cngFareService;

    @GetMapping("/cng")
    public String showCngForm(HttpSession session, Model model) {

        Long user_id = (Long) session.getAttribute("logged_user_id");

        if(user_id == null){
            return "redirect:/login";
        }

        model.addAttribute("fare", new CngFare());
        model.addAttribute("rideTypes", CngRideType.values());
        return "cng/calculate";
    }

    @PostMapping("/fare/cng")
    public String calculate(@Valid @ModelAttribute("fare") CngFare cngFare, BindingResult bindingResult, HttpSession session, Model model) {

        Long user_id = (Long) session.getAttribute("logged_user_id");

        if(user_id == null){
            return "redirect:/login";
        }

        log.info("CNG fare request received: {}", cngFare);

        if (bindingResult.hasErrors()) {
            model.addAttribute("rideTypes", CngRideType.values());
            return "cng/calculate";
        }

        cngFareService.calAndStore(cngFare, user_id);

        log.info("CNG fare calculated and saved: {}", cngFare);

        model.addAttribute("fare", cngFare);
        model.addAttribute("rideTypes", CngRideType.values());
        model.addAttribute("calculated", true);

        return "cng/calculate";
    }

    @GetMapping("/cngHistory")
    public String history(HttpSession session, Model model) {

        Long user_id = (Long) session.getAttribute("logged_user_id");

        if(user_id == null){
            return "redirect:/login";
        }

        model.addAttribute("history", cngFareService.findAllByUserId(user_id));
        return "cng/history";
    }

    @GetMapping("/cngHistory/remove/{id}")
    public String remove(@PathVariable Long id, HttpSession session) {

        Long user_id = (Long) session.getAttribute("logged_user_id");

        if(user_id == null){
            return "redirect:/login";
        }

        cngFareService.deleteByIdAndUser_Id(id, user_id);
        return "redirect:/cngHistory";
    }
}
