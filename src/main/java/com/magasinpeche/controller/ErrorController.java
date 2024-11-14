package com.magasinpeche.controller;

import com.magasinpeche.exception.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/some-page")
    public String getPage() {
        // Code qui peut potentiellement lever une exception
        throw new ResourceNotFoundException("Page non trouvée !");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException ex, Model model) {
        model.addAttribute("error", "Page non trouvée !");
        return "error/404"; // Redirige vers la page 404
    }
}
