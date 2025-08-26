package com.projetosoftware.Sistema.de.Matriculas.Controller;

import com.projetosoftware.Sistema.de.Matriculas.Model.Aluno;
import com.projetosoftware.Sistema.de.Matriculas.Model.Matricula;
import com.projetosoftware.Sistema.de.Matriculas.Service.AlunoService;
import com.projetosoftware.Sistema.de.Matriculas.Repository.MatriculaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    private AlunoService alunoService;
    private MatriculaRepository matriculaRepository;

    public AlunoController(AlunoService alunoService, MatriculaRepository matriculaRepository) {
        this.alunoService = alunoService;
        this.matriculaRepository = matriculaRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Aluno aluno) {
        try{
            return ResponseEntity.ok(alunoService.add(aluno));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/getall")
    public ResponseEntity<?> getAll() {
        try{
            return ResponseEntity.ok(alunoService.getAll());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(alunoService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            alunoService.delete(id);
            return ResponseEntity.ok("Aluno com id:" +id+" deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String senha) {
        try {
            boolean ok = alunoService.autenticar(email, senha);
            return ok ? ResponseEntity.ok("Login bem-sucedido") : ResponseEntity.status(401).body("Credenciais inválidas");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{alunoId}/matriculas")
    public ResponseEntity<?> listarMatriculas(@PathVariable Long alunoId, @RequestParam String semestre) {
        try {
            Aluno aluno = alunoService.getById(alunoId);
            if (aluno == null) return ResponseEntity.notFound().build();
            List<Matricula> matriculas = matriculaRepository.findByAlunoAndOferta_Semestre(aluno, semestre);
            return ResponseEntity.ok(matriculas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
