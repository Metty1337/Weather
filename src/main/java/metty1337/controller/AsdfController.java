package metty1337.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AsdfController {
    @GetMapping("/")
    public String display() {
        return "index";
    }
}
