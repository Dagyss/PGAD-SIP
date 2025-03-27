package unlu.sip.pga.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173") 
@RestController  

public class HomeController {

    @GetMapping("/api/home")  
    public String home() {
        return "home!";
    }
}

