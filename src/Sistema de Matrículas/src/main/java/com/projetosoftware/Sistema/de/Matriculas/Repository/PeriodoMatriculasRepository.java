package com.projetosoftware.Sistema.de.Matriculas.Repository;

import com.projetosoftware.Sistema.de.Matriculas.Model.PeriodoMatriculas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeriodoMatriculasRepository extends JpaRepository<PeriodoMatriculas, Long> {
	Optional<PeriodoMatriculas> findBySemestre(String semestre);
} 