package pruebas;

import java.time.LocalDateTime;
import java.util.*;
import juegos.*;
import mesas.*;
import pedidos.*;
import prestamos.*;
import productos.*;
import usuarios.*;
import ventas.*;
import modelo.*;

public class PruebasAutomaticas {

    public static void main(String[] args) {
        try {
            Cafe cafe = new Cafe();
            GestorPersistencia gestor = new GestorPersistencia("C:/Users/jumag/OneDrive/Documentos/U/Diseño y Programación Orientada a Objetos/Proyectos/Proyecto 1/Datos/datosTest.txt");
            gestor.cargarTodo(cafe);

            // ---- RF1 - Registrar cliente ----
            try {
                cafe.registrarCliente("cliente1", "1234");
                System.out.println("RF1 OK - Cliente registrado");
            } catch (Exception e) {
                System.out.println("RF1 ERROR: " + e.getMessage());
            }
            try {
                cafe.registrarCliente("cliente1", "1234");
                System.out.println("RF1 ERROR - Debió bloquear duplicado");
            } catch (Exception e) {
                System.out.println("RF1 OK - Duplicado bloqueado: " + e.getMessage());
            }

            // ---- RF2 - Iniciar sesion ----
            try {
                cafe.iniciarSesion("cliente1", "1234");
                System.out.println("RF2 OK - Sesion iniciada");
            } catch (Exception e) {
                System.out.println("RF2 ERROR: " + e.getMessage());
            }
            try {
                cafe.iniciarSesion("cliente1", "wrong");
                System.out.println("RF2 ERROR - Debio bloquear contrasena incorrecta");
            } catch (Exception e) {
                System.out.println("RF2 OK - Contrasena incorrecta bloqueada: " + e.getMessage());
            }

            // ---- RF Admin 1 - Registrar empleados ----
            try {
                cafe.registrarMesero("admin", "mesero1", "1234");
                cafe.registrarMesero("admin", "mesero2", "1234");
                cafe.registrarCocinero("admin", "cocinero1", "1234");
                System.out.println("RF Admin 1 OK - Mesero y cocinero registrados");
            } catch (Exception e) {
                System.out.println("RF Admin 1 ERROR: " + e.getMessage());
            }

            // ---- RF Admin 2 - Agregar turnos ----
            try {
                Empleado mesero1 = (Empleado) cafe.getUsuarios().get("mesero1");
                Empleado cocinero1 = (Empleado) cafe.getUsuarios().get("cocinero1");
                Turno turnoMesero = new Turno("T1", LocalDateTime.of(2026, 4, 20, 8, 0), LocalDateTime.of(2026, 4, 20, 16, 0), "LUNES", mesero1);
                Turno turnoCocinero = new Turno("T2", LocalDateTime.of(2026, 4, 20, 8, 0), LocalDateTime.of(2026, 4, 20, 16, 0), "LUNES", cocinero1);
                cafe.agregarTurno("admin", "mesero1", turnoMesero);
                cafe.agregarTurno("admin", "cocinero1", turnoCocinero);
                System.out.println("RF Admin 2 OK - Turnos asignados");
            } catch (Exception e) {
                System.out.println("RF Admin 2 ERROR: " + e.getMessage());
            }

            // ---- RF12 - Consultar turno ----
            try {
                List<Turno> turnos = cafe.consultarTurnoEmpleado("mesero1");
                System.out.println("RF12 OK - Turnos del mesero: " + turnos.size());
                for (Turno t : turnos) {
                    System.out.println("  " + t.toString());
                }
            } catch (Exception e) {
                System.out.println("RF12 ERROR: " + e.getMessage());
            }

            // ---- RF Admin 5 - Registrar juegos ----
            try {
                cafe.agregarJuegoPrestamo("admin", "Catan", 1995, "Kosmos", "Tablero", 3, 4, false, false, true, true, 0, "Bueno");
                cafe.agregarJuegoPrestamo("admin", "UNO", 1971, "Mattel", "Cartas", 2, 10, false, true, true, true, 0, "Bueno");
                cafe.agregarJuegoPrestamo("admin", "Twilight Imperium", 2017, "FFG", "Tablero", 3, 6, true, false, false, true, 0, "Nuevo");
                cafe.agregarJuegoVenta("admin", "Terraforming Mars", 2016, "FryxGames", "Tablero", 1, 5, false, false, true, 180000, 5, 120000);
                System.out.println("RF Admin 5 OK - Juegos registrados");
            } catch (Exception e) {
                System.out.println("RF Admin 5 ERROR: " + e.getMessage());
            }
            
         // ---- RF Admin 3 - Juego conocido mesero ----
            try {
                String idJuegoDificil = null;
                for (Map.Entry<String, JuegoMesaPrestamo> entry : cafe.getJuegosPrestamo().entrySet()) {
                    if (entry.getValue().getNombre().equals("Twilight Imperium")) {
                        idJuegoDificil = entry.getKey();
                        break;
                    }
                }
                cafe.agregarJuegoConocidoMesero("admin", "mesero1", idJuegoDificil);
                System.out.println("RF Admin 3 OK - Juego conocido agregado a mesero");
            } catch (Exception e) {
                System.out.println("RF Admin 3 ERROR: " + e.getMessage());
            }   

         // ---- RF Admin 3 - Ver inventario detallado ----
            try {
                Map<String, List<Prestamo>> historial = cafe.consultarInventarioPrestamo("admin");
                System.out.println("RF Admin 3 OK - Inventario detallado:");
                for (Map.Entry<String, List<Prestamo>> entry : historial.entrySet()) {
                    JuegoMesaPrestamo j = cafe.getJuegosPrestamo().get(entry.getKey());
                    System.out.println("  " + j.getNombre() + " | Estado: " + j.getEstado() + " | Veces prestado: " + j.getVecesPrestado());
                    for (Prestamo p : entry.getValue()) {
                        System.out.println("  - " + p.getIdPrestamo() + " | " + p.getUsuario().getLogin() + " | " + p.getFechaPrestamo() + " | Devuelto: " + p.fueDevuelto());
                    }
                }
            } catch (Exception e) {
                System.out.println("RF Admin 3 ERROR: " + e.getMessage());
            }

            // ---- RF Admin 6 - Gestionar menu ----
            try {
                cafe.agregarBebida("admin", "Cafe Latte", 8500, true, false, true);
                cafe.agregarBebida("admin", "Cerveza", 9000, true, true, false);
                cafe.agregarPasteleria("admin", "Torta Chocolate", 9000, true, Arrays.asList("Gluten", "Leche"));
                System.out.println("RF Admin 6 OK - Menu agregado");
            } catch (Exception e) {
                System.out.println("RF Admin 6 ERROR: " + e.getMessage());
            }

            // ---- RF3 - Catalogo juegos ----
            try {
                System.out.println("\nRF3 - Catalogo juegos prestamo:");
                for (JuegoMesaPrestamo j : cafe.consultarCatalogoJuegosPrestamo()) {
                    System.out.println("  " + j.getNombre() + " | Disponible: " + j.isDisponible());
                }
                System.out.println("RF3 OK");
            } catch (Exception e) {
                System.out.println("RF3 ERROR: " + e.getMessage());
            }

            // ---- RF7 - Consultar menu ----
            try {
                System.out.println("\nRF7 - Menu:");
                for (ProductoMenu p : cafe.consultarMenu()) {
                    System.out.println("  " + p.toString());
                }
                System.out.println("RF7 OK");
            } catch (Exception e) {
                System.out.println("RF7 ERROR: " + e.getMessage());
            }

            // ---- RF4 - Asignar mesa ----
            Mesa mesaAsignada = null;
            try {
                Cliente cliente1 = (Cliente) cafe.getUsuarios().get("cliente1");
                mesaAsignada = cafe.asignarMesaACliente(cliente1, 3, false, false);
                System.out.println("RF4 OK - Mesa asignada: " + mesaAsignada.getIdMesa());
            } catch (Exception e) {
                System.out.println("RF4 ERROR: " + e.getMessage());
            }

            // ---- RF5/RF19 - Prestamo mesero a cliente ----
            Prestamo prestamo1 = null;
            try {
                String idJuego = null;
                for (Map.Entry<String, JuegoMesaPrestamo> entry : cafe.getJuegosPrestamo().entrySet()) {
                    if (entry.getValue().getNombre().equals("Catan")) {
                        idJuego = entry.getKey();
                        break;
                    }
                }
                prestamo1 = cafe.prestamoDeJuegos(idJuego, "mesero1", mesaAsignada.getIdMesa(), "cliente1");
                System.out.println("RF19 OK - Prestamo realizado: " + prestamo1.getIdPrestamo());
            } catch (Exception e) {
                System.out.println("RF19 ERROR: " + e.getMessage());
            }

            // ---- RF19 - Prestamo juego dificil con advertencia ----
            try {
                String idJuegoDificil = null;
                for (Map.Entry<String, JuegoMesaPrestamo> entry : cafe.getJuegosPrestamo().entrySet()) {
                    if (entry.getValue().getNombre().equals("Twilight Imperium")) {
                        idJuegoDificil = entry.getKey();
                        break;
                    }
                }
                cafe.prestamoDeJuegos(idJuegoDificil, "mesero1", mesaAsignada.getIdMesa(), "cliente1");
                System.out.println("RF19 OK - Prestamo juego dificil con advertencia si aplica");
            } catch (Exception e) {
                System.out.println("RF19 ERROR: " + e.getMessage());
            }

            // ---- RF6 - Devolver juego ----
            try {
                cafe.devolverJuego(prestamo1.getIdPrestamo());
                System.out.println("RF6 OK - Juego devuelto");
            } catch (Exception e) {
                System.out.println("RF6 ERROR: " + e.getMessage());
            }
            try {
                cafe.devolverJuego(prestamo1.getIdPrestamo());
                System.out.println("RF6 ERROR - Debio bloquear devolucion duplicada");
            } catch (Exception e) {
                System.out.println("RF6 OK - Devolucion duplicada bloqueada: " + e.getMessage());
            }

            // ---- RF8/RF18 - Pedido cafeteria ----
            Pedido pedido1 = null;
            try {
                pedido1 = cafe.registrarPedidoMesero("mesero1", mesaAsignada.getIdMesa());
                String idBebida = null;
                for (Map.Entry<String, ProductoMenu> entry : cafe.getMenu().entrySet()) {
                    if (entry.getValue().getNombre().equals("Cafe Latte")) {
                        idBebida = entry.getKey();
                        break;
                    }
                }
                cafe.agregarProductoAPedido(pedido1, idBebida, 2);
                cafe.confirmarPedido(pedido1);
                System.out.println("RF8 OK - Pedido | Total: " + pedido1.getTotal());
            } catch (Exception e) {
                System.out.println("RF8 ERROR: " + e.getMessage());
            }

            // ---- RF20 - Cocinero prepara pedido ----
            try {
                cafe.prepararPedido("cocinero1", pedido1.getIdPedido());
                System.out.println("RF20 OK - Pedido preparado por cocinero");
            } catch (Exception e) {
                System.out.println("RF20 ERROR: " + e.getMessage());
            }

            // ---- RF9 - Comprar juego ----
            try {
                String idJuegoVenta = null;
                for (Map.Entry<String, JuegoMesaVenta> entry : cafe.getJuegosVenta().entrySet()) {
                    if (entry.getValue().getNombre().equals("Terraforming Mars")) {
                        idJuegoVenta = entry.getKey();
                        break;
                    }
                }
                VentaJuego venta = cafe.comprarJuegoConDescuento("cliente1", idJuegoVenta, 1, 0, "");
                System.out.println("RF9 OK - Compra | Total: " + venta.getTotal() + " | Puntos: " + venta.getPuntosGenerados());
            } catch (Exception e) {
                System.out.println("RF9 ERROR: " + e.getMessage());
            }

            // ---- RF10 - Puntos fidelidad ----
            try {
                Cliente cliente1 = (Cliente) cafe.getUsuarios().get("cliente1");
                System.out.println("RF10 - Puntos actuales: " + cliente1.getPuntosFidelidad());
                String idJuegoVenta = null;
                for (Map.Entry<String, JuegoMesaVenta> entry : cafe.getJuegosVenta().entrySet()) {
                    if (entry.getValue().getNombre().equals("Terraforming Mars")) {
                        idJuegoVenta = entry.getKey();
                        break;
                    }
                }
                VentaJuego ventaConPuntos = cafe.comprarJuegoConDescuento("cliente1", idJuegoVenta, 1, cliente1.getPuntosFidelidad(), "");
                System.out.println("RF10 OK - Compra con puntos | Total: " + ventaConPuntos.getTotal());
            } catch (Exception e) {
                System.out.println("RF10 ERROR: " + e.getMessage());
            }

            // ---- RF14 - Compra empleado con descuento ----
            try {
                String idJuegoVenta = null;
                for (Map.Entry<String, JuegoMesaVenta> entry : cafe.getJuegosVenta().entrySet()) {
                    if (entry.getValue().getNombre().equals("Terraforming Mars")) {
                        idJuegoVenta = entry.getKey();
                        break;
                    }
                }
                VentaJuego ventaEmp = cafe.comprarJuegoConDescuento("mesero1", idJuegoVenta, 1, 0, "");
                System.out.println("RF14 OK - Compra empleado 20% descuento | Total: " + ventaEmp.getTotal());
            } catch (Exception e) {
                System.out.println("RF14 ERROR: " + e.getMessage());
            }

            // ---- RF11/RF16 - Favoritos ----
            try {
                String idJuego = null;
                for (Map.Entry<String, JuegoMesaPrestamo> entry : cafe.getJuegosPrestamo().entrySet()) {
                    if (entry.getValue().getNombre().equals("UNO")) {
                        idJuego = entry.getKey();
                        break;
                    }
                }
                cafe.agregarJuegoFavoritoAUsuario("cliente1", idJuego);
                cafe.agregarJuegoFavoritoAUsuario("mesero1", idJuego);
                System.out.println("RF11/RF16 OK - Favorito agregado");
                for (JuegoMesa j : cafe.consultarFavoritos("cliente1")) {
                    System.out.println("  Favorito cliente: " + j.getNombre());
                }
                for (JuegoMesa j : cafe.consultarFavoritos("mesero1")) {
                    System.out.println("  Favorito mesero: " + j.getNombre());
                }
            } catch (Exception e) {
                System.out.println("RF11/RF16 ERROR: " + e.getMessage());
            }

         // ---- RF13 - Solicitar intercambio de turno ----
            try {
                cafe.registrarMesero("admin", "mesero3", "1234");
                Empleado mesero3 = (Empleado) cafe.getUsuarios().get("mesero3");
                Turno turnoMesero3 = new Turno("T3", LocalDateTime.of(2026, 4, 21, 8, 0), LocalDateTime.of(2026, 4, 21, 16, 0), "MARTES", mesero3);
                cafe.agregarTurno("admin", "mesero3", turnoMesero3);
                SolicitudCambioTurno sc2 = cafe.solicitarIntercambioTurno("mesero1", "T1", "mesero3", "T3");
                System.out.println("RF13 OK - Solicitud intercambio creada: " + sc2.getIdSolicitud());
                cafe.aprobarCambioTurno("admin", sc2.getIdSolicitud());
                System.out.println("RF13 OK - Intercambio aprobado");
                System.out.println("  Turno mesero1: " + cafe.consultarTurnoEmpleado("mesero1").get(0).getDia());
                System.out.println("  Turno mesero3: " + cafe.consultarTurnoEmpleado("mesero3").get(0).getDia());
            } catch (Exception e) {
                System.out.println("RF13 intercambio ERROR: " + e.getMessage());
            }

         // ---- RF13 - Cambio de turno general ----
            try {
                Empleado mesero2 = (Empleado) cafe.getUsuarios().get("mesero2");
                Turno turnoMesero2 = new Turno("T4", LocalDateTime.of(2026, 4, 22, 8, 0), LocalDateTime.of(2026, 4, 22, 16, 0), "MIERCOLES", mesero2);
                cafe.agregarTurno("admin", "mesero2", turnoMesero2);
                SolicitudCambioTurno sc = cafe.solicitarCambioTurnoGeneral("mesero2", "T4");
                System.out.println("RF13 OK - Solicitud creada: " + sc.getIdSolicitud());
                cafe.aprobarCambioTurno("admin", sc.getIdSolicitud());
                System.out.println("RF13 OK - Solicitud aprobada");
            } catch (Exception e) {
                System.out.println("RF13 ERROR: " + e.getMessage());
            }

            // ---- RF17 - Sugerir plato ----
            try {
                cafe.sugerirPlato("mesero1", "Brownie", 7000, false, true, Arrays.asList("Gluten"), "PASTELERIA");
                System.out.println("RF17 OK - Sugerencia enviada");
            } catch (Exception e) {
                System.out.println("RF17 ERROR: " + e.getMessage());
            }

            // ---- RF Admin 6 - Aprobar sugerencia ----
            try {
                String idSugerencia = cafe.getSugerencias().get(0).getIdSugerencia();
                cafe.aprobarSugerenciaPlato("admin", idSugerencia);
                System.out.println("RF Admin 6 OK - Sugerencia aprobada y plato en menu");
            } catch (Exception e) {
                System.out.println("RF Admin 6 ERROR: " + e.getMessage());
            }

            // ---- RF Admin 4 - Cambiar estado juego ----
            try {
                String idJuego = cafe.getJuegosPrestamo().keySet().iterator().next();
                cafe.cambiarEstadoJuego("admin", idJuego, "Falta una pieza");
                System.out.println("RF Admin 4 OK - Estado juego cambiado");
            } catch (Exception e) {
                System.out.println("RF Admin 4 ERROR: " + e.getMessage());
            }
            
         // ---- RF Admin 4 - Ver inventario venta ----
            try {
                Map<String, JuegoMesaVenta> inventario = cafe.consultarInventarioVenta("admin");
                System.out.println("RF Admin 4 OK - Inventario venta:");
                for (JuegoMesaVenta j : inventario.values()) {
                    System.out.println("  " + j.getNombre() + " | Stock: " + j.getCantidadStock() + " | Precio: " + j.getPrecioVenta());
                }
            } catch (Exception e) {
                System.out.println("RF Admin 4 ERROR: " + e.getMessage());
            }

            // ---- RF Admin 7 - Informe ----
            try {
                System.out.println("\nRF Admin 7 - Informe diario:");
                cafe.consultarInforme("admin", "diaria");
                System.out.println("RF Admin 7 OK");
            } catch (Exception e) {
                System.out.println("RF Admin 7 ERROR: " + e.getMessage());
            }
            System.out.println("Guardando...");
            gestor.guardarTodo(cafe);
            
            System.out.println("\nPruebas finalizadas y datos guardados.");

        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
    }
}