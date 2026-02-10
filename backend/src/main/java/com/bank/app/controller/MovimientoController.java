package com.bank.app.controller;

import com.bank.app.model.Movimiento;
import com.bank.app.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public List<Movimiento> getAllMovimientos() {
        return movimientoService.obtenerMovimientos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> getMovimientoById(@PathVariable Long id) {
        Movimiento movimiento = movimientoService.obtenerMovimientoPorId(id);
        if (movimiento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movimiento);
    }

    @PostMapping
    public Movimiento createMovimiento(@RequestBody Movimiento movimiento) {
        return movimientoService.registrarMovimiento(movimiento);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimiento(@PathVariable Long id) {
        movimientoService.eliminarMovimiento(id);
        return ResponseEntity.ok().build();
    }
}
