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
        int rowsUpdated = cuentaRepository.actualizarSaldoAtomatico(
                movimiento.getCuenta().getId(), 
                movimiento.getValor()
        );

        if (rowsUpdated == 0) {
            // Check if it's due to insufficient balance or missing account
            Cuenta cuenta = cuentaRepository.findById(movimiento.getCuenta().getId())
                    .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
            
            if (cuenta.getSaldoInicial() + movimiento.getValor() < 0) {
                throw new SaldoNoDisponibleException("Saldo no disponible");
            }
            
            throw new RuntimeException("Error al procesar la transacción");
        }

        // Fetch the updated account to get the new balance for the movement record
        Cuenta cuentaActualizada = cuentaRepository.findById(movimiento.getCuenta().getId()).get();

        // Set movement details
        movimiento.setSaldo(cuentaActualizada.getSaldoInicial());
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setCuenta(cuentaActualizada);

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
