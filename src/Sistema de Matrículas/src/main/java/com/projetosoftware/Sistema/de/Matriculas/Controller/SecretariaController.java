package com.projetosoftware.Sistema.de.Matriculas.Controller;

import com.projetosoftware.Sistema.de.Matriculas.Model.*;
import com.projetosoftware.Sistema.de.Matriculas.Repository.*;
import com.projetosoftware.Sistema.de.Matriculas.Service.DisciplinaService;
import com.projetosoftware.Sistema.de.Matriculas.Service.SecretariaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secretaria")
public class SecretariaController {

    private final SecretariaService secretariaService;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final CursoRepository cursoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final PeriodoMatriculasRepository periodoRepository;
    private final DisciplinaService disciplinaService;

    public SecretariaController(SecretariaService secretariaService,
                                AlunoRepository alunoRepository,
                                ProfessorRepository professorRepository,
                                CursoRepository cursoRepository,
                                DisciplinaRepository disciplinaRepository,
                                PeriodoMatriculasRepository periodoRepository,
                                DisciplinaService disciplinaService) {
        this.secretariaService = secretariaService;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.cursoRepository = cursoRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.periodoRepository = periodoRepository;
        this.disciplinaService = disciplinaService;
    }

    private boolean autorizado(String token) {
        return secretariaService.validarToken(token);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Secretaria sec) {
        try { return ResponseEntity.ok(secretariaService.add(sec)); }
        catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String senha) {
        try {
            if (!secretariaService.autenticar(email, senha)) return ResponseEntity.status(401).body("Credenciais inválidas");
            String token = secretariaService.gerarToken(email);
            return ResponseEntity.ok(token);
        } catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    // CRUDs administrados pela secretaria (token via header X-Auth-Token)

    @PostMapping("/aluno")
    public ResponseEntity<?> criarAluno(@RequestHeader("X-Auth-Token") String token, @RequestBody Aluno aluno) {
        if (!autorizado(token)) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(alunoRepository.save(aluno));
    }

    @PostMapping("/professor")
    public ResponseEntity<?> criarProfessor(@RequestHeader("X-Auth-Token") String token, @RequestBody Professor professor) {
        if (!autorizado(token)) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(professorRepository.save(professor));
    }

    @PostMapping("/curso")
    public ResponseEntity<?> criarCurso(@RequestHeader("X-Auth-Token") String token, @RequestBody Curso curso) {
        if (!autorizado(token)) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(cursoRepository.save(curso));
    }

    @PostMapping("/disciplina")
    public ResponseEntity<?> criarDisciplina(@RequestHeader("X-Auth-Token") String token, @RequestBody Disciplina disciplina) {
        if (!autorizado(token)) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(disciplinaRepository.save(disciplina));
    }

    @PostMapping("/periodo")
    public ResponseEntity<?> abrirPeriodo(@RequestHeader("X-Auth-Token") String token, @RequestBody PeriodoMatriculas periodo) {
        if (!autorizado(token)) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(periodoRepository.save(periodo));
    }

    @PostMapping("/oferta/{disciplinaId}")
    public ResponseEntity<?> criarOferta(@RequestHeader("X-Auth-Token") String token,
                                         @PathVariable Long disciplinaId,
                                         @RequestParam String semestre,
                                         @RequestParam(required = false) Integer capacidadeMaxima) {
        if (!autorizado(token)) return ResponseEntity.status(403).build();
        OfertaDisciplina oferta = disciplinaService.criarOferta(disciplinaId, semestre, capacidadeMaxima);
        return oferta == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(oferta);
    }
} 