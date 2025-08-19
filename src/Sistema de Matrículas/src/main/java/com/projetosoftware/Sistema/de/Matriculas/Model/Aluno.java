package com.projetosoftware.Sistema.de.Matriculas.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_alunos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Disciplina[4] disciplinasObrigatorias;
    private Disciplina[2] disciplinasoptativas;
}
