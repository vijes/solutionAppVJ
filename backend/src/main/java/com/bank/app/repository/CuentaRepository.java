package com.bank.app.repository;

import com.bank.app.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    List<Cuenta> findByClienteId(Long clienteId);
}
