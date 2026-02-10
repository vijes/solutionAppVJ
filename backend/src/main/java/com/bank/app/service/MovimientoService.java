package com.bank.app.service;

import com.bank.app.exception.SaldoNoDisponibleException;
import com.bank.app.model.Cuenta;
import com.bank.app.model.Movimiento;
import com.bank.app.repository.CuentaRepository;
import com.bank.app.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Transactional
    public Movimiento registrarMovimiento(Movimiento movimiento) {
        Cuenta cuenta = cuentaRepository.findById(movimiento.getCuenta().getId())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        double nuevoSaldo = cuenta.getSaldoInicial() + movimiento.getValor();

        if (nuevoSaldo < 0) {
            throw new SaldoNoDisponibleException("Saldo no disponible");
        }

        // Update account balance
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        // Set movement details
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setCuenta(cuenta);

        return movimientoRepository.save(movimiento);
    }

    public List<Movimiento> obtenerMovimientos() {
        return movimientoRepository.findAll();
    }
    
    public void eliminarMovimiento(Long id) {
        movimientoRepository.deleteById(id);
    }
    
     public Movimiento obtenerMovimientoPorId(Long id) {
        return movimientoRepository.findById(id).orElse(null);
    }
}
