package com.projetosoftware.Sistema.de.Matriculas.Controller;

import com.projetosoftware.Sistema.de.Matriculas.Model.Aluno;
import com.projetosoftware.Sistema.de.Matriculas.Service.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    private AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
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
}
