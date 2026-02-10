package com.bank.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "clienteId")
public class Cliente extends Persona {

    private String contrasena;
    private Boolean estado;
    
    // Although Persona has an ID, requirement says "clienteid" and unique key.
    // JPA MappedSuperclass strategy with PrimaryKeyJoinColumn shares the ID, but customizes the column name in this table if needed.
    // Or we can use @Inheritance(strategy = InheritanceType.JOINED) on Persona if we want separate tables joined.
    // Given "Persona" has PK, and Client inherits.
    // Let's assume Single Table or Joined. MappedSuperclass is simplest if Persona is not an entity itself queryable alone.
    // But "Cliente debe manejar una entidad, que herede de la clase persona".
    
    // If MappedSuperclass is used, Persona table doesn't exist. Columns are in Cliente.
    // Requirements say "Implementar la clase persona... Debe manejar su clave primaria".
    // "Cliente... herede... clienteid, contraseña, estado... clave única".
    
    // I will use MappedSuperclass for simplicity unless Persona needs to be an Entity.
    // Wait, "Persona... Debe manejar su clave primaria".
    // If I use MappedSuperclass, the ID is in the child.
    
}
