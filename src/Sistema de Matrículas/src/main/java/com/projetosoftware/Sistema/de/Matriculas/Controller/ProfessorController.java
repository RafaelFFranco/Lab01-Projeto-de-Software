package com.projetosoftware.Sistema.de.Matriculas.Controller;

import com.projetosoftware.Sistema.de.Matriculas.Model.Aluno;
import com.projetosoftware.Sistema.de.Matriculas.Model.Matricula;
import com.projetosoftware.Sistema.de.Matriculas.Model.Professor;
import com.projetosoftware.Sistema.de.Matriculas.Model.OfertaDisciplina;
import com.projetosoftware.Sistema.de.Matriculas.Repository.MatriculaRepository;
import com.projetosoftware.Sistema.de.Matriculas.Service.ProfessorService;
import com.projetosoftware.Sistema.de.Matriculas.Repository.OfertaDisciplinaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    private ProfessorService professorService;
    private MatriculaRepository matriculaRepository;
    private OfertaDisciplinaRepository ofertaDisciplinaRepository;

    public ProfessorController(ProfessorService professorService,
                               MatriculaRepository matriculaRepository,
                               OfertaDisciplinaRepository ofertaDisciplinaRepository) {
        this.professorService = professorService;
        this.matriculaRepository = matriculaRepository;
        this.ofertaDisciplinaRepository = ofertaDisciplinaRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Professor professor) {
        try {
            return ResponseEntity.ok(professorService.add(professor));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(professorService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(professorService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            professorService.delete(id);
            return ResponseEntity.ok("Professor com id: " + id + " deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Professor professor) {
    try {
        Professor atualizado = professorService.update(id, professor);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String senha) {
        try {
            boolean ok = professorService.autenticar(email, senha);
            return ok ? ResponseEntity.ok("Login bem-sucedido") : ResponseEntity.status(401).body("Credenciais inválidas");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{professorId}/oferta/{ofertaId}/alunos")
    public ResponseEntity<?> listarAlunosPorOferta(@PathVariable Long professorId, @PathVariable Long ofertaId) {
        try {
            Professor professor = professorService.getById(professorId);
            if (professor == null) return ResponseEntity.notFound().build();

            OfertaDisciplina oferta = ofertaDisciplinaRepository.findById(ofertaId)
                    .orElse(null);
            if (oferta == null || oferta.getDisciplina() == null || oferta.getDisciplina().getProfessor() == null ||
                !oferta.getDisciplina().getProfessor().getId().equals(professor.getId())) {
                return ResponseEntity.badRequest().body("Oferta não pertence a este professor");
            }

            List<Aluno> alunos = matriculaRepository.findByOferta(oferta).stream()
                    .filter(m -> m.getStatus() == Matricula.Status.ATIVA)
                    .map(Matricula::getAluno)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(alunos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
