package modelo;

import java.time.LocalDateTime;
import java.util.*;
import juegos.*;
import pedidos.*;
import prestamos.*;
import productos.*;
import usuarios.*;
import ventas.*;

public class PruebasCafe {

	public static void main(String[] args) throws Exception {
	    Cafe cafe = new Cafe();
	    GestorPersistencia gestor = new GestorPersistencia("C:/Users/jumag/OneDrive/Documentos/U/Diseño y Programación Orientada a Objetos/Proyectos/Proyecto 1/Datos/datos.txt");
	    gestor.cargarTodo(cafe);
	    Scanner sc = new Scanner(System.in);

	    int opcionInicio = -1;
	    while (opcionInicio != 0) {
	        System.out.println("\n--- BIENVENIDO ---");
	        System.out.println("1. Iniciar sesión");
	        System.out.println("2. Registrarse como cliente");
	        System.out.println("0. Salir");
	        opcionInicio = Integer.parseInt(sc.nextLine());

	        if (opcionInicio == 1) {
	            System.out.println("Login: ");
	            String login = sc.nextLine();
	            System.out.println("Contraseña: ");
	            String contrasena = sc.nextLine();
	            try {
	                Usuario usuario = cafe.iniciarSesion(login, contrasena);
	                if (usuario instanceof Administrador) {
	                    menuAdmin(cafe, sc, login);
	                } else if (usuario instanceof Mesero) {
	                    menuMesero(cafe, sc, login);
	                } else if (usuario instanceof Cocinero) {
	                    menuCocinero(cafe, sc, login);
	                } else if (usuario instanceof Cliente) {
	                    menuCliente(cafe, sc, login);
	                }
	            } catch (Exception e) {
	                System.out.println("Error: " + e.getMessage());
	            }

	        } else if (opcionInicio == 2) {
	            System.out.println("Login: ");
	            String login = sc.nextLine();
	            System.out.println("Contraseña: ");
	            String contrasena = sc.nextLine();
	            cafe.registrarCliente(login, contrasena);
	            System.out.println("Cliente registrado exitosamente.");
	        }
	    }

	    gestor.guardarTodo(cafe);
	    sc.close();
	}


