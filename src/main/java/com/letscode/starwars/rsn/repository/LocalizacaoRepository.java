package com.letscode.starwars.rsn.repository;

import com.letscode.starwars.rsn.domain.dto.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalizacaoRepository  extends JpaRepository<Localizacao,Long> {
}
