package com.projetosoftware.Sistema.de.Matriculas.Repository;

import com.projetosoftware.Sistema.de.Matriculas.Model.OfertaDisciplina;
import com.projetosoftware.Sistema.de.Matriculas.Model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfertaDisciplinaRepository extends JpaRepository<OfertaDisciplina, Long> {
	List<OfertaDisciplina> findBySemestre(String semestre);
	List<OfertaDisciplina> findByDisciplina(Disciplina disciplina);
} 