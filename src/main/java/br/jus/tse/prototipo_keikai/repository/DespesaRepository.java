package br.jus.tse.prototipo_keikai.repository;

import br.jus.tse.prototipo_keikai.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    // Métodos padrão como save() já estão inclusos aqui.
}
