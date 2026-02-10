package com.bank.app.service;

import com.bank.app.model.Cliente;
import com.bank.app.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> obtenerClientes() {
        return clienteRepository.findAll();
    }

    public Cliente obtenerClientePorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
    
    public Cliente actualizarCliente(Long id, Cliente clienteDetails) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente != null) {
            cliente.setNombre(clienteDetails.getNombre());
            cliente.setGenero(clienteDetails.getGenero());
            cliente.setEdad(clienteDetails.getEdad());
            cliente.setIdentificacion(clienteDetails.getIdentificacion());
            cliente.setDireccion(clienteDetails.getDireccion());
            cliente.setTelefono(clienteDetails.getTelefono());
            cliente.setContrasena(clienteDetails.getContrasena());
            cliente.setEstado(clienteDetails.getEstado());
            return clienteRepository.save(cliente);
        }
        return null;
    }
}
