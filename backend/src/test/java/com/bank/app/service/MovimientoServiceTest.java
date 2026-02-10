package com.bank.app.service;

import com.bank.app.exception.SaldoNoDisponibleException;
import com.bank.app.model.Cliente;
import com.bank.app.model.Cuenta;
import com.bank.app.model.Movimiento;
import com.bank.app.repository.CuentaRepository;
import com.bank.app.repository.MovimientoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MovimientoServiceTest {

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private MovimientoService movimientoService;

    private Cuenta cuenta;

    @BeforeEach
    public void setUp() {
        cuenta = new Cuenta();
        cuenta.setId(1L);
        cuenta.setNumeroCuenta("12345");
        cuenta.setSaldoInicial(100.0);
        cuenta.setTipoCuenta("Ahorros");
        cuenta.setEstado(true);
    }

    @Test
    public void testRegistrarMovimientoCredito() {
        Movimiento movimiento = new Movimiento();
        movimiento.setValor(50.0);
        movimiento.setCuenta(cuenta);

        Mockito.when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));
        Mockito.when(movimientoRepository.save(any(Movimiento.class))).thenAnswer(i -> i.getArguments()[0]);

        Movimiento result = movimientoService.registrarMovimiento(movimiento);

        Assertions.assertEquals(150.0, result.getSaldo());
        Assertions.assertEquals(150.0, cuenta.getSaldoInicial());
    }

    @Test
    public void testRegistrarMovimientoDebitoConSaldo() {
        Movimiento movimiento = new Movimiento();
        movimiento.setValor(-50.0);
        movimiento.setCuenta(cuenta);

        Mockito.when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));
        Mockito.when(movimientoRepository.save(any(Movimiento.class))).thenAnswer(i -> i.getArguments()[0]);

        Movimiento result = movimientoService.registrarMovimiento(movimiento);

        Assertions.assertEquals(50.0, result.getSaldo());
    }

    @Test
    public void testRegistrarMovimientoDebitoSinSaldo() {
        Movimiento movimiento = new Movimiento();
        movimiento.setValor(-150.0);
        movimiento.setCuenta(cuenta);

        Mockito.when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));

        Assertions.assertThrows(SaldoNoDisponibleException.class, () -> {
            movimientoService.registrarMovimiento(movimiento);
        });
    }
}
