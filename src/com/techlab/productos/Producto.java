package com.techlab.productos;

import com.techlab.excepciones.DatoInvalidoException;
import com.techlab.excepciones.StockInsuficienteException;

// Entidad que representa un producto del catalogo de TechLab. 
// Posee atributos privados encapsulados con sus validaciones de negocio.

public class Producto {

    private int identificador;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(int identificador, String nombre, double precio, int stock) throws DatoInvalidoException {
        if (identificador <= 0) {
            throw new DatoInvalidoException("El identificador del producto debe ser un numero positivo.");
        }
        validarNombre(nombre);
        validarPrecio(precio);
        validarStock(stock);

        this.identificador = identificador;
        this.nombre = nombre.trim();
        this.precio = precio;
        this.stock = stock;
    }

    private void validarNombre(String nombre) throws DatoInvalidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatoInvalidoException("El nombre del producto no puede estar vacio.");
        }
    }

    private void validarPrecio(double precio) throws DatoInvalidoException {
        if (precio < 0.0) {
            throw new DatoInvalidoException("El precio del producto no puede ser negativo.");
        }
    }

    private void validarStock(int stock) throws DatoInvalidoException {
        if (stock < 0) {
            throw new DatoInvalidoException("La cantidad de stock no puede ser negativa.");
        }
    }

    public int getIdentificador() {
        return this.identificador;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) throws DatoInvalidoException {
        validarNombre(nombre);
        this.nombre = nombre.trim();
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) throws DatoInvalidoException {
        validarPrecio(precio);
        this.precio = precio;
    }

    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) throws DatoInvalidoException {
        validarStock(stock);
        this.stock = stock;
    }

    public void reducirStock(int cantidad) throws StockInsuficienteException, DatoInvalidoException {
        if (cantidad <= 0) {
            throw new DatoInvalidoException("La cantidad a descontar debe ser mayor a cero.");
        }
        if (this.stock < cantidad) {
            throw new StockInsuficienteException(
                "Stock insuficiente para el producto '" + this.nombre + 
                "'. Disponible: " + this.stock + ", requerido: " + cantidad + "."
            );
        }
        this.stock -= cantidad;
    }

    public void aumentarStock(int cantidad) throws DatoInvalidoException {
        if (cantidad <= 0) {
            throw new DatoInvalidoException("La cantidad a incrementar debe ser mayor a cero.");
        }
        this.stock += cantidad;
    }

    public String obtenerDetalle() {
        return String.format("ID: %-4d | Nombre: %-25s | Precio: $%10.2f | Stock: %-5d",
                this.identificador, this.nombre, this.precio, this.stock);
    }

    @Override
    public String toString() {
        return obtenerDetalle();
    }
}
