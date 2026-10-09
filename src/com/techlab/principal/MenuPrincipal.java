package com.techlab.principal;

import java.util.ArrayList;
import com.techlab.excepciones.DatoInvalidoException;
import com.techlab.excepciones.EntidadNoEncontradaException;
import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.excepciones.TechLabException;
import com.techlab.pedidos.LineaPedido;
import com.techlab.pedidos.Pedido;
import com.techlab.pedidos.ServicioPedidos;
import com.techlab.productos.Producto;
import com.techlab.productos.ServicioProductos;

// Menu principal interactivo de consola para TechLab. 
// Integra todas las funcionalidades de gestion de productos y pedidos.

public class MenuPrincipal {

    private ServicioProductos servicioProductos;
    private ServicioPedidos servicioPedidos;
    private boolean activo;

    public MenuPrincipal() {
        this.servicioProductos = new ServicioProductos();
        try {
            this.servicioPedidos = new ServicioPedidos(this.servicioProductos);
            cargarDatosIniciales();
        } catch (DatoInvalidoException excepcion) {
            System.out.println("[Aviso] No se pudieron precargar datos: " + excepcion.getMessage());
        }
        this.activo = true;
    }

    private void cargarDatosIniciales() {
        try {
            this.servicioProductos.agregarProducto("Notebook Gamer 16GB", 1250000.0, 5);
            this.servicioProductos.agregarProducto("Monitor IPS 27 144Hz", 340000.0, 8);
            this.servicioProductos.agregarProducto("Teclado Mecanico RGB", 78000.0, 15);
            this.servicioProductos.agregarProducto("Mouse Inalambrico Pro", 45000.0, 20);
            this.servicioProductos.agregarProducto("Auriculares HyperBass", 92000.0, 12);
        } catch (DatoInvalidoException excepcion) {
        }
    }

    public void iniciar() {
        System.out.println("====================================================================");
        System.out.println("   BIENVENIDO AL SISTEMA DE GESTION DE PRODUCTOS Y PEDIDOS TECHLAB   ");
        System.out.println("====================================================================");

        while (this.activo) {
            mostrarOpciones();
            int opcion = ConsolaLector.leerEntero("Seleccione una opcion (1-8): ");
            System.out.println("--------------------------------------------------------------------");

            try {
                switch (opcion) {
                    case 1:
                        gestionarRegistroProducto();
                        break;
                    case 2:
                        gestionarListadoProductos();
                        break;
                    case 3:
                        gestionarBusquedaProducto();
                        break;
                    case 4:
                        gestionarActualizacionProducto();
                        break;
                    case 5:
                        gestionarEliminacionProducto();
                        break;
                    case 6:
                        gestionarCreacionPedido();
                        break;
                    case 7:
                        gestionarListadoPedidos();
                        break;
                    case 8:
                        this.activo = false;
                        System.out.println("Gracias por utilizar el sistema TechLab. Hasta luego!");
                        break;
                    default:
                        System.out.println("[Atencion] Opcion no valida. Seleccione un numero entre 1 y 8.");
                        break;
                }
            } catch (TechLabException excepcion) {
                // Manejo de excepcion del dominio TechLab
                System.out.println("[Error de Negocio] " + excepcion.getMessage());
            } catch (Exception excepcion) {
                System.out.println("[Error Inesperado] Ocurrio un fallo en la operacion: " + excepcion.getMessage());
            }

            if (this.activo) {
                System.out.println("--------------------------------------------------------------------");
            }
        }
    }

    private void mostrarOpciones() {
        System.out.println("\n====================== MENU PRINCIPAL TECHLAB ======================");
        System.out.println("1. Registrar nuevo producto");
        System.out.println("2. Listar todos los productos");
        System.out.println("3. Buscar producto (por ID o por nombre)");
        System.out.println("4. Actualizar precio o stock de producto");
        System.out.println("5. Eliminar producto");
        System.out.println("6. Crear y confirmar nuevo pedido");
        System.out.println("7. Listar pedidos realizados");
        System.out.println("8. Salir del sistema");
        System.out.println("====================================================================");
    }

