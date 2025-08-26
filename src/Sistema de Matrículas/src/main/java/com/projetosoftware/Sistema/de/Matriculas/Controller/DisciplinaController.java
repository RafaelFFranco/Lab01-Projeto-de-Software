package com.projetosoftware.Sistema.de.Matriculas.Controller;

import com.projetosoftware.Sistema.de.Matriculas.Model.Disciplina;
import com.projetosoftware.Sistema.de.Matriculas.Model.OfertaDisciplina;
import com.projetosoftware.Sistema.de.Matriculas.Service.DisciplinaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplina")
public class DisciplinaController {

    private DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Disciplina disciplina) {
        try {
            return ResponseEntity.ok(disciplinaService.add(disciplina));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(disciplinaService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(disciplinaService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            disciplinaService.delete(id);
            return ResponseEntity.ok("Disciplina com id: " + id + " deletada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Disciplina disciplina) {
        try {
            Disciplina atualizada = disciplinaService.update(id, disciplina);
            if (atualizada == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(atualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/oferta")
    public ResponseEntity<?> criarOferta(@PathVariable Long id,
                                         @RequestParam String semestre,
                                         @RequestParam(required = false) Integer capacidadeMaxima) {
        try {
            OfertaDisciplina oferta = disciplinaService.criarOferta(id, semestre, capacidadeMaxima);
            if (oferta == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(oferta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/ofertas")
    public ResponseEntity<?> listarOfertas(@RequestParam String semestre) {
        try {
            List<OfertaDisciplina> ofertas = disciplinaService.listarOfertasPorSemestre(semestre);
            return ResponseEntity.ok(ofertas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
