package com.projetosoftware.Sistema.de.Matriculas.Repository;

import com.projetosoftware.Sistema.de.Matriculas.Model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
	Optional<Aluno> findByEmail(String email);
}
