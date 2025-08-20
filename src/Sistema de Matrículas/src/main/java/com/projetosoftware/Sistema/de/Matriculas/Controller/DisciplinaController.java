package com.projetosoftware.Sistema.de.Matriculas.Controller;

import com.projetosoftware.Sistema.de.Matriculas.Model.Aluno;
import com.projetosoftware.Sistema.de.Matriculas.Model.Professor;
import com.projetosoftware.Sistema.de.Matriculas.Model.Disciplina;
import com.projetosoftware.Sistema.de.Matriculas.Service.ProfessorService;
import com.projetosoftware.Sistema.de.Matriculas.Service.DisciplinaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disciplina")
public class DisciplinaController {

    private DisciplinaService disciplinaService;

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
}
