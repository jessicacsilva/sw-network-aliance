package com.letscode.starwars.rsn.repository;

import com.letscode.starwars.rsn.domain.dto.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecursoRepository extends JpaRepository<Recurso,Long> {
}
