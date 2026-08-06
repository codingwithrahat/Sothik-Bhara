package com.turtlesltd.sothikbhara.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String showReg(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String resultReg(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
        log.info("Register request receive: {}", user);

        if (bindingResult.hasErrors()) {
            return "register";
        }

        boolean flag = userService.register(user);

        if (!flag) {
            model.addAttribute("error", "email already exists");
            return "register";
        }

        log.info("user registered {}", user.getEmail());

        return "redirect:/";

    }

    @GetMapping("/")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/")
    public String login(@RequestParam String email, @RequestParam String pass, Model model) {

        log.info("Login attempt for: {}", email);

        User user = userService.authenticate(email, pass);

        if (user == null) {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }

        return "redirect:/dashboard";
    }

}
