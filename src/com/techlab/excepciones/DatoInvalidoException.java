package com.techlab.excepciones;


// Excepcion lanzada cuando un dato de entrada no cumple con las reglas de negocio, como precios negativos, cantidades nulas o textos vacios.

public class DatoInvalidoException extends TechLabException {

    public DatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
