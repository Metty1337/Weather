package metty1337.controller;

import metty1337.dto.SignInFormDto;
import metty1337.dto.SignUpFormDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/signup")
    public String signUpPage(SignUpFormDto signupFormDto) {
        return "sign-up";
    }

    @PostMapping("/signup")
    public String signUpSubmit(@ModelAttribute("signupFormDto") SignUpFormDto signUpFormDto) {
        return "sign-up";
    }

    @GetMapping("/signin")
    public String signInPage(SignInFormDto signInFormDto) {
        return "sign-in";
    }

    @PostMapping("/signin")
    public String signInSubmit(@ModelAttribute("signInFormDto") SignInFormDto signInFormDto) {
        return "sign-in";
    }

    @GetMapping("/signup-with-errors")
    public String signUpPageWithErrors(SignUpFormDto signUpFormDto) {
        return "sign-up-with-errors";
    }
}
