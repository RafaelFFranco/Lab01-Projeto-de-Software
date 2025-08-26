package com.projetosoftware.Sistema.de.Matriculas.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_matricula")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne(optional = false)
    @JoinColumn(name = "oferta_id")
    private OfertaDisciplina oferta;

    @Enumerated(EnumType.STRING)
    private Tipo tipo; // OBRIGATORIA ou OPTATIVA

    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVA; // ATIVA ou CANCELADA

    private LocalDateTime dataCriacao = LocalDateTime.now();

    public enum Tipo { OBRIGATORIA, OPTATIVA }
    public enum Status { ATIVA, CANCELADA }
} 