package com.projetosoftware.Sistema.de.Matriculas.Service;

import com.projetosoftware.Sistema.de.Matriculas.Model.*;
import com.projetosoftware.Sistema.de.Matriculas.Repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final OfertaDisciplinaRepository ofertaDisciplinaRepository;
    private final PeriodoMatriculasRepository periodoMatriculasRepository;
    private final AlunoRepository alunoRepository;
    private final CobrancaService cobrancaService;

    public MatriculaService(MatriculaRepository matriculaRepository,
                            OfertaDisciplinaRepository ofertaDisciplinaRepository,
                            PeriodoMatriculasRepository periodoMatriculasRepository,
                            AlunoRepository alunoRepository,
                            CobrancaService cobrancaService) {
        this.matriculaRepository = matriculaRepository;
        this.ofertaDisciplinaRepository = ofertaDisciplinaRepository;
        this.periodoMatriculasRepository = periodoMatriculasRepository;
        this.alunoRepository = alunoRepository;
        this.cobrancaService = cobrancaService;
    }

    private void validarPeriodoAberto(String semestre) {
        PeriodoMatriculas periodo = periodoMatriculasRepository.findBySemestre(semestre)
                .orElseThrow(() -> new IllegalStateException("Período de matrículas não encontrado para o semestre."));
        if (!periodo.estaAberto()) {
            throw new IllegalStateException("Período de matrículas encerrado.");
        }
    }

    @Transactional
    public Matricula matricular(Long alunoId, Long ofertaId, Matricula.Tipo tipo) {
        OfertaDisciplina oferta = ofertaDisciplinaRepository.findById(ofertaId)
                .orElseThrow(() -> new IllegalArgumentException("Oferta não encontrada"));
        validarPeriodoAberto(oferta.getSemestre());

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        long numAtivasNaOferta = matriculaRepository.countByOfertaAndStatus(oferta, Matricula.Status.ATIVA);
        if (numAtivasNaOferta >= oferta.getCapacidadeMaxima()) {
            throw new IllegalStateException("Capacidade máxima atingida para a oferta.");
        }

        long jaMatriculadasTipo = matriculaRepository.countByAlunoAndOferta_SemestreAndTipoAndStatus(
                aluno, oferta.getSemestre(), tipo, Matricula.Status.ATIVA);
        if (tipo == Matricula.Tipo.OBRIGATORIA && jaMatriculadasTipo >= 4) {
            throw new IllegalStateException("Limite de 4 disciplinas obrigatórias por semestre atingido.");
        }
        if (tipo == Matricula.Tipo.OPTATIVA && jaMatriculadasTipo >= 2) {
            throw new IllegalStateException("Limite de 2 disciplinas optativas por semestre atingido.");
        }

        Matricula nova = new Matricula();
        nova.setAluno(aluno);
        nova.setOferta(oferta);
        nova.setTipo(tipo);
        nova.setStatus(Matricula.Status.ATIVA);
        Matricula criada = matriculaRepository.save(nova);

        // Se esta é a primeira matrícula ativa do semestre para o aluno, notificar cobrança
        List<Matricula> doSemestre = matriculaRepository.findByAlunoAndOferta_Semestre(aluno, oferta.getSemestre());
        long ativas = doSemestre.stream().filter(m -> m.getStatus() == Matricula.Status.ATIVA).count();
        if (ativas == 1) {
            cobrancaService.notificarCobranca(oferta.getSemestre(), aluno, doSemestre.stream()
                    .filter(m -> m.getStatus() == Matricula.Status.ATIVA)
                    .toList());
        }

        return criada;
    }

    @Transactional
    public void cancelarMatricula(Long matriculaId) {
        Matricula matricula = matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada"));
        validarPeriodoAberto(matricula.getOferta().getSemestre());
        matricula.setStatus(Matricula.Status.CANCELADA);
        matriculaRepository.save(matricula);
    }

    public List<Matricula> listarPorOferta(Long ofertaId) {
        OfertaDisciplina oferta = ofertaDisciplinaRepository.findById(ofertaId)
                .orElseThrow(() -> new IllegalArgumentException("Oferta não encontrada"));
        return matriculaRepository.findByOferta(oferta);
    }

    @Transactional
    public void finalizarPeriodo(String semestre) {
        PeriodoMatriculas periodo = periodoMatriculasRepository.findBySemestre(semestre)
                .orElseThrow(() -> new IllegalArgumentException("Período não encontrado"));
        // encerra o período
        periodo.setFim(java.time.LocalDateTime.now());
        periodoMatriculasRepository.save(periodo);

        // Atualiza status das ofertas conforme regras: >=3 ativa; senão cancelada
        List<OfertaDisciplina> ofertas = ofertaDisciplinaRepository.findBySemestre(semestre);
        for (OfertaDisciplina oferta : ofertas) {
            long ativos = matriculaRepository.countByOfertaAndStatus(oferta, Matricula.Status.ATIVA);
            oferta.setStatus(ativos >= 3 ? OfertaDisciplina.StatusOferta.ATIVA : OfertaDisciplina.StatusOferta.CANCELADA);
            ofertaDisciplinaRepository.save(oferta);
        }
    }
} 