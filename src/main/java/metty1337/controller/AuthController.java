package metty1337.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import metty1337.dto.SignInFormDto;
import metty1337.dto.SignUpFormDto;
import metty1337.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @GetMapping("/signup")
    public String signUpPage(SignUpFormDto signUpFormDto) {
        return "sign-up";
    }

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute("signUpFormDto") SignUpFormDto signUpFormDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sign-up-with-errors";
        }
        userService.createUser(signUpFormDto);
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
