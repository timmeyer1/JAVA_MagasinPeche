package com.magasinpeche.controller;

import com.magasinpeche.model.Client;
import com.magasinpeche.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Page d'inscription
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("client", new Client());
        return "register";  // Retourne la vue pour l'inscription
    }

    // Gestion de l'inscription
    @PostMapping("/register")
    public String registerClient(Client client) {
        // Encoder le mot de passe
        client.setPassword(passwordEncoder.encode(client.getPassword())); //
        clientRepository.save(client);  // Sauvegarde client dans la base de données
        return "redirect:/auth/login";  // Redirige vers la page de connexion
    }

    // Page de connexion
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";  // Retourne la vue pour la connexion
    }
}