    private static void menuAdmin(Cafe cafe, Scanner sc, String login) throws Exception {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENU ADMINISTRADOR ---");
            System.out.println("1. Registrar mesero");
            System.out.println("2. Registrar cocinero");
            System.out.println("3. Agregar juego préstamo");
            System.out.println("4. Agregar juego venta");
            System.out.println("5. Agregar turno a empleado");
            System.out.println("6. Aprobar cambio de turno");
            System.out.println("7. Rechazar cambio de turno");
            System.out.println("8. Agregar bebida");
            System.out.println("9. Agregar pastelería");
            System.out.println("10. Aprobar sugerencia plato");
            System.out.println("11. Rechazar sugerencia plato");
            System.out.println("12. Cambiar estado juego préstamo");
            System.out.println("13. Eliminar juego préstamo");
            System.out.println("14. Eliminar juego venta");
            System.out.println("15. Agregar juegos dificiles a un empleado");
            System.out.println("16. Informe diario");
            System.out.println("17. Informe semanal");
            System.out.println("18. Informe mensual");

            System.out.println("0. Salir");
            opcion = Integer.parseInt(sc.nextLine());

            if (opcion == 1) {
                System.out.println("Login mesero: ");
                String l = sc.nextLine();
                System.out.println("Contraseña: ");
                String c = sc.nextLine();
                cafe.registrarMesero(login, l, c);
                System.out.println("Mesero registrado.");

            } else if (opcion == 2) {
                System.out.println("Login cocinero: ");
                String l = sc.nextLine();
                System.out.println("Contraseña: ");
                String c = sc.nextLine();
                cafe.registrarCocinero(login, l, c);
                System.out.println("Cocinero registrado.");

            } else if (opcion == 3) {
                System.out.println("Nombre: ");
                String nombre = sc.nextLine();
                System.out.println("Año publicación: ");
                int anio = Integer.parseInt(sc.nextLine());
                System.out.println("Editor: ");
                String editor = sc.nextLine();
                System.out.println("Categoria (Cartas/Tablero/Accion): ");
                String categoria = sc.nextLine();
                System.out.println("Min jugadores: ");
                int min = Integer.parseInt(sc.nextLine());
                System.out.println("Max jugadores: ");
                int max = Integer.parseInt(sc.nextLine());
                System.out.println("Es dificil (true/false): ");
                boolean dificil = Boolean.parseBoolean(sc.nextLine());
                System.out.println("Juegan menores 5 (true/false): ");
                boolean menores5 = Boolean.parseBoolean(sc.nextLine());
                System.out.println("Juegan menores 18 (true/false): ");
                boolean menores18 = Boolean.parseBoolean(sc.nextLine());
                System.out.println("Estado (Nuevo/Bueno/Falta una pieza): ");
                String estado = sc.nextLine();
                cafe.agregarJuegoPrestamo(login, nombre, anio, editor, categoria, min, max, dificil, menores5, menores18, true, 0, estado);
                System.out.println("Juego préstamo agregado.");

            } else if (opcion == 4) {
                System.out.println("Nombre: ");
                String nombre = sc.nextLine();
                System.out.println("Año publicación: ");
                int anio = Integer.parseInt(sc.nextLine());
                System.out.println("Editor: ");
                String editor = sc.nextLine();
                System.out.println("Categoria (Cartas/Tablero/Accion): ");
                String categoria = sc.nextLine();
                System.out.println("Min jugadores: ");
                int min = Integer.parseInt(sc.nextLine());
                System.out.println("Max jugadores: ");
                int max = Integer.parseInt(sc.nextLine());
                System.out.println("Es dificil (true/false): ");
                boolean dificil = Boolean.parseBoolean(sc.nextLine());
                System.out.println("Juegan menores 5 (true/false): ");
                boolean menores5 = Boolean.parseBoolean(sc.nextLine());
                System.out.println("Juegan menores 18 (true/false): ");
                boolean menores18 = Boolean.parseBoolean(sc.nextLine());
                System.out.println("Precio venta: ");
                double precio = Double.parseDouble(sc.nextLine());
                System.out.println("Stock: ");
                int stock = Integer.parseInt(sc.nextLine());
                System.out.println("Costo base: ");
                double costo = Double.parseDouble(sc.nextLine());
                cafe.agregarJuegoVenta(login, nombre, anio, editor, categoria, min, max, dificil, menores5, menores18, precio, stock, costo);
                System.out.println("Juego venta agregado.");

            } else if (opcion == 5) {
                System.out.println("Login empleado: ");
                String idEmpleado = sc.nextLine();
                System.out.println("ID turno: ");
                String idTurno = sc.nextLine();
                System.out.println("Dia (LUNES/MARTES/...): ");
                String dia = sc.nextLine();
                System.out.println("Hora inicio (yyyy-MM-ddTHH:mm): ");
                LocalDateTime inicio = LocalDateTime.parse(sc.nextLine());
                System.out.println("Hora fin (yyyy-MM-ddTHH:mm): ");
                LocalDateTime fin = LocalDateTime.parse(sc.nextLine());
                Empleado emp = (Empleado) cafe.getUsuarios().get(idEmpleado);
                Turno turno = new Turno(idTurno, inicio, fin, dia, emp);
                cafe.agregarTurno(login, idEmpleado, turno);
                System.out.println("Turno asignado.");
            } else if (opcion == 6) {
                System.out.println("ID solicitud: ");
                String idSolicitud = sc.nextLine();
                cafe.aprobarCambioTurno(login, idSolicitud);
                System.out.println("Solicitud aprobada.");

            } else if (opcion == 7) {
                System.out.println("ID solicitud: ");
                String idSolicitud = sc.nextLine();
                cafe.rechazarCambioTurno(login, idSolicitud);
                System.out.println("Solicitud rechazada.");

            } else if (opcion == 8) {
                System.out.println("Nombre: ");
                String nombre = sc.nextLine();
                System.out.println("Precio: ");
                double precio = Double.parseDouble(sc.nextLine());
                System.out.println("Es alcoholica (true/false): ");
                boolean alcoholica = Boolean.parseBoolean(sc.nextLine());
                System.out.println("Es caliente (true/false): ");
                boolean caliente = Boolean.parseBoolean(sc.nextLine());
                cafe.agregarBebida(login, nombre, precio, true, alcoholica, caliente);
                System.out.println("Bebida agregada.");

            } else if (opcion == 9) {
                System.out.println("Nombre: ");
                String nombre = sc.nextLine();
                System.out.println("Precio: ");
                double precio = Double.parseDouble(sc.nextLine());
                System.out.println("Alergenos (separados por coma): ");
                List<String> alergenos = Arrays.asList(sc.nextLine().split(","));
                cafe.agregarPasteleria(login, nombre, precio, true, alergenos);
                System.out.println("Pastelería agregada.");

            } else if (opcion == 10) {
                System.out.println("ID sugerencia: ");
                String idSugerencia = sc.nextLine();
                cafe.aprobarSugerenciaPlato(login, idSugerencia);
                System.out.println("Sugerencia aprobada.");

            } else if (opcion == 11) {
                System.out.println("ID sugerencia: ");
                String idSugerencia = sc.nextLine();
                cafe.rechazarSugerenciaPlato(login, idSugerencia);
                System.out.println("Sugerencia rechazada.");

            } else if (opcion == 12) {
                System.out.println("ID juego préstamo: ");
                String idJuego = sc.nextLine();
                System.out.println("Nuevo estado: ");
                String estado = sc.nextLine();
                cafe.cambiarEstadoJuego(login, idJuego, estado);
                System.out.println("Estado actualizado.");

            } else if (opcion == 13) {
                System.out.println("ID juego préstamo: ");
                String idJuego = sc.nextLine();
                cafe.eliminarJuegoPrestamo(login, idJuego);
                System.out.println("Juego préstamo eliminado.");

            } else if (opcion == 14) {
                System.out.println("ID juego venta: ");
                String idJuego = sc.nextLine();
                cafe.eliminarJuegoVenta(login, idJuego);
                System.out.println("Juego venta eliminado.");
            } else if (opcion == 15) {
                System.out.println("Login mesero: ");
                String idMesero = sc.nextLine();
                System.out.println("ID juego: ");
                String idJuego = sc.nextLine();
                cafe.agregarJuegoConocidoMesero(login, idMesero, idJuego);
                System.out.println("Juego conocido agregado al mesero.");
     
            } else if (opcion == 16) {
                cafe.consultarInforme(login, "diaria");
            } else if (opcion == 17) {
                cafe.consultarInforme(login, "semanal");
            } else if (opcion == 18) {
                cafe.consultarInforme(login, "mensual");
            }
        }
    }


    private static void menuMesero(Cafe cafe, Scanner sc, String login) throws Exception {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENU MESERO ---");
            System.out.println("1. Registrar pedido a mesa");
            System.out.println("2. Prestar juego a cliente");
            System.out.println("3. Devolver juego");
            System.out.println("4. Consultar turno");
            System.out.println("5. Solicitar cambio de turno general");
            System.out.println("6. Solicitar intercambio de turno");
            System.out.println("7. Ver favoritos");
            System.out.println("8. Agregar favorito");
            System.out.println("0. Salir");
            opcion = Integer.parseInt(sc.nextLine());

            if (opcion == 1) {
                System.out.println("ID mesa: ");
                String idMesa = sc.nextLine();
                Pedido pedido = cafe.registrarPedidoMesero(login, idMesa);
                System.out.println("Pedido creado: " + pedido.getIdPedido());
                System.out.println("ID producto a agregar (enter para terminar): ");
                String idProducto = sc.nextLine();
                while (!idProducto.isEmpty()) {
                    System.out.println("Cantidad: ");
                    int cantidad = Integer.parseInt(sc.nextLine());
                    cafe.agregarProductoAPedido(pedido, idProducto, cantidad);
                    System.out.println("ID producto (enter para terminar): ");
                    idProducto = sc.nextLine();
                }
                cafe.confirmarPedido(pedido);
                System.out.println("Pedido confirmado | Total: " + pedido.getTotal());

            } else if (opcion == 2) {
                System.out.println("ID juego: ");
                String idJuego = sc.nextLine();
                System.out.println("ID mesa: ");
                String idMesa = sc.nextLine();
                System.out.println("Login cliente: ");
                String idCliente = sc.nextLine();
                Prestamo p = cafe.prestamoDeJuegos(idJuego, login, idMesa, idCliente);
                System.out.println("Préstamo realizado: " + p.getIdPrestamo());

            } else if (opcion == 3) {
                System.out.println("ID préstamo: ");
                String idPrestamo = sc.nextLine();
                cafe.devolverJuego(idPrestamo);
                System.out.println("Juego devuelto.");

            } else if (opcion == 4) {
            	for (Turno t : cafe.consultarTurnoEmpleado(login)) {
            	    System.out.println(t.toString());
            	}


            } else if (opcion == 5) {
                System.out.println("ID turno a cambiar: ");
                String idTurno = sc.nextLine();
                SolicitudCambioTurno sc2 = cafe.solicitarCambioTurnoGeneral(login, idTurno);
                System.out.println("Solicitud creada: " + sc2.getIdSolicitud());
            } else if (opcion == 6) {
                System.out.println("ID turno a intercambiar: ");
                String idTurno = sc.nextLine();
                System.out.println("Login empleado destino: ");
                String loginDestino = sc.nextLine();
                System.out.println("ID turno del destino: ");
                String idTurnoDestino = sc.nextLine();
                SolicitudCambioTurno sc2 = cafe.solicitarIntercambioTurno(login, idTurno, loginDestino, idTurnoDestino);
                System.out.println("Solicitud creada: " + sc2.getIdSolicitud());

            } else if (opcion == 7) {
                for (JuegoMesa j : cafe.consultarFavoritos(login)) {
                    System.out.println(j.toString());
                }

            } else if (opcion == 8) {
                System.out.println("ID juego: ");
                String idJuego = sc.nextLine();
                cafe.agregarJuegoFavoritoAUsuario(login, idJuego);
                System.out.println("Favorito agregado.");
            }
        }
    }


    private static void menuCocinero(Cafe cafe, Scanner sc, String login) throws Exception {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENU COCINERO ---");
            System.out.println("1. Preparar pedido");
            System.out.println("2. Consultar turno");
            System.out.println("3. Solicitar cambio de turno general");
            System.out.println("4. Solicitar intercambio de turno");
            System.out.println("5. Ver favoritos");
            System.out.println("6. Agregar favorito");
            System.out.println("0. Salir");
            opcion = Integer.parseInt(sc.nextLine());

            if (opcion == 1) {
                System.out.println("ID pedido: ");
                String idPedido = sc.nextLine();
                cafe.prepararPedido(login, idPedido);
                System.out.println("Pedido preparado.");

            } else if (opcion == 2) {
            	for (Turno t : cafe.consultarTurnoEmpleado(login)) {
            	    System.out.println(t.toString());
            	}

            } else if (opcion == 3) {
                System.out.println("ID turno a cambiar: ");
                String idTurno = sc.nextLine();
                SolicitudCambioTurno sc2 = cafe.solicitarCambioTurnoGeneral(login, idTurno);
                System.out.println("Solicitud creada: " + sc2.getIdSolicitud());

            } else if (opcion == 4) {
                System.out.println("ID turno a intercambiar: ");
                String idTurno = sc.nextLine();
                System.out.println("Login empleado destino: ");
                String loginDestino = sc.nextLine();
                System.out.println("ID turno del destino: ");
                String idTurnoDestino = sc.nextLine();
                SolicitudCambioTurno sc2 = cafe.solicitarIntercambioTurno(login, idTurno, loginDestino, idTurnoDestino);
                System.out.println("Solicitud creada: " + sc2.getIdSolicitud());

            } else if (opcion == 5) {
                for (JuegoMesa j : cafe.consultarFavoritos(login)) {
                    System.out.println(j.toString());
                }

            } else if (opcion == 6) {
                System.out.println("ID juego: ");
                String idJuego = sc.nextLine();
                cafe.agregarJuegoFavoritoAUsuario(login, idJuego);
                System.out.println("Favorito agregado.");
            }
        }
    }


    private static void menuCliente(Cafe cafe, Scanner sc, String login) throws Exception {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENU CLIENTE ---");
            System.out.println("1. Ver catálogo juegos préstamo");
            System.out.println("2. Ver menú");
            System.out.println("3. Comprar juego");
            System.out.println("4. Ver favoritos");
            System.out.println("5. Agregar favorito");
            System.out.println("6. Sugerir plato");
            System.out.println("0. Salir");
            opcion = Integer.parseInt(sc.nextLine());

            if (opcion == 1) {
                for (JuegoMesaPrestamo j : cafe.consultarCatalogoJuegosPrestamo()) {
                    System.out.println(j.toString());
                }

            } else if (opcion == 2) {
                for (ProductoMenu p : cafe.consultarMenu()) {
                    System.out.println(p.toString());
                }

            } else if (opcion == 3) {
                System.out.println("ID juego venta: ");
                String idJuego = sc.nextLine();
                System.out.println("Cantidad: ");
                int cantidad = Integer.parseInt(sc.nextLine());
                System.out.println("Puntos a usar (0 si ninguno): ");
                int puntos = Integer.parseInt(sc.nextLine());
                System.out.println("Código descuento (enter si ninguno): ");
                String codigo = sc.nextLine();
                VentaJuego v = cafe.comprarJuegoConDescuento(login, idJuego, cantidad, puntos, codigo);
                System.out.println("Compra realizada | Total: " + v.getTotal() + " | Puntos generados: " + v.getPuntosGenerados());

            } else if (opcion == 4) {
                for (JuegoMesa j : cafe.consultarFavoritos(login)) {
                    System.out.println(j.toString());
                }

            } else if (opcion == 5) {
                System.out.println("ID juego: ");
                String idJuego = sc.nextLine();
                cafe.agregarJuegoFavoritoAUsuario(login, idJuego);
                System.out.println("Favorito agregado.");

            } else if (opcion == 6) {
                System.out.println("Nombre del plato: ");
                String nombre = sc.nextLine();
                System.out.println("Precio: ");
                double precio = Double.parseDouble(sc.nextLine());
                System.out.println("Es alcoholica (true/false): ");
                boolean alcoholica = Boolean.parseBoolean(sc.nextLine());
                System.out.println("Es caliente (true/false): ");
                boolean caliente = Boolean.parseBoolean(sc.nextLine());
                System.out.println("Alergenos (separados por coma): ");
                List<String> alergenos = Arrays.asList(sc.nextLine().split(","));
                System.out.println("Tipo (BEBIDA/PASTELERIA): ");
                String tipo = sc.nextLine();
                cafe.sugerirPlato(login, nombre, precio, alcoholica, caliente, alergenos, tipo);
                System.out.println("Sugerencia enviada.");
            }
        }
    }
}
