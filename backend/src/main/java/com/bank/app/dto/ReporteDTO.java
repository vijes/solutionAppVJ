package com.bank.app.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReporteDTO {
    private LocalDateTime fecha;
    private String cliente;
    private String numeroCuenta;
    private String tipo;
    private Double saldoInicial;
    private Boolean estado;
    private Double movimiento;
    private Double saldoDisponible;
}
