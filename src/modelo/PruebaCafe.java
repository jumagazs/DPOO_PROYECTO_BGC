package modelo;

import usuarios.Cliente;
import usuarios.Usuario;
import juegos.JuegoMesaPrestamo;
import productos.Bebida;
import productos.Pasteleria;
import productos.ProductoMenu;
import java.util.ArrayList;
import java.util.List;
import mesas.Mesa;
import prestamos.Prestamo;
import pedidos.Pedido;
import pedidos.DetallePedido;
import juegos.JuegoMesaVenta;
import ventas.VentaJuego;
import ventas.DetalleVenta;

public class PruebaCafe {
    public static void main(String[] args) {
        try {
            Cafe cafe = new Cafe();

            Cliente cliente1 = cafe.registrarCliente("sebastian@gmail.com", "1234");
            System.out.println("Cliente registrado con " + cliente1.getPuntosFidelidad() + " puntos.");

            Usuario usuario = cafe.iniciarSesion("sebastian@gmail.com", "1234");
            System.out.println("Inicio de sesión exitoso para: " + usuario.getLogin());
            
            JuegoMesaPrestamo juego1 = new JuegoMesaPrestamo(
                    "Catan", 1995, "Kosmos", "Estrategia",
                    3, 4, false, false, true,
                    "JP001", true, 0, "Bueno"
            );

            JuegoMesaPrestamo juego2 = new JuegoMesaPrestamo(
                    "UNO", 1971, "Mattel", "Cartas",
                    2, 10, false, true, true,
                    "JP002", true, 0, "Bueno"
            );

            cafe.agregarJuegoPrestamo(juego1);
            cafe.agregarJuegoPrestamo(juego2);

            System.out.println("\nCatálogo de juegos de préstamo:");
            for (JuegoMesaPrestamo juego : cafe.consultarCatalogoJuegosPrestamo()) {
                System.out.println("- " + juego.getNombre() + " | Categoría: " + juego.getCategoria() + " | Disponible: " + juego.isDisponible());
            }
            
            Bebida bebida1 = new Bebida("B001", "Café Latte", 8500, true, false, true);
            Bebida bebida2 = new Bebida("B002", "Jugo de Naranja", 7000, true, false, false);

            List<String> alergenosTorta = new ArrayList<>();
            alergenosTorta.add("Gluten");
            alergenosTorta.add("Leche");

            Pasteleria pastel1 = new Pasteleria("P001", "Torta de Chocolate", 9000, true, alergenosTorta);

            cafe.agregarProductoMenu(bebida1);
            cafe.agregarProductoMenu(bebida2);
            cafe.agregarProductoMenu(pastel1);

            System.out.println("\nMenú del café:");
            for (ProductoMenu producto : cafe.consultarMenu()) {
                System.out.println("- " + producto.getNombre() + " | Precio: " + producto.getPrecio()
                        + " | Disponible: " + producto.isDisponible());
            }
            
            Mesa mesa1 = new Mesa("M001", 2);
            Mesa mesa2 = new Mesa("M002", 4);
            Mesa mesa3 = new Mesa("M003", 6);

            cafe.agregarMesa(mesa1);
            cafe.agregarMesa(mesa2);
            cafe.agregarMesa(mesa3);

            Mesa mesaAsignada = cafe.asignarMesaACliente(cliente1, 3);
            System.out.println("\nMesa asignada al cliente: " + mesaAsignada.getIdMesa()
                    + " | Capacidad: " + mesaAsignada.getCapacidad()
                    + " | Ocupada: " + mesaAsignada.isOcupada());
            
            Prestamo prestamo1 = cafe.solicitarPrestamoJuego(cliente1, mesaAsignada, "JP001", false);

            System.out.println("\nPréstamo realizado:");
            System.out.println("ID préstamo: " + prestamo1.getIdPrestamo());
            System.out.println("Juego prestado: " + prestamo1.getJuego().getNombre());
            System.out.println("Cliente: " + prestamo1.getCliente().getLogin());
            System.out.println("Disponible después del préstamo: " + prestamo1.getJuego().isDisponible());
            
            cafe.devolverJuego("PR1", "2026-04-17");

            System.out.println("\nDevolución realizada:");
            System.out.println("Fecha devolución: " + prestamo1.getFechaDevolucion());
            System.out.println("Juego disponible nuevamente: " + prestamo1.getJuego().isDisponible());
            System.out.println("Veces prestado: " + prestamo1.getJuego().getVecesPrestado());
            
            try {
                cafe.devolverJuego("PR1", "2026-04-18");
            } catch (Exception e) {
                System.out.println("\nError esperado: " + e.getMessage());
            }
            
            Pedido pedido1 = cafe.crearPedido(mesaAsignada);
            cafe.agregarProductoAPedido(pedido1, "B001", 2); // Café Latte
            cafe.agregarProductoAPedido(pedido1, "P001", 1); // Torta
            cafe.confirmarPedido(pedido1);

            System.out.println("\nPedido realizado:");
            System.out.println("ID pedido: " + pedido1.getIdPedido());
            System.out.println("Mesa: " + pedido1.getMesa().getIdMesa());
            System.out.println("Subtotal: " + pedido1.getSubtotal());
            System.out.println("Impuesto consumo: " + pedido1.getImpuestoConsumo());
            System.out.println("Propina: " + pedido1.getPropina());
            System.out.println("Total: " + pedido1.getTotal());

            System.out.println("Detalles del pedido:");
            for (DetallePedido detalle : pedido1.getDetalles()) {
                System.out.println("- " + detalle.getProducto().getNombre()
                        + " | Cantidad: " + detalle.getCantidad()
                        + " | Subtotal: " + detalle.getSubtotal());
            }
            
            JuegoMesaVenta juegoVenta1 = new JuegoMesaVenta(
                    "Terraforming Mars", 2016, "FryxGames", "Estrategia",
                    1, 5, true, false, true,
                    "JV001", 180000, 3, 120000
            );

            cafe.agregarJuegoVenta(juegoVenta1);

            VentaJuego venta1 = cafe.comprarJuegoMesa(cliente1, "JV001", 2);

            System.out.println("\nCompra de juego realizada:");
            System.out.println("ID venta: " + venta1.getIdVenta());
            System.out.println("Cliente: " + venta1.getComprador().getLogin());
            System.out.println("Subtotal: " + venta1.getSubTotal());
            System.out.println("Impuesto: " + venta1.getImpuesto());
            System.out.println("Total: " + venta1.getTotal());
            System.out.println("Puntos generados: " + venta1.getPuntosGenerados());
            System.out.println("Puntos actuales del cliente: " + cliente1.getPuntosFidelidad());

            System.out.println("Detalles de la venta:");
            for (DetalleVenta detalle : venta1.getDetalles()) {
                System.out.println("- " + detalle.getJuego().getNombre()
                        + " | Cantidad: " + detalle.getCantidad()
                        + " | Subtotal: " + detalle.getSubtotal());
            }

            System.out.println("Stock restante del juego: " + juegoVenta1.getCantidadStock());
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}