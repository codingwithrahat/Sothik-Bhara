package com.turtlesltd.sothikbhara.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ProfileController {

    private final UserService userService;

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Long user_id = (Long) session.getAttribute("logged_user_id");

        if (user_id == null) {
            return "redirect:/login";
        }

        User user = userService.getUser(user_id);

        model.addAttribute("user", user);
        return "profile";
    }
}
