package com.techlab.excepciones;

// Excepcion lanzada cuando se intenta acceder o modificar una entidad inexistente en el sistema.

public class EntidadNoEncontradaException extends TechLabException {

    public EntidadNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
