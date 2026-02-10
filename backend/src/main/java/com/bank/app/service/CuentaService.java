package com.bank.app.service;

import com.bank.app.model.Cuenta;
import com.bank.app.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    public Cuenta guardarCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public List<Cuenta> obtenerCuentas() {
        return cuentaRepository.findAll();
    }

    public Cuenta obtenerCuentaPorId(Long id) {
        return cuentaRepository.findById(id).orElse(null);
    }

    public void eliminarCuenta(Long id) {
        cuentaRepository.deleteById(id);
    }
    
    public Cuenta actualizarCuenta(Long id, Cuenta cuentaDetails) {
        Cuenta cuenta = cuentaRepository.findById(id).orElse(null);
        if (cuenta != null) {
            cuenta.setNumeroCuenta(cuentaDetails.getNumeroCuenta());
            cuenta.setTipoCuenta(cuentaDetails.getTipoCuenta());
            cuenta.setSaldoInicial(cuentaDetails.getSaldoInicial());
            cuenta.setEstado(cuentaDetails.getEstado());
            return cuentaRepository.save(cuenta);
        }
        return null;
    }
}
