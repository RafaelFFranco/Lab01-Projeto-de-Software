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

    public Aluno add(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public void delete(Long id) {
        Optional<Aluno> aluno = alunoRepository.findById(id);
        if (aluno.isPresent()) {
            alunoRepository.delete(aluno.get());
        }
    }
}
