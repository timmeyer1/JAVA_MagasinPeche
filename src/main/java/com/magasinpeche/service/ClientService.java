package com.magasinpeche.service;

import com.magasinpeche.model.Client;
import com.magasinpeche.model.Role;
import com.magasinpeche.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Client save(Client client) {
        // Ici, nous attribuons toujours le rôle USER
        client.setRole(Role.USER); // Attribue le rôle USER à tous les nouveaux utilisateurs

        // Encode le mot de passe avant de sauvegarder
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientRepository.save(client);
    }

    public Client updateProfile(Client client, String nom, String prenom, String Email) {
        client.setPrenom(prenom);
        client.setNom(nom);
        client.setEmail(Email);
        return clientRepository.save(client);
    }

    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    // find by id
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }
}
