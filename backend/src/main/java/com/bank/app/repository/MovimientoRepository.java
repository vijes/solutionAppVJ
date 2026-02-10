package com.bank.app.repository;

import com.bank.app.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    @Query("SELECT m FROM Movimiento m WHERE m.cuenta.cliente.id = :clienteId AND m.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Movimiento> findByClienteIdAndFechaBetween(@Param("clienteId") Long clienteId, @Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
}
