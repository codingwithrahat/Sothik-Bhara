package com.turtlesltd.sothikbhara;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
    private final UserRepository userRepository;

    @GetMapping("/register")
    public String showReg(Model model){
        model.addAttribute("user", new User());
        return "Register";
    }

    @PostMapping("/register")
    public String resultReg(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model){
        log.info("Register request receive: {}", user);

        if(bindingResult.hasErrors()){
            return "Register";
        }

        if(userRepository.existsByEmail(user.getEmail())){
            model.addAttribute("error", "email already exists");
            return "register";
        }

        userRepository.save(user);

        log.info("user registered {}", user.getEmail());

        return "redirect:/";

    }

    @GetMapping("/")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/")
    public String login(@RequestParam String email, @RequestParam String pass, Model model){

        log.info("Login attempt for: {}", email);

        User user = userRepository.findByEmail(email);

        if(user == null){
            model.addAttribute("error", "User not exists");
            return "login";
        }

        if(!user.getPassword().equals(pass)){
            model.addAttribute("error", "Invalid Password");
            return "login";
        }

        return "redirect:/dashboard";
    }





}
