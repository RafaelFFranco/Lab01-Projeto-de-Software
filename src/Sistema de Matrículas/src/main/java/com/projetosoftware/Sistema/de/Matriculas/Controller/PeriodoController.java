package com.projetosoftware.Sistema.de.Matriculas.Controller;

import com.projetosoftware.Sistema.de.Matriculas.Model.PeriodoMatriculas;
import com.projetosoftware.Sistema.de.Matriculas.Repository.PeriodoMatriculasRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/periodo")
public class PeriodoController {

    private final PeriodoMatriculasRepository periodoRepository;

    public PeriodoController(PeriodoMatriculasRepository periodoRepository) {
        this.periodoRepository = periodoRepository;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody PeriodoMatriculas periodo) {
        try {
            return ResponseEntity.ok(periodoRepository.save(periodo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{semestre}")
    public ResponseEntity<?> obter(@PathVariable String semestre) {
        try {
            return periodoRepository.findBySemestre(semestre)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 