package com.turtlesltd.sothikbhara.fare.bus;

import com.turtlesltd.sothikbhara.user.User;
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
public class BusFareController {

    private final BusFareService busFareService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {

        Long user_id = (Long) session.getAttribute("logged_user_id");

        if(user_id == null){
            return "redirect:/login";
        }

        return "dashboard";
    }

    @GetMapping("/bus")
    public String showBusForm(HttpSession session, Model model) {
        model.addAttribute("busFare", new BusFare());
        Long user_id = (Long) session.getAttribute("logged_user_id");

        if(user_id == null){
            return "redirect:/";
        }

        return "bus/calculate";
    }

    @PostMapping("/fare/bus")
    public String calculate(@Valid @ModelAttribute BusFare busFare, BindingResult bindingResult, HttpSession session, Model model) {

        Long user_id = (Long) session.getAttribute("logged_user_id");

        if(user_id == null){
            return "redirect:/login";
        }

        log.info("Fare request received: {}", busFare);

        if (bindingResult.hasErrors()) {
            return "bus/calculate";
        }

        busFareService.calAndStore(busFare, user_id);

        log.info("Fare calculated and saved to database: {}", busFare);

        model.addAttribute("busFare", busFare);
        model.addAttribute("calculated", true);

        return "bus/calculate";
    }

    @GetMapping("/busHistory")
    public String history(HttpSession session, Model model) {

        Long user_id = (Long) session.getAttribute("logged_user_id");

        if (user_id == null) {
            return "redirect:/login";
        }

        model.addAttribute("history", busFareService.findByUserId(user_id));
        return "bus/history";
    }

    @GetMapping("/busHistory/remove/{id}")
    public String remove(@PathVariable Long id, HttpSession session) {

        Long user_id = (Long) session.getAttribute("logged_user_id");

        if (user_id == null) {
            return "redirect:/login";
        }

        busFareService.deleteByIdAndUser_Id(id, user_id);
        return "redirect:/busHistory";
    }
}