package unlu.sip.pga.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "http://localhost:5173")
@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
