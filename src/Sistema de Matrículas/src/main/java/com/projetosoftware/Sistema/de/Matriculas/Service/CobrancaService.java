package com.projetosoftware.Sistema.de.Matriculas.Service;

import com.projetosoftware.Sistema.de.Matriculas.Model.Aluno;
import com.projetosoftware.Sistema.de.Matriculas.Model.Matricula;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CobrancaService {

    public void notificarCobranca(String semestre, Aluno aluno, List<Matricula> matriculasAtivasDoSemestre) {
        // Stub: aqui poderíamos chamar um serviço externo (REST) de cobrança
        // Para protótipo, apenas simula o envio registrando no log
        System.out.println("[COBRANCA] Semestre: " + semestre + ", Aluno: " + aluno.getId() + " - " + aluno.getNome()
                + ", Itens: " + matriculasAtivasDoSemestre.size());
    }
} 