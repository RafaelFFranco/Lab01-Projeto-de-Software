package com.projetosoftware.Sistema.de.Matriculas.Repository;

import com.projetosoftware.Sistema.de.Matriculas.Model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}