    private void gestionarRegistroProducto() throws DatoInvalidoException {
        System.out.println(">>> REGISTRO DE PRODUCTO <<<");
        String nombre = ConsolaLector.leerTexto("Nombre del producto: ");
        double precio = ConsolaLector.leerDecimalNoNegativo("Precio unitario: $");
        int stock = ConsolaLector.leerEnteroNoNegativo("Stock inicial: ");

        Producto producto = this.servicioProductos.agregarProducto(nombre, precio, stock);
        System.out.println("[Exito] Producto registrado correctamente con ID #" + producto.getIdentificador());
    }

    private void gestionarListadoProductos() {
        System.out.println(">>> CATALOGO DE PRODUCTOS <<<");
        ArrayList<Producto> listaProductos = this.servicioProductos.obtenerTodos();
        if (listaProductos.isEmpty()) {
            System.out.println("El catalogo se encuentra vacio.");
            return;
        }
        for (Producto producto : listaProductos) {
            System.out.println(producto.obtenerDetalle());
        }
        System.out.println("Total de productos en catalogo: " + listaProductos.size());
    }

    private void gestionarBusquedaProducto() throws EntidadNoEncontradaException, DatoInvalidoException {
        System.out.println(">>> BUSQUEDA DE PRODUCTOS <<<");
        System.out.println("1. Buscar por identificador (ID)");
        System.out.println("2. Buscar por nombre (coincidencia parcial)");
        int criterio = ConsolaLector.leerEntero("Seleccione el criterio (1-2): ");

        if (criterio == 1) {
            int identificador = ConsolaLector.leerEnteroPositivo("Ingrese el ID del producto: ");
            Producto producto = this.servicioProductos.buscarPorIdentificador(identificador);
            System.out.println("[Resultado Encontrado]");
            System.out.println(producto.obtenerDetalle());
        } else if (criterio == 2) {
            String termino = ConsolaLector.leerTexto("Ingrese el texto o nombre a buscar: ");
            ArrayList<Producto> coincidencias = this.servicioProductos.buscarPorNombre(termino);
            if (coincidencias.isEmpty()) {
                System.out.println("No se encontraron productos con el termino: '" + termino + "'.");
            } else {
                System.out.println("[Resultados Encontrados: " + coincidencias.size() + "]");
                for (Producto producto : coincidencias) {
                    System.out.println(producto.obtenerDetalle());
                }
            }
        } else {
            System.out.println("[Atencion] Criterio no reconocido.");
        }
    }

    private void gestionarActualizacionProducto() throws EntidadNoEncontradaException, DatoInvalidoException {
        System.out.println(">>> ACTUALIZACION DE PRODUCTO <<<");
        int identificador = ConsolaLector.leerEnteroPositivo("Ingrese el ID del producto a modificar: ");
        Producto producto = this.servicioProductos.buscarPorIdentificador(identificador);
        System.out.println("Producto actual: " + producto.obtenerDetalle());

        System.out.println("1. Modificar precio");
        System.out.println("2. Modificar stock");
        int opcionModificacion = ConsolaLector.leerEntero("Seleccione opcion (1-2): ");

        if (opcionModificacion == 1) {
            double nuevoPrecio = ConsolaLector.leerDecimalNoNegativo("Nuevo precio: $");
            this.servicioProductos.actualizarPrecio(identificador, nuevoPrecio);
            System.out.println("[Exito] Precio actualizado correctamente.");
        } else if (opcionModificacion == 2) {
            int nuevoStock = ConsolaLector.leerEnteroNoNegativo("Nuevo stock: ");
            this.servicioProductos.actualizarStock(identificador, nuevoStock);
            System.out.println("[Exito] Stock actualizado correctamente.");
        } else {
            System.out.println("[Atencion] Opcion de modificacion invalida.");
        }
    }

