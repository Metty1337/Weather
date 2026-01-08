package metty1337.controller;

import jakarta.validation.Valid;
import metty1337.dto.SignInFormDto;
import metty1337.dto.SignUpFormDto;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/signup")
    public String signUpPage(SignUpFormDto signUpFormDto) {
        return "sign-up";
    }

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute("signUpFormDto") SignUpFormDto signUpFormDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sign-up-with-errors";
        }
        return "redirect:/auth/signin";
    }

    @GetMapping("/signin")
    public String signInPage(SignInFormDto signInFormDto) {
        return "sign-in";
    }

    @PostMapping("/signin")
    public String signInSubmit(@ModelAttribute("signInFormDto") SignInFormDto signInFormDto) {
        return "sign-in";
    }
}
