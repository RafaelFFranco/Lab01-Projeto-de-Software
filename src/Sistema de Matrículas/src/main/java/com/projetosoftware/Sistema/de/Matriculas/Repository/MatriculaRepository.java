package com.projetosoftware.Sistema.de.Matriculas.Repository;

import com.projetosoftware.Sistema.de.Matriculas.Model.Matricula;
import com.projetosoftware.Sistema.de.Matriculas.Model.OfertaDisciplina;
import com.projetosoftware.Sistema.de.Matriculas.Model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
	long countByAlunoAndOferta_SemestreAndTipoAndStatus(Aluno aluno, String semestre, Matricula.Tipo tipo, Matricula.Status status);
	long countByOfertaAndStatus(OfertaDisciplina oferta, Matricula.Status status);
	List<Matricula> findByOferta(OfertaDisciplina oferta);
	List<Matricula> findByAlunoAndOferta_Semestre(Aluno aluno, String semestre);
} 