    private void gestionarEliminacionProducto() throws EntidadNoEncontradaException {
        System.out.println(">>> ELIMINACION DE PRODUCTO <<<");
        int identificador = ConsolaLector.leerEnteroPositivo("Ingrese el ID del producto a eliminar: ");
        Producto producto = this.servicioProductos.buscarPorIdentificador(identificador);
        System.out.println("Producto a eliminar: " + producto.obtenerDetalle());

        String confirmacion = ConsolaLector.leerTexto("Confirma la eliminacion? (S/N): ");
        if (confirmacion.equalsIgnoreCase("S")) {
            this.servicioProductos.eliminarProducto(identificador);
            System.out.println("[Exito] Producto eliminado del catalogo.");
        } else {
            System.out.println("[Cancelado] Operacion de eliminacion cancelada.");
        }
    }

    private void gestionarCreacionPedido() 
            throws EntidadNoEncontradaException, DatoInvalidoException, StockInsuficienteException {
        System.out.println(">>> CREACION DE NUEVO PEDIDO <<<");
        ArrayList<LineaPedido> lineasPedido = new ArrayList<>();
        boolean cargaActiva = true;

        while (cargaActiva) {
            gestionarListadoProductos();
            int identificadorProducto = ConsolaLector.leerEnteroPositivo("\nID del producto a anadir al pedido: ");
            Producto producto = this.servicioProductos.buscarPorIdentificador(identificadorProducto);

            System.out.println("Producto seleccionado: " + producto.getNombre() + 
                               " | Stock disponible: " + producto.getStock());
            int cantidad = ConsolaLector.leerEnteroPositivo("Cantidad solicitada: ");

            if (cantidad > producto.getStock()) {
                System.out.println("[Aviso] La cantidad solicitada (" + cantidad + 
                                   ") supera el stock actual disponible (" + producto.getStock() + ").");
            }

            LineaPedido linea = new LineaPedido(producto, cantidad);
            lineasPedido.add(linea);
            System.out.println("[Linea anadida] Subtotal: $" + String.format("%.2f", linea.calcularSubtotal()));

            String respuestaContinuar = ConsolaLector.leerTexto("Desea agregar otro producto al pedido? (S/N): ");
            if (!respuestaContinuar.equalsIgnoreCase("S")) {
                cargaActiva = false;
            }
        }

        if (lineasPedido.isEmpty()) {
            System.out.println("[Cancelado] No se incluyeron productos en el pedido.");
            return;
        }

        // Resumen preliminar previo a confirmacion
        double montoTentativo = 0.0;
        System.out.println("\n--- RESUMEN PREVIO DEL PEDIDO ---");
        for (LineaPedido linea : lineasPedido) {
            System.out.println(linea.obtenerDetalle());
            montoTentativo += linea.calcularSubtotal();
        }
        System.out.println("MONTO TOTAL ESTIMADO: $" + String.format("%.2f", montoTentativo));

        String confirmacionPedido = ConsolaLector.leerTexto("Desea confirmar el pedido y descontar el stock? (S/N): ");
        if (confirmacionPedido.equalsIgnoreCase("S")) {
            Pedido pedidoConfirmado = this.servicioPedidos.crearPedido(lineasPedido);
            System.out.println("\n[Exito] Pedido confirmado y registrado exitosamente!");
            System.out.println(pedidoConfirmado.obtenerDetalle());
        } else {
            System.out.println("[Cancelado] Pedido cancelado. El stock no ha sido modificado.");
        }
    }

    private void gestionarListadoPedidos() {
        System.out.println(">>> HISTORIAL DE PEDIDOS REALIZADOS <<<");
        ArrayList<Pedido> listaPedidos = this.servicioPedidos.obtenerTodos();
        if (listaPedidos.isEmpty()) {
            System.out.println("No se han registrado pedidos hasta el momento.");
            return;
        }
        for (Pedido pedido : listaPedidos) {
            System.out.println(pedido.obtenerDetalle());
            System.out.println();
        }
        System.out.println("Total de pedidos realizados: " + listaPedidos.size());
    }
}
