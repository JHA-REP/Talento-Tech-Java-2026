package com.techlab.pedidos;

import java.util.ArrayList;
import com.techlab.excepciones.DatoInvalidoException;
import com.techlab.excepciones.EntidadNoEncontradaException;
import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.productos.Producto;
import com.techlab.productos.ServicioProductos;

// Servicio de negocio para la creacion, validacion y gestion de pedidos.
// Garantiza la consistencia del inventario mediante validacion previa exhaustiva: el stock solo se descuenta si todos los articulos solicitados estan disponibles.

public class ServicioPedidos {

    private ArrayList<Pedido> listaPedidos;
    private int secuenciaIdentificador;
    private ServicioProductos servicioProductos;

    public ServicioPedidos(ServicioProductos servicioProductos) throws DatoInvalidoException {
        if (servicioProductos == null) {
            throw new DatoInvalidoException("El servicio de productos no puede ser nulo.");
        }
        this.listaPedidos = new ArrayList<>();
        this.secuenciaIdentificador = 1;
        this.servicioProductos = servicioProductos;
    }

    public Pedido crearPedido(ArrayList<LineaPedido> lineas) 
            throws DatoInvalidoException, EntidadNoEncontradaException, StockInsuficienteException {
        if (lineas == null || lineas.isEmpty()) {
            throw new DatoInvalidoException("El pedido debe contener al menos un producto.");
        }

        // FASE 1 DE CREACION: Verificacion previa sin modificar inventario
        for (LineaPedido linea : lineas) {
            int identificadorProducto = linea.getProducto().getIdentificador();
            Producto productoCatalogo = this.servicioProductos.buscarPorIdentificador(identificadorProducto);

            int cantidadSolicitada = linea.getCantidad();
            int stockDisponible = productoCatalogo.getStock();

            if (stockDisponible < cantidadSolicitada) {
                throw new StockInsuficienteException(
                    "No es posible completar el pedido. Stock insuficiente para '" + 
                    productoCatalogo.getNombre() + "'. Disponible: " + stockDisponible + 
                    ", solicitado: " + cantidadSolicitada + "."
                );
            }
        }

        // Aplicacion del descuento de stock una vez comprobada la disponibilidad total
        for (LineaPedido linea : lineas) {
            int identificadorProducto = linea.getProducto().getIdentificador();
            int cantidadSolicitada = linea.getCantidad();
            this.servicioProductos.reducirStock(identificadorProducto, cantidadSolicitada);
        }

        // Registro definitivo del pedido en la coleccion
        int identificadorNuevo = this.secuenciaIdentificador;
        Pedido nuevoPedido = new Pedido(identificadorNuevo);
        for (LineaPedido linea : lineas) {
            nuevoPedido.agregarLinea(linea);
        }

        this.listaPedidos.add(nuevoPedido);
        this.secuenciaIdentificador++;
        return nuevoPedido;
    }

    public ArrayList<Pedido> obtenerTodos() {
        return new ArrayList<>(this.listaPedidos);
    }

    public Pedido buscarPorIdentificador(int identificador) throws EntidadNoEncontradaException {
        for (Pedido pedido : this.listaPedidos) {
            if (pedido.getIdentificador() == identificador) {
                return pedido;
            }
        }
        throw new EntidadNoEncontradaException("No se encontro ningun pedido con el ID: " + identificador);
    }

    public int cantidadTotalPedidos() {
        return this.listaPedidos.size();
    }
}
