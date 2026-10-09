package com.techlab.excepciones;

// Excepcion base para errores del dominio de TechLab
 
public abstract class TechLabException extends Exception {

    public TechLabException(String mensaje) {
        super(mensaje);
    }

    public TechLabException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
