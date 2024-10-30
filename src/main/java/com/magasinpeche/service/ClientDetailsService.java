package com.magasinpeche.service;

import com.magasinpeche.model.Client;
import com.magasinpeche.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Trouver le client par email (nom d'utilisateur)
        Client client = clientRepository.findByEmail(email);
        if (client == null) {
            throw new UsernameNotFoundException("Client non trouvé avec l'email : " + email);
        }

        // Convertir Client en un objet UserDetails que Spring Security peut gérer
        return User.builder()
                .username(client.getEmail())
                .password(client.getPassword())
                .roles("USER")
                .build();
    }
}
