package com.projetosoftware.Sistema.de.Matriculas.Controller;

import com.projetosoftware.Sistema.de.Matriculas.Repository.AlunoRepository;
import com.projetosoftware.Sistema.de.Matriculas.Repository.DisciplinaRepository;
import com.projetosoftware.Sistema.de.Matriculas.Repository.ProfessorRepository;
import com.projetosoftware.Sistema.de.Matriculas.Repository.CursoRepository;
import com.projetosoftware.Sistema.de.Matriculas.Repository.PeriodoMatriculasRepository;
import com.projetosoftware.Sistema.de.Matriculas.Repository.OfertaDisciplinaRepository;
import com.projetosoftware.Sistema.de.Matriculas.Repository.MatriculaRepository;
import com.projetosoftware.Sistema.de.Matriculas.Service.MatriculaService;
import com.projetosoftware.Sistema.de.Matriculas.Service.DisciplinaService;
import com.projetosoftware.Sistema.de.Matriculas.Service.AlunoService;
import com.projetosoftware.Sistema.de.Matriculas.Service.ProfessorService;
import com.projetosoftware.Sistema.de.Matriculas.Service.SecretariaService;
import com.projetosoftware.Sistema.de.Matriculas.Model.Curso;
import com.projetosoftware.Sistema.de.Matriculas.Model.Aluno;
import com.projetosoftware.Sistema.de.Matriculas.Model.OfertaDisciplina;
import com.projetosoftware.Sistema.de.Matriculas.Model.Matricula;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;
import com.projetosoftware.Sistema.de.Matriculas.Model.Professor;
import com.projetosoftware.Sistema.de.Matriculas.Model.Disciplina;

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
    private final OfertaDisciplinaRepository ofertaDisciplinaRepository;
    private final MatriculaRepository matriculaRepository;

    public UIController(AlunoRepository alunoRepository,
                        ProfessorRepository professorRepository,
                        DisciplinaRepository disciplinaRepository,
                        CursoRepository cursoRepository,
                        AlunoService alunoService,
                        ProfessorService professorService,
                        SecretariaService secretariaService,
                        PeriodoMatriculasRepository periodoRepository,
                        MatriculaService matriculaService,
                        DisciplinaService disciplinaService,
                        OfertaDisciplinaRepository ofertaDisciplinaRepository,
                        MatriculaRepository matriculaRepository) {
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
        this.ofertaDisciplinaRepository = ofertaDisciplinaRepository;
        this.matriculaRepository = matriculaRepository;
    }

    @GetMapping("/")
    public String index(HttpSession session) {
        Object usuarioLogado = session.getAttribute("usuarioLogado");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        if (usuarioLogado != null && tipoUsuario != null) {
            switch (tipoUsuario) {
                case "aluno":
                    return "redirect:/ui/aluno/dashboard";
                case "professor":
                    return "redirect:/ui/professor/dashboard";
                case "secretaria":
                    return "redirect:/ui/secretaria/dashboard";
            }
        }
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

    @PostMapping("/ui/login")
    public String login(Model model,
                        @RequestParam String email,
                        @RequestParam String senha,
                        @RequestParam String tipo,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        boolean ok = false;
        Object usuario = null;
        
        switch (tipo) {
            case "aluno" -> {
                ok = alunoService.autenticar(email, senha);
                if (ok) {
                    usuario = alunoService.getByEmail(email).orElse(null);
                }
            }
            case "professor" -> {
                ok = professorService.autenticar(email, senha);
                if (ok) {
                    usuario = professorService.getByEmail(email).orElse(null);
                }
            }
            case "secretaria" -> {
                ok = secretariaService.autenticar(email, senha);
                if (ok) {
                    usuario = secretariaService.getByEmail(email).orElse(null);
                }
            }
        }
        
        if (ok && usuario != null) {
            session.setAttribute("usuarioLogado", usuario);
            session.setAttribute("tipoUsuario", tipo);
            return "redirect:/ui/" + tipo + "/dashboard";
        } else {
            model.addAttribute("mensagem", "Credenciais inválidas");
            return "login";
        }
    }

    @GetMapping("/ui/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("mensagem", "Logout realizado com sucesso!");
        return "redirect:/";
    }

    @GetMapping("/ui/test")
    public String testPage(Model model) {
        model.addAttribute("mensagem", "Sistema funcionando!");
        model.addAttribute("timestamp", System.currentTimeMillis());
        return "test";
    }

    @GetMapping("/ui/test-professor")
    public String testProfessorPage(Model model, HttpSession session) {
        Object usuarioLogado = session.getAttribute("usuarioLogado");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        if (usuarioLogado == null || !"professor".equals(tipoUsuario)) {
            return "redirect:/ui/login";
        }
        
        Professor professor = (Professor) usuarioLogado;
        model.addAttribute("professor", professor);
        model.addAttribute("mensagem", "Teste do Professor funcionando!");
        model.addAttribute("timestamp", System.currentTimeMillis());
        
        return "test_professor";
    }

    @GetMapping("/ui/test-db")
    public String testDatabase(Model model) {
        try {
            // Testar repositórios
            long numAlunos = alunoRepository.count();
            long numProfessores = professorRepository.count();
            long numDisciplinas = disciplinaRepository.count();
            
            model.addAttribute("mensagem", "Banco de dados funcionando!");
            model.addAttribute("numAlunos", numAlunos);
            model.addAttribute("numProfessores", numProfessores);
            model.addAttribute("numDisciplinas", numDisciplinas);
            model.addAttribute("status", "OK");
            
        } catch (Exception e) {
            model.addAttribute("mensagem", "Erro no banco de dados: " + e.getMessage());
            model.addAttribute("status", "ERRO");
            model.addAttribute("erro", e.getMessage());
            e.printStackTrace();
        }
        
        return "test_db";
    }

    // --- Dashboard do Aluno ---
    @GetMapping("/ui/aluno/dashboard")
    public String alunoDashboard(Model model, HttpSession session) {
        Object usuarioLogado = session.getAttribute("usuarioLogado");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        if (usuarioLogado == null || !"aluno".equals(tipoUsuario) || !(usuarioLogado instanceof Aluno)) {
            return "redirect:/ui/login";
        }
        
        Aluno aluno = (Aluno) usuarioLogado;
        model.addAttribute("aluno", aluno);
        
        try {
            // Por enquanto, vamos usar listas vazias para evitar erros de banco
            model.addAttribute("ofertasDisponiveis", List.of());
            model.addAttribute("matriculasAtivas", List.of());
            
            // TODO: Implementar consultas ao banco quando estiver funcionando
            // List<OfertaDisciplina> ofertasDisponiveis = ofertaDisciplinaRepository.findBySemestre("2024.1");
            // List<Matricula> matriculasAtivas = matriculaRepository.findByAlunoAndOferta_Semestre(aluno, "2024.1");
            
        } catch (Exception e) {
            System.err.println("Erro no dashboard do aluno: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("ofertasDisponiveis", List.of());
            model.addAttribute("matriculasAtivas", List.of());
            model.addAttribute("erro", "Erro ao carregar dados: " + e.getMessage());
        }
        
        return "aluno_dashboard";
    }

    // --- Funcionalidades específicas do aluno ---
    @GetMapping("/ui/aluno/matricular")
    public String alunoMatricularPage(Model model, HttpSession session) {
        Object usuarioLogado = session.getAttribute("usuarioLogado");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        if (usuarioLogado == null || !"aluno".equals(tipoUsuario) || !(usuarioLogado instanceof Aluno)) {
            return "redirect:/ui/login";
        }
        
        Aluno aluno = (Aluno) usuarioLogado;
        model.addAttribute("aluno", aluno);
        
        try {
            // Por enquanto, usar lista vazia para evitar erros
            model.addAttribute("ofertasDisponiveis", List.of());
            
            // TODO: Implementar consulta quando banco estiver funcionando
            // List<OfertaDisciplina> ofertasDisponiveis = ofertaDisciplinaRepository.findBySemestre("2024.1");
            
        } catch (Exception e) {
            model.addAttribute("ofertasDisponiveis", List.of());
            model.addAttribute("erro", "Erro ao carregar ofertas: " + e.getMessage());
        }
        
        return "aluno_matricular";
    }

    @PostMapping("/ui/aluno/matricular")
    public String alunoMatricular(Model model,
                                @RequestParam Long ofertaId,
                                @RequestParam Matricula.Tipo tipo,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        Object usuarioLogado = session.getAttribute("usuarioLogado");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        if (usuarioLogado == null || !"aluno".equals(tipoUsuario) || !(usuarioLogado instanceof Aluno)) {
            return "redirect:/ui/login";
        }
        
        Aluno aluno = (Aluno) usuarioLogado;
        
        try {
            var result = matriculaService.matricular(aluno.getId(), ofertaId, tipo);
            redirectAttributes.addFlashAttribute("mensagem", "Matrícula registrada com sucesso! ID: " + result.getId());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao matricular: " + e.getMessage());
        }
        
        return "redirect:/ui/aluno/matricular";
    }

    @PostMapping("/ui/aluno/cancelar-matricula")
    public String alunoCancelarMatricula(@RequestParam Long matriculaId,
                                       HttpSession session,
                                       RedirectAttributes redirectAttributes) {
        Object usuarioLogado = session.getAttribute("usuarioLogado");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        if (usuarioLogado == null || !"aluno".equals(tipoUsuario) || !(usuarioLogado instanceof Aluno)) {
            return "redirect:/ui/login";
        }
        
        try {
            matriculaService.cancelarMatricula(matriculaId);
            redirectAttributes.addFlashAttribute("mensagem", "Matrícula cancelada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao cancelar matrícula: " + e.getMessage());
        }
        
        return "redirect:/ui/aluno/dashboard";
    }

    @GetMapping("/ui/aluno/minhas-matriculas")
    public String alunoMinhasMatriculas(Model model, HttpSession session) {
        Object usuarioLogado = session.getAttribute("usuarioLogado");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        if (usuarioLogado == null || !"aluno".equals(tipoUsuario) || !(usuarioLogado instanceof Aluno)) {
            return "redirect:/ui/login";
        }
        
        Aluno aluno = (Aluno) usuarioLogado;
        model.addAttribute("aluno", aluno);
        
        try {
            // Por enquanto, usar lista vazia para evitar erros
            model.addAttribute("matriculas", List.of());
            
            // TODO: Implementar consulta quando banco estiver funcionando
            // List<Matricula> todasMatriculas = matriculaRepository.findByAlunoAndOferta_Semestre(aluno, "2024.1");
            
        } catch (Exception e) {
            model.addAttribute("matriculas", List.of());
            model.addAttribute("erro", "Erro ao carregar matrículas: " + e.getMessage());
        }
        
        return "aluno_minhas_matriculas";
    }

    // --- Dashboard do Professor ---
    @GetMapping("/ui/professor/dashboard")
    public String professorDashboard(Model model, HttpSession session) {
        Object usuarioLogado = session.getAttribute("usuarioLogado");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        if (usuarioLogado == null || !"professor".equals(tipoUsuario)) {
            return "redirect:/ui/login";
        }
        
        return "professor_dashboard";
    }

    @GetMapping("/ui/professor/ofertas")
    public String professorOfertas(Model model, HttpSession session) {
        Object usuarioLogado = session.getAttribute("usuarioLogado");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        if (usuarioLogado == null || !"professor".equals(tipoUsuario)) {
            return "redirect:/ui/login";
        }
        
        Professor professor = (Professor) usuarioLogado;
        model.addAttribute("professor", professor);
        
        try {
            List<Disciplina> disciplinas = disciplinaRepository.findByProfessor(professor);
            List<OfertaDisciplina> ofertas = ofertaDisciplinaRepository.findByDisciplina_Professor(professor);
            
            model.addAttribute("disciplinas", disciplinas);
            model.addAttribute("ofertas", ofertas);
        } catch (Exception e) {
            model.addAttribute("disciplinas", List.of());
            model.addAttribute("ofertas", List.of());
            model.addAttribute("erro", "Erro ao carregar dados: " + e.getMessage());
        }
        
        return "professor_ofertas";
    }

    // --- Funcionalidades específicas do Professor ---
    @GetMapping("/ui/professor/disciplinas")
    public String professorDisciplinas(Model model, HttpSession session) {
        Object usuarioLogado = session.getAttribute("usuarioLogado");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        if (usuarioLogado == null || !"professor".equals(tipoUsuario)) {
            return "redirect:/ui/login";
        }
        
        Professor professor = (Professor) usuarioLogado;
        model.addAttribute("professor", professor);
        
        try {
            List<Disciplina> disciplinas = disciplinaRepository.findByProfessor(professor);
            model.addAttribute("disciplinas", disciplinas);
        } catch (Exception e) {
            model.addAttribute("disciplinas", List.of());
            model.addAttribute("erro", "Erro ao carregar disciplinas: " + e.getMessage());
        }
        
        return "professor_disciplinas";
    }

    @GetMapping("/ui/professor/alunos-matriculados")
    public String professorAlunosMatriculados(Model model, HttpSession session) {
        Object usuarioLogado = session.getAttribute("usuarioLogado");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        if (usuarioLogado == null || !"professor".equals(tipoUsuario)) {
            return "redirect:/ui/login";
        }
        
        Professor professor = (Professor) usuarioLogado;
        model.addAttribute("professor", professor);
        
        try {
            // Buscar todas as disciplinas do professor
            List<Disciplina> disciplinas = disciplinaRepository.findByProfessor(professor);
            List<OfertaDisciplina> ofertas = ofertaDisciplinaRepository.findByDisciplina_Professor(professor);
            
            // Buscar matrículas para cada oferta
            List<Matricula> todasMatriculas = new ArrayList<>();
            for (OfertaDisciplina oferta : ofertas) {
                List<Matricula> matriculas = matriculaRepository.findByOferta(oferta);
                todasMatriculas.addAll(matriculas);
            }
            
            model.addAttribute("disciplinas", disciplinas);
            model.addAttribute("ofertas", ofertas);
            model.addAttribute("matriculas", todasMatriculas);
            
        } catch (Exception e) {
            model.addAttribute("disciplinas", List.of());
            model.addAttribute("ofertas", List.of());
            model.addAttribute("matriculas", List.of());
            model.addAttribute("erro", "Erro ao carregar dados: " + e.getMessage());
        }
        
        return "professor_alunos_matriculados";
    }

    @PostMapping("/ui/professor/criar-oferta")
    public String professorCriarOferta(Model model,
                                      @RequestParam Long disciplinaId,
                                      @RequestParam String semestre,
                                      @RequestParam(required = false) Integer capacidadeMaxima,
                                      HttpSession session,
                                      RedirectAttributes redirectAttributes) {
        Object usuarioLogado = session.getAttribute("usuarioLogado");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        if (usuarioLogado == null || !"professor".equals(tipoUsuario)) {
            return "redirect:/ui/login";
        }
        
        Professor professor = (Professor) usuarioLogado;
        
        try {
            // Verificar se a disciplina pertence ao professor
            Disciplina disciplina = disciplinaRepository.findById(disciplinaId).orElse(null);
            if (disciplina == null || !disciplina.getProfessor().equals(professor)) {
                redirectAttributes.addFlashAttribute("erro", "Disciplina não encontrada ou não pertence a você");
                return "redirect:/ui/professor/ofertas";
            }
            
            OfertaDisciplina oferta = disciplinaService.criarOferta(disciplinaId, semestre, capacidadeMaxima);
            if (oferta != null) {
                redirectAttributes.addFlashAttribute("mensagem", "Oferta criada com sucesso!");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Erro ao criar oferta");
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao criar oferta: " + e.getMessage());
        }
        
        return "redirect:/ui/professor/ofertas";
    }

    @PostMapping("/ui/professor/alterar-status-oferta")
    public String professorAlterarStatusOferta(@RequestParam Long ofertaId,
                                             @RequestParam String novoStatus,
                                             HttpSession session,
                                             RedirectAttributes redirectAttributes) {
        Object usuarioLogado = session.getAttribute("usuarioLogado");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        if (usuarioLogado == null || !"professor".equals(tipoUsuario)) {
            return "redirect:/ui/login";
        }
        
        Professor professor = (Professor) usuarioLogado;
        
        try {
            OfertaDisciplina oferta = ofertaDisciplinaRepository.findById(ofertaId).orElse(null);
            if (oferta == null || !oferta.getDisciplina().getProfessor().equals(professor)) {
                redirectAttributes.addFlashAttribute("erro", "Oferta não encontrada ou não pertence a você");
                return "redirect:/ui/professor/ofertas";
            }
            
            // Alterar status da oferta
            oferta.setStatus(OfertaDisciplina.StatusOferta.valueOf(novoStatus));
            ofertaDisciplinaRepository.save(oferta);
            
            redirectAttributes.addFlashAttribute("mensagem", "Status da oferta alterado com sucesso!");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao alterar status: " + e.getMessage());
        }
        
        return "redirect:/ui/professor/ofertas";
    }

    // --- Dashboard da Secretaria ---
    @GetMapping("/ui/secretaria/dashboard")
    public String secretariaDashboard(Model model, HttpSession session) {
        Object usuarioLogado = session.getAttribute("usuarioLogado");
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        
        if (usuarioLogado == null || !"secretaria".equals(tipoUsuario)) {
            return "redirect:/ui/login";
        }
        
        return "secretaria_dashboard";
    }

    @PostMapping("/ui/curso/novo")
    public String salvarCurso(Model model,
                              @RequestParam String nome,
                              @RequestParam int totalCreditos) {
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

    @PostMapping("/ui/aluno/novo")
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

    @PostMapping("/ui/professor/novo")
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

    @PostMapping("/ui/disciplina/nova")
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

    @PostMapping("/ui/periodo/novo")
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

    @PostMapping("/ui/oferta/nova")
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

    @PostMapping("/ui/matricula/registrar")
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

    @PostMapping("/ui/matricula/cancelar")
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


