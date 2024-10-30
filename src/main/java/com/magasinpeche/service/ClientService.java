package com.magasinpeche.service;

import com.magasinpeche.model.Client;
import com.magasinpeche.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injection du PasswordEncoder

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Client createClient(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword())); // Encoder le mot de passe
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client clientDetails) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client != null) {
            client.setNom(clientDetails.getNom());
            client.setPrenom(clientDetails.getPrenom());
            client.setEmail(clientDetails.getEmail());

            client.setPassword(passwordEncoder.encode(clientDetails.getPassword()));
            client.setAdresse(clientDetails.getAdresse());
            client.setTelephone(clientDetails.getTelephone());
            return clientRepository.save(client);
        }
        return null;
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
