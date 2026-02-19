package com.bank.app.repository;

import com.bank.app.model.Cuenta;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    List<Cuenta> findByClienteId(Long clienteId);

    @Transactional
    @Modifying
    @Query("UPDATE Cuenta c SET c.saldoInicial = c.saldoInicial + :valor WHERE c.id = :id AND (c.saldoInicial + :valor) >= 0")
    int actualizarSaldoAtomatico(@Param("id") Long id, @Param("valor") Double valor);
}
