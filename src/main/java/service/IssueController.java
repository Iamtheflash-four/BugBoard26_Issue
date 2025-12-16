package service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IssueController {
    @GetMapping("/")
    public String hello() {
        return "Server avviato e funzionante su Render!";
    }
}
