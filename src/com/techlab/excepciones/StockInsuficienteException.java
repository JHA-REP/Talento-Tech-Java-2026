package com.techlab.excepciones;

// Excepcion lanzada cuando una cantidad requerida supera la existencia disponible en inventario.
 
public class StockInsuficienteException extends TechLabException {

    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
