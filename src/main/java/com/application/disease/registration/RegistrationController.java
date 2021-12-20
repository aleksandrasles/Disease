package com.application.disease.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;


    @PostMapping("/process_register")
    public String processRegister(RegistrationRequest request, Model model) {
        try {
            registrationService.register(request);
            return "register_success";
        } catch (Exception ex) {
            model.addAttribute("message", ex.getMessage());
            return "signup_form";
        }
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "signup_form";
    }


    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

}
