package com.techlab.pedidos;

import com.techlab.excepciones.DatoInvalidoException;
import com.techlab.productos.Producto;

// Representa un item individual dentro de un pedido de compra. Contiene la referencia al producto, la cantidad solicitada y el precio unitario congelado.

public class LineaPedido {

    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public LineaPedido(Producto producto, int cantidad) throws DatoInvalidoException {
        if (producto == null) {
            throw new DatoInvalidoException("El producto de la linea de pedido no puede ser nulo.");
        }
        if (cantidad <= 0) {
            throw new DatoInvalidoException("La cantidad solicitada debe ser mayor a cero.");
        }
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
    }

    public Producto getProducto() {
        return this.producto;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) throws DatoInvalidoException {
        if (cantidad <= 0) {
            throw new DatoInvalidoException("La cantidad solicitada debe ser mayor a cero.");
        }
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return this.precioUnitario;
    }

    public double calcularSubtotal() {
        return this.precioUnitario * this.cantidad;
    }

    public String obtenerDetalle() {
        return String.format("  - %-25s | Cantidad: %-4d | Precio Unitario: $%10.2f | Subtotal: $%10.2f",
                this.producto.getNombre(), this.cantidad, this.precioUnitario, calcularSubtotal());
    }

    @Override
    public String toString() {
        return obtenerDetalle();
    }
}
