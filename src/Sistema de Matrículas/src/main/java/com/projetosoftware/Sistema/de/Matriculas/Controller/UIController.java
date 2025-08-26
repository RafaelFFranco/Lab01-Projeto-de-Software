package com.projetosoftware.Sistema.de.Matriculas.Controller;

import com.projetosoftware.Sistema.de.Matriculas.Repository.AlunoRepository;
import com.projetosoftware.Sistema.de.Matriculas.Repository.DisciplinaRepository;
import com.projetosoftware.Sistema.de.Matriculas.Repository.ProfessorRepository;
import com.projetosoftware.Sistema.de.Matriculas.Repository.CursoRepository;
import com.projetosoftware.Sistema.de.Matriculas.Repository.PeriodoMatriculasRepository;
import com.projetosoftware.Sistema.de.Matriculas.Service.MatriculaService;
import com.projetosoftware.Sistema.de.Matriculas.Service.DisciplinaService;
import com.projetosoftware.Sistema.de.Matriculas.Service.AlunoService;
import com.projetosoftware.Sistema.de.Matriculas.Service.ProfessorService;
import com.projetosoftware.Sistema.de.Matriculas.Service.SecretariaService;
import com.projetosoftware.Sistema.de.Matriculas.Model.Curso;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UIController {

    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final CursoRepository cursoRepository;
    private final AlunoService alunoService;
    private final ProfessorService professorService;
    private final SecretariaService secretariaService;
    private final PeriodoMatriculasRepository periodoRepository;
    private final MatriculaService matriculaService;
    private final DisciplinaService disciplinaService;

    public UIController(AlunoRepository alunoRepository,
                        ProfessorRepository professorRepository,
                        DisciplinaRepository disciplinaRepository,
                        CursoRepository cursoRepository,
                        AlunoService alunoService,
                        ProfessorService professorService,
                        SecretariaService secretariaService,
                        PeriodoMatriculasRepository periodoRepository,
                        MatriculaService matriculaService,
                        DisciplinaService disciplinaService) {
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.cursoRepository = cursoRepository;
        this.alunoService = alunoService;
        this.professorService = professorService;
        this.secretariaService = secretariaService;
        this.periodoRepository = periodoRepository;
        this.matriculaService = matriculaService;
        this.disciplinaService = disciplinaService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/ui/alunos")
    public String alunos(Model model) {
        model.addAttribute("alunos", alunoRepository.findAll());
        return "alunos";
    }

    @GetMapping("/ui/professores")
    public String professores(Model model) {
        model.addAttribute("professores", professorRepository.findAll());
        return "professores";
    }

    @GetMapping("/ui/disciplinas")
    public String disciplinas(Model model) {
        model.addAttribute("disciplinas", disciplinaRepository.findAll());
        return "disciplinas";
    }

    @GetMapping("/ui/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/ui/curso/novo")
    public String novoCursoPage() {
        return "curso_novo";
    }

    @org.springframework.web.bind.annotation.PostMapping("/ui/login")
    public String login(org.springframework.ui.Model model,
                        @org.springframework.web.bind.annotation.RequestParam String email,
                        @org.springframework.web.bind.annotation.RequestParam String senha,
                        @org.springframework.web.bind.annotation.RequestParam String tipo) {
        boolean ok = false;
        switch (tipo) {
            case "aluno" -> ok = alunoService.autenticar(email, senha);
            case "professor" -> ok = professorService.autenticar(email, senha);
            case "secretaria" -> ok = secretariaService.autenticar(email, senha);
        }
        model.addAttribute("mensagem", ok ? "Login bem-sucedido" : "Credenciais inválidas");
        return "login";
    }

    @org.springframework.web.bind.annotation.PostMapping("/ui/curso/novo")
    public String salvarCurso(org.springframework.ui.Model model,
                              @org.springframework.web.bind.annotation.RequestParam String nome,
                              @org.springframework.web.bind.annotation.RequestParam int totalCreditos) {
        Curso curso = new Curso();
        curso.setNome(nome);
        curso.setTotalCreditos(totalCreditos);
        cursoRepository.save(curso);
        model.addAttribute("mensagem", "Curso salvo com sucesso!");
        return "curso_novo";
    }

    // --- Aluno ---
    @GetMapping("/ui/aluno/novo")
    public String novoAlunoPage() { return "aluno_novo"; }

    @org.springframework.web.bind.annotation.PostMapping("/ui/aluno/novo")
    public String salvarAluno(Model model,
                              @RequestParam String nome,
                              @RequestParam String email,
                              @RequestParam String senha) {
        var a = new com.projetosoftware.Sistema.de.Matriculas.Model.Aluno();
        a.setNome(nome); a.setEmail(email); a.setSenha(senha);
        alunoRepository.save(a);
        model.addAttribute("mensagem", "Aluno salvo com sucesso!");
        return "aluno_novo";
    }

    // --- Professor ---
    @GetMapping("/ui/professor/novo")
    public String novoProfessorPage() { return "professor_novo"; }

    @org.springframework.web.bind.annotation.PostMapping("/ui/professor/novo")
    public String salvarProfessor(Model model,
                                  @RequestParam String nome,
                                  @RequestParam String email,
                                  @RequestParam String senha,
                                  @RequestParam String departamento) {
        var p = new com.projetosoftware.Sistema.de.Matriculas.Model.Professor();
        p.setNome(nome); p.setEmail(email); p.setSenha(senha); p.setDepartamento(departamento);
        professorRepository.save(p);
        model.addAttribute("mensagem", "Professor salvo com sucesso!");
        return "professor_novo";
    }

    // --- Disciplina ---
    @GetMapping("/ui/disciplina/nova")
    public String novaDisciplinaPage(Model model) {
        model.addAttribute("cursos", cursoRepository.findAll());
        model.addAttribute("professores", professorRepository.findAll());
        return "disciplina_nova";
    }

    @org.springframework.web.bind.annotation.PostMapping("/ui/disciplina/nova")
    public String salvarDisciplina(Model model,
                                   @RequestParam String nome,
                                   @RequestParam int numCreditos,
                                   @RequestParam(required = false) Long cursoId,
                                   @RequestParam(required = false) Long professorId) {
        var d = new com.projetosoftware.Sistema.de.Matriculas.Model.Disciplina();
        d.setNome(nome); d.setNumCreditos(numCreditos);
        if (cursoId != null) {
            cursoRepository.findById(cursoId).ifPresent(d::setCurso);
        }
        if (professorId != null) {
            professorRepository.findById(professorId).ifPresent(d::setProfessor);
        }
        disciplinaRepository.save(d);
        model.addAttribute("mensagem", "Disciplina salva com sucesso!");
        model.addAttribute("cursos", cursoRepository.findAll());
        model.addAttribute("professores", professorRepository.findAll());
        return "disciplina_nova";
    }

    // --- Período ---
    @GetMapping("/ui/periodo/novo")
    public String novoPeriodoPage() { return "periodo_novo"; }

    @org.springframework.web.bind.annotation.PostMapping("/ui/periodo/novo")
    public String salvarPeriodo(Model model,
                                @RequestParam String semestre) {
        var periodo = new com.projetosoftware.Sistema.de.Matriculas.Model.PeriodoMatriculas();
        periodo.setSemestre(semestre);
        periodoRepository.save(periodo);
        model.addAttribute("mensagem", "Período salvo com sucesso!");
        return "periodo_novo";
    }

    // --- Oferta ---
    @GetMapping("/ui/oferta/nova")
    public String novaOfertaPage(Model model) {
        model.addAttribute("disciplinas", disciplinaRepository.findAll());
        return "oferta_nova";
    }

    @org.springframework.web.bind.annotation.PostMapping("/ui/oferta/nova")
    public String salvarOferta(Model model,
                               @RequestParam Long disciplinaId,
                               @RequestParam String semestre,
                               @RequestParam(required = false) Integer capacidadeMaxima) {
        var oferta = disciplinaService.criarOferta(disciplinaId, semestre, capacidadeMaxima);
        model.addAttribute("mensagem", oferta != null ? "Oferta criada!" : "Disciplina não encontrada");
        model.addAttribute("disciplinas", disciplinaRepository.findAll());
        return "oferta_nova";
    }

    // --- Matrícula ---
    @GetMapping("/ui/matricula")
    public String matriculaPage() { return "matricula"; }

    @org.springframework.web.bind.annotation.PostMapping("/ui/matricula/registrar")
    public String registrarMatricula(Model model,
                                     @RequestParam Long alunoId,
                                     @RequestParam Long ofertaId,
                                     @RequestParam com.projetosoftware.Sistema.de.Matriculas.Model.Matricula.Tipo tipo) {
        try {
            var result = matriculaService.matricular(alunoId, ofertaId, tipo);
            model.addAttribute("mensagem", "Matrícula registrada: ID " + result.getId());
        } catch (Exception e) {
            model.addAttribute("mensagem", e.getMessage());
        }
        return "matricula";
    }

    @org.springframework.web.bind.annotation.PostMapping("/ui/matricula/cancelar")
    public String cancelarMatricula(Model model,
                                    @RequestParam Long matriculaId) {
        try {
            matriculaService.cancelarMatricula(matriculaId);
            model.addAttribute("mensagem", "Matrícula cancelada!");
        } catch (Exception e) {
            model.addAttribute("mensagem", e.getMessage());
        }
        return "matricula";
    }
}


