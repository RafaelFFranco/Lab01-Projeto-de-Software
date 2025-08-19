package com.projetosoftware.Sistema.de.Matriculas.Repository;

import com.projetosoftware.Sistema.de.Matriculas.Model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
