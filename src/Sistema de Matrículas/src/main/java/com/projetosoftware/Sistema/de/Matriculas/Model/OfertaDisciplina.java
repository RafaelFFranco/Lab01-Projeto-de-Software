package com.projetosoftware.Sistema.de.Matriculas.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_oferta_disciplina")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OfertaDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    private String semestre;

    private int capacidadeMaxima = 60;

    @Enumerated(EnumType.STRING)
    private StatusOferta status = StatusOferta.PENDENTE;

    public enum StatusOferta {
        PENDENTE, ATIVA, CANCELADA
    }
} 