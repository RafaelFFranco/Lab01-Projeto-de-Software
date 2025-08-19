package com.projetosoftware.Sistema.de.Matriculas.Controller;

import com.projetosoftware.Sistema.de.Matriculas.Model.Aluno;
import com.projetosoftware.Sistema.de.Matriculas.Model.Professor;
import com.projetosoftware.Sistema.de.Matriculas.Service.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    private ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
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

   /* @GetMapping("/{idProfessor}/disciplina/{idDisciplina}/alunos")
    public ResponseEntity<?> getAlunosByDisciplina(
        @PathVariable Long idProfessor,
        @PathVariable Long idDisciplina) {
    try {
        Professor professor = professorService.getById(idProfessor);
        if (professor == null) {
            return ResponseEntity.notFound().build();
        }

        return professor.getDisciplinas().stream()
                .filter(disciplina -> disciplina.getId().equals(idDisciplina))
                .findFirst()
                .map(disciplina -> {
                    List<Aluno> alunos = new java.util.ArrayList<>();
                    if (disciplina.getAlunosObrigatorios() != null) {
                        alunos.addAll(disciplina.getAlunosObrigatorios());
                    }
                    if (disciplina.getAlunosOptativos() != null) {
                        alunos.addAll(disciplina.getAlunosOptativos());
                    }
                    return ResponseEntity.ok(alunos);
                })
                .orElse(ResponseEntity.badRequest().body("Disciplina não pertence a este professor"));

    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    }
    //Remover comentario quando for incluido a classe Disciplina
    */
}
