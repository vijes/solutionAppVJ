package com.bank.app.service;

import com.bank.app.dto.ReporteDTO;
import com.bank.app.model.Movimiento;
import com.bank.app.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    public List<ReporteDTO> generarReporte(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Movimiento> movimientos = movimientoRepository.findByClienteIdAndFechaBetween(clienteId, fechaInicio, fechaFin);
        
        return movimientos.stream().map(m -> {
            ReporteDTO dto = new ReporteDTO();
            dto.setFecha(m.getFecha());
            dto.setCliente(m.getCuenta().getCliente().getNombre());
            dto.setNumeroCuenta(m.getCuenta().getNumeroCuenta());
            dto.setTipo(m.getCuenta().getTipoCuenta());
            // Logic to determine initial balance of THIS transaction? 
            // The requirement says "Saldo Inicial" in the JSON example.
            // But if we just list movements, the saldo of the movement is the balance AFTER logic.
            // So Saldo Inicial = Saldo - Valor.
            dto.setSaldoInicial(m.getSaldo() - m.getValor());
            dto.setEstado(m.getCuenta().getEstado());
            dto.setMovimiento(m.getValor());
            dto.setSaldoDisponible(m.getSaldo());
            return dto;
        }).collect(Collectors.toList());
    }
}
