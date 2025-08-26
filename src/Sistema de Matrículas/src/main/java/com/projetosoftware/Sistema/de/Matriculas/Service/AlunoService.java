package com.projetosoftware.Sistema.de.Matriculas.Service;

import com.projetosoftware.Sistema.de.Matriculas.Model.Aluno;
import com.projetosoftware.Sistema.de.Matriculas.Repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    private AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public List<Aluno> getAll() {
        return alunoRepository.findAll();
    }

    public Aluno getById(Long id) {
        Optional<Aluno> aluno = alunoRepository.findById(id);
        return aluno.isPresent() ? aluno.get() : null;
    }

    public Optional<Aluno> getByEmail(String email) {
        return alunoRepository.findByEmail(email);
    }

    public Aluno add(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public void delete(Long id) {
        Optional<Aluno> aluno = alunoRepository.findById(id);
        if (aluno.isPresent()) {
            alunoRepository.delete(aluno.get());
        }
    }

    public boolean autenticar(String email, String senha) {
        return alunoRepository.findByEmail(email)
                .map(a -> a.getSenha() != null && a.getSenha().equals(senha))
                .orElse(false);
    }
}
