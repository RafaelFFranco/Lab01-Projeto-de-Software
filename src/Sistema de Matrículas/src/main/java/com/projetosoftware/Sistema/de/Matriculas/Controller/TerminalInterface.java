package com.projetosoftware.Sistema.de.Matriculas.Controller;

import com.projetosoftware.Sistema.de.Matriculas.Service.AlunoService;
import com.projetosoftware.Sistema.de.Matriculas.Service.DisciplinaService;
import com.projetosoftware.Sistema.de.Matriculas.Service.ProfessorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class TerminalInterface implements CommandLineRunner {

    private AlunoService alunoService;
    private ProfessorService professorService;
    private DisciplinaService disciplinaService;

    public TerminalInterface(ProfessorService professorService, AlunoService alunoService, DisciplinaService disciplinaService) {
        this.professorService = professorService;
        this.alunoService = alunoService;
        this.disciplinaService = disciplinaService;
    }
    private void menu(){

        System.out.println("=========================================================\n"
                           "|                                                       |\n"
                           "|        SISTEMA DE MATRÍCULAS UNIVERSITÁRIO            |\n"
                           "|                                                       |\n"
                           "=======================================================\n"
                           "                                                      |\n"
                           " Bem-vindo! Por favor, faça o login para continuar.   |\n"
                           "                                                      |\n"
                           " 1. Login                                             |\n"
                           " 2. Sair                                              |\n"
                           "                                                      |\n"
                           "=========================================================");

    }

    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println();


    }
}
