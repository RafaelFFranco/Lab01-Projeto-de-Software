package com.projetosoftware.Sistema.de.Matriculas.Service;

import com.projetosoftware.Sistema.de.Matriculas.Model.Professor;
import com.projetosoftware.Sistema.de.Matriculas.Repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    private ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public List<Professor> getAll() {
        return professorRepository.findAll();
    }

    public Professor getById(Long id) {
        Optional<Professor> professor = professorRepository.findById(id);
        return professor.isPresent() ? professor.get() : null;
    }

    public Optional<Professor> getByEmail(String email) {
        return professorRepository.findByEmail(email);
    }

    public Professor add(Professor professor) {
        return professorRepository.save(professor);
    }

    public void delete(Long id) {
        Optional<Professor> professor = professorRepository.findById(id);
        if (professor.isPresent()) {
            professorRepository.delete(professor.get());
        }
    }

    public Professor update(Long id, Professor professorAtualizado) {
        return professorRepository.findById(id)
            .map(professor -> {
                professor.setNome(professorAtualizado.getNome());
                professor.setEmail(professorAtualizado.getEmail());
                professor.setDepartamento(professorAtualizado.getDepartamento());
                professor.setSenha(professorAtualizado.getSenha());
                return professorRepository.save(professor);
            })
            .orElse(null);
    }

    public boolean autenticar(String email, String senha) {
        return professorRepository.findByEmail(email)
                .map(p -> p.getSenha() != null && p.getSenha().equals(senha))
                .orElse(false);
    }
}
