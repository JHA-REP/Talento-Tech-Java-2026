package com.techlab.pedidos;

import java.util.ArrayList;
import com.techlab.excepciones.DatoInvalidoException;

// Representa un pedido de compra realizado por el cliente.
// Posee un identificador unico y una coleccion de lineas de pedido.

public class Pedido {

    private int identificador;
    private ArrayList<LineaPedido> listaLineas;

    public Pedido(int identificador) throws DatoInvalidoException {
        if (identificador <= 0) {
            throw new DatoInvalidoException("El identificador del pedido debe ser un numero positivo.");
        }
        this.identificador = identificador;
        this.listaLineas = new ArrayList<>();
    }

    public int getIdentificador() {
        return this.identificador;
    }

    public ArrayList<LineaPedido> getListaLineas() {
        return new ArrayList<>(this.listaLineas);
    }

    public void agregarLinea(LineaPedido linea) throws DatoInvalidoException {
        if (linea == null) {
            throw new DatoInvalidoException("La linea a agregar no puede ser nula.");
        }
        this.listaLineas.add(linea);
    }

    public double calcularTotal() {
        double acumuladorTotal = 0.0;
        for (LineaPedido linea : this.listaLineas) {
            acumuladorTotal += linea.calcularSubtotal();
        }
        return acumuladorTotal;
    }

    public int cantidadLineas() {
        return this.listaLineas.size();
    }

    public String obtenerDetalle() {
        StringBuilder detalle = new StringBuilder();
        detalle.append("====================================================================\n");
        detalle.append(String.format("PEDIDO #%d\n", this.identificador));
        detalle.append("--------------------------------------------------------------------\n");
        detalle.append("Articulos solicitados:\n");
        for (LineaPedido linea : this.listaLineas) {
            detalle.append(linea.obtenerDetalle()).append("\n");
        }
        detalle.append("--------------------------------------------------------------------\n");
        detalle.append(String.format("TOTAL DEL PEDIDO: $%10.2f\n", calcularTotal()));
        detalle.append("====================================================================");
        return detalle.toString();
    }

    @Override
    public String toString() {
        return obtenerDetalle();
    }
}
