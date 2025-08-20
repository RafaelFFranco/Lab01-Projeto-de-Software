package com.projetosoftware.Sistema.de.Matriculas.Service;

import com.projetosoftware.Sistema.de.Matriculas.Model.Disciplina;
import com.projetosoftware.Sistema.de.Matriculas.Model.Professor;
import com.projetosoftware.Sistema.de.Matriculas.Repository.DisciplinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    private DisciplinaRepository disciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public List<Disciplina> getAll() {
        return disciplinaRepository.findAll();
    }

    public Disciplina getById(Long id) {
        Optional<Disciplina> disciplina = disciplinaRepository.findById(id);
        return disciplina.isPresent() ? disciplina.get() : null;
    }

    public Disciplina add(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    public void delete(Long id) {
        Optional<Disciplina> disciplina = disciplinaRepository.findById(id);
        if (disciplina.isPresent()) {
            disciplinaRepository.delete(disciplina.get());
        }
    }

    public Disciplina update(Long id, Disciplina disciplinaAtualizado) {
        return disciplinaRepository.findById(id)
            .map(disciplina -> {
                disciplina.setNome(disciplinaAtualizado.getNome());
                disciplina.setNumCredito(disciplinaAtualizado.getNumCredito());
                disciplina.setProfessor(disciplinaAtualizado.getProfessor());
                return disciplinaRepository.save(disciplina);
            })
            .orElse(null);
    }
}
