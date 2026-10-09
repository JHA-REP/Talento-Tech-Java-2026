package com.techlab.productos;

import java.util.ArrayList;
import com.techlab.excepciones.DatoInvalidoException;
import com.techlab.excepciones.EntidadNoEncontradaException;
import com.techlab.excepciones.StockInsuficienteException;

// Servicio de negocio para la gestion en memoria de productos. 
// Administra el catalogo mediante colecciones ArrayList y garantiza identificadores unicos y validaciones de integridad.

public class ServicioProductos {

    private ArrayList<Producto> listaProductos;
    private int secuenciaIdentificador;

    public ServicioProductos() {
        this.listaProductos = new ArrayList<>();
        this.secuenciaIdentificador = 1;
    }

    public Producto agregarProducto(String nombre, double precio, int stock) throws DatoInvalidoException {
        int identificadorNuevo = this.secuenciaIdentificador;
        Producto nuevoProducto = new Producto(identificadorNuevo, nombre, precio, stock);
        this.listaProductos.add(nuevoProducto);
        this.secuenciaIdentificador++;
        return nuevoProducto;
    }

    public ArrayList<Producto> obtenerTodos() {
        return new ArrayList<>(this.listaProductos);
    }

    public Producto buscarPorIdentificador(int identificador) throws EntidadNoEncontradaException {
        for (Producto producto : this.listaProductos) {
            if (producto.getIdentificador() == identificador) {
                return producto;
            }
        }
        throw new EntidadNoEncontradaException("No se encontro ningun producto con el ID: " + identificador);
    }

    public ArrayList<Producto> buscarPorNombre(String termino) throws DatoInvalidoException {
        if (termino == null || termino.trim().isEmpty()) {
            throw new DatoInvalidoException("El termino de busqueda no puede estar vacio.");
        }
        String terminoNormalizado = termino.trim().toLowerCase();
        ArrayList<Producto> coincidencias = new ArrayList<>();

        for (Producto producto : this.listaProductos) {
            if (producto.getNombre().toLowerCase().contains(terminoNormalizado)) {
                coincidencias.add(producto);
            }
        }
        return coincidencias;
    }

    public void actualizarPrecio(int identificador, double nuevoPrecio) 
            throws EntidadNoEncontradaException, DatoInvalidoException {
        Producto producto = buscarPorIdentificador(identificador);
        producto.setPrecio(nuevoPrecio);
    }

    public void actualizarStock(int identificador, int nuevoStock) 
            throws EntidadNoEncontradaException, DatoInvalidoException {
        Producto producto = buscarPorIdentificador(identificador);
        producto.setStock(nuevoStock);
    }

    public void eliminarProducto(int identificador) throws EntidadNoEncontradaException {
        Producto producto = buscarPorIdentificador(identificador);
        this.listaProductos.remove(producto);
    }

    public void reducirStock(int identificador, int cantidad) 
            throws EntidadNoEncontradaException, StockInsuficienteException, DatoInvalidoException {
        Producto producto = buscarPorIdentificador(identificador);
        producto.reducirStock(cantidad);
    }

    public void aumentarStock(int identificador, int cantidad) 
            throws EntidadNoEncontradaException, DatoInvalidoException {
        Producto producto = buscarPorIdentificador(identificador);
        producto.aumentarStock(cantidad);
    }

    public int cantidadTotalProductos() {
        return this.listaProductos.size();
    }
}
