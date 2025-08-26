package com.projetosoftware.Sistema.de.Matriculas.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_periodo_matriculas")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PeriodoMatriculas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Formato sugerido: "2025-1", "2025-2"
    private String semestre;

    private LocalDateTime inicio;
    private LocalDateTime fim;

    public boolean estaAberto() {
        LocalDateTime agora = LocalDateTime.now();
        return (inicio == null || !agora.isBefore(inicio)) && (fim == null || !agora.isAfter(fim));
    }
} 