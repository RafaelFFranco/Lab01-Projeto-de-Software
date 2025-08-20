package com.projetosoftware.Sistema.de.Matriculas.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import model.Disciplina;

@Entity
@Table(name = "tb_disciplina")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String nome;
    private int numCreditos;
    private Professor professor;
    
    @OneToMany(mappedBy = "disciplina") 
    private List<Aluno> alunos;
}