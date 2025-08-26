package com.projetosoftware.Sistema.de.Matriculas.Controller;

import com.projetosoftware.Sistema.de.Matriculas.Model.Matricula;
import com.projetosoftware.Sistema.de.Matriculas.Service.MatriculaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matricula")
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestParam Long alunoId,
                                       @RequestParam Long ofertaId,
                                       @RequestParam Matricula.Tipo tipo) {
        try {
            return ResponseEntity.ok(matriculaService.matricular(alunoId, ofertaId, tipo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cancelar/{matriculaId}")
    public ResponseEntity<?> cancelar(@PathVariable Long matriculaId) {
        try {
            matriculaService.cancelarMatricula(matriculaId);
            return ResponseEntity.ok("Matrícula cancelada");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/oferta/{ofertaId}")
    public ResponseEntity<?> listarPorOferta(@PathVariable Long ofertaId) {
        try {
            return ResponseEntity.ok(matriculaService.listarPorOferta(ofertaId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/finalizar")
    public ResponseEntity<?> finalizarPeriodo(@RequestParam String semestre) {
        try {
            matriculaService.finalizarPeriodo(semestre);
            return ResponseEntity.ok("Período finalizado e ofertas atualizadas");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 