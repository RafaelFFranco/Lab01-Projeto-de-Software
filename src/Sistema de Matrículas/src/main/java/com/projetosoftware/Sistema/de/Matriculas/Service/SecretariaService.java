package com.projetosoftware.Sistema.de.Matriculas.Service;

import com.projetosoftware.Sistema.de.Matriculas.Model.Secretaria;
import com.projetosoftware.Sistema.de.Matriculas.Repository.SecretariaRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SecretariaService {

    private final SecretariaRepository secretariaRepository;
    private final Map<String, Long> tokens = new ConcurrentHashMap<>();

    public SecretariaService(SecretariaRepository secretariaRepository) {
        this.secretariaRepository = secretariaRepository;
    }

    public Optional<Secretaria> getByEmail(String email) {
        return secretariaRepository.findByEmail(email);
    }

    public Secretaria add(Secretaria secretaria) {
        return secretariaRepository.save(secretaria);
    }

    public boolean autenticar(String email, String senha) {
        return secretariaRepository.findByEmail(email)
                .map(s -> s.getSenha() != null && s.getSenha().equals(senha))
                .orElse(false);
    }

    public String gerarToken(String email) {
        return secretariaRepository.findByEmail(email)
                .map(s -> {
                    String token = UUID.randomUUID().toString();
                    tokens.put(token, s.getId());
                    return token;
                })
                .orElse(null);
    }

    public boolean validarToken(String token) {
        return token != null && tokens.containsKey(token);
    }
} 