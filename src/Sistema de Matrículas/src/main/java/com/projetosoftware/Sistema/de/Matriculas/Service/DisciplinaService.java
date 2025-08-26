package com.projetosoftware.Sistema.de.Matriculas.Service;

import com.projetosoftware.Sistema.de.Matriculas.Model.*;
import com.projetosoftware.Sistema.de.Matriculas.Repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    private DisciplinaRepository disciplinaRepository;
    private OfertaDisciplinaRepository ofertaDisciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository,
                             OfertaDisciplinaRepository ofertaDisciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.ofertaDisciplinaRepository = ofertaDisciplinaRepository;
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
                disciplina.setNumCreditos(disciplinaAtualizado.getNumCreditos());
                disciplina.setProfessor(disciplinaAtualizado.getProfessor());
                disciplina.setCurso(disciplinaAtualizado.getCurso());
                return disciplinaRepository.save(disciplina);
            })
            .orElse(null);
    }

    public OfertaDisciplina criarOferta(Long disciplinaId, String semestre, Integer capacidadeMaxima) {
        Disciplina disciplina = getById(disciplinaId);
        if (disciplina == null) return null;
        OfertaDisciplina oferta = new OfertaDisciplina();
        oferta.setDisciplina(disciplina);
        oferta.setSemestre(semestre);
        if (capacidadeMaxima != null) oferta.setCapacidadeMaxima(capacidadeMaxima);
        oferta.setStatus(OfertaDisciplina.StatusOferta.PENDENTE);
        return ofertaDisciplinaRepository.save(oferta);
    }

    public List<OfertaDisciplina> listarOfertasPorSemestre(String semestre) {
        return ofertaDisciplinaRepository.findBySemestre(semestre);
    }

    public OfertaDisciplina atualizarStatusOferta(Long ofertaId, OfertaDisciplina.StatusOferta status) {
        return ofertaDisciplinaRepository.findById(ofertaId)
                .map(o -> { o.setStatus(status); return ofertaDisciplinaRepository.save(o); })
                .orElse(null);
    }
}
