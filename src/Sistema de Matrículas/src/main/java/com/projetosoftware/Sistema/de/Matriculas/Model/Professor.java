package com.projetosoftware.Sistema.de.Matriculas.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
//import model.Disciplina;

@Entity
@Table(name = "tb_professores")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String nome;
    private String email;
    private String departamento;


    // @OneToMany(mappedBy = "professor") 
     //private List<Disciplina> disciplinas;

//remover esses comentarios quando for incluido a classe Disciplina
}
