package modelo;

import java.util.Map;
import juegos.*;
import mesas.*;
import pedidos.*;
import prestamos.*;
import productos.*;
import sugerencias.*;
import usuarios.*;
import ventas.*;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import juegos.JuegoMesaVenta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;


public class GestorPersistencia {

    private String rutaArchivo;

    public GestorPersistencia(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    // ---- GUARDAR ----

    public void guardarTodo(Cafe cafe) throws Exception {
        PrintWriter pw = new PrintWriter(new FileWriter(this.rutaArchivo));

        pw.println("[administradores]");
        for (Usuario u : cafe.getUsuarios().values()) {
            if (u instanceof Administrador) {
                pw.println(u.toString());
            }
        }

        pw.println("[clientes]");
        for (Usuario u : cafe.getUsuarios().values()) {
            if (u instanceof Cliente) {
                pw.println(u.toString());
            }
        }

        pw.println("[meseros]");
        for (Usuario u : cafe.getUsuarios().values()) {
            if (u instanceof Mesero) {
                pw.println(u.toString());
            }
        }

        pw.println("[cocineros]");
        for (Usuario u : cafe.getUsuarios().values()) {
            if (u instanceof Cocinero) {
                pw.println(u.toString());
            }
        }

        pw.println("[juegosPrestamo]");
        for (JuegoMesaPrestamo j : cafe.getJuegosPrestamo().values()) {
            pw.println(j.toString());
        }

        pw.println("[juegosVenta]");
        for (JuegoMesaVenta j : cafe.getJuegosVenta().values()) {
            pw.println(j.toString());
        }

        pw.println("[mesas]");
        for (Mesa m : cafe.getMesas()) {
            pw.println(m.toString());
        }

        pw.println("[prestamos]");
        for (Prestamo p : cafe.getPrestamos()) {
            pw.println(p.toString());
        }

        pw.println("[ventas]");
        for (VentaJuego v : cafe.getVentas()) {
            pw.println(v.toString());
        }

        pw.println("[pedidos]");
        for (Pedido p : cafe.getPedidos()) {
            pw.println(p.toString());
        }
        
        pw.println("[turnos]");
        for (Usuario u : cafe.getUsuarios().values()) {
            if (u instanceof Empleado) {
                for (Turno t : ((Empleado) u).getTurnos()) {
                    pw.println(t.toString());
                }
            }
        }

        pw.close();
    }

    // Escrinir

    public void cargarTodo(Cafe cafe) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(this.rutaArchivo));
        String linea;
        String seccionActual = "";

        while ((linea = br.readLine()) != null) {
            if (linea.startsWith("[")) {
                seccionActual = linea.trim();
            } else if (!linea.trim().isEmpty()) {
                String[] p = linea.split("\\|");

                if (seccionActual.equals("[administradores]")) {
                    String login = p[0].split("\t")[1];
                    String contrasena = p[1].split("\t")[1];
                    cafe.getUsuarios().put(login, new Administrador(login, contrasena));

                } else if (seccionActual.equals("[clientes]")) {
                    String login = p[0].split("\t")[1];
                    String contrasena = p[1].split("\t")[1];
                    int puntos = Integer.parseInt(p[2].split("\t")[1]);
                    Cliente c = new Cliente(login, contrasena);
                    c.agregarPuntos(puntos);
                    cafe.getUsuarios().put(login, c);

                } else if (seccionActual.equals("[meseros]")) {
	                	String login = p[0].split("\t")[1];
	                	String contrasena = p[1].split("\t")[1];
	                	Mesero mesero = new Mesero(login, contrasena);
	                	if (p.length > 2 && !p[2].split("\t")[1].isEmpty()) {
	                	    for (String idJuego : p[2].split("\t")[1].split(",")) {
	                	        mesero.agregarJuegoConocido(idJuego);
	                	    }
	                	}
                    cafe.getUsuarios().put(login, mesero);

                } else if (seccionActual.equals("[cocineros]")) {
                    String login = p[0].split("\t")[1];
                    String contrasena = p[1].split("\t")[1];
                    cafe.getUsuarios().put(login, new Cocinero(login, contrasena));
                } else if (seccionActual.equals("[turnos]")) {
                    String idTurno = p[0].split("\t")[1];
                    LocalDateTime inicio = LocalDateTime.parse(p[1].split("\t")[1]);
                    LocalDateTime fin = LocalDateTime.parse(p[2].split("\t")[1]);
                    String dia = p[3].split("\t")[1];
                    String loginEmpleado = p[4].split("\t")[1];
                    Empleado emp = (Empleado) cafe.getUsuarios().get(loginEmpleado);
                    emp.asignarTurno(new Turno(idTurno, inicio, fin, dia, emp));
                } else if (seccionActual.equals("[juegosPrestamo]")) {
                    String id = p[0].split("\t")[1];
                    String nombre = p[1].split("\t")[1];
                    int anio = Integer.parseInt(p[2].split("\t")[1]);
                    String editor = p[3].split("\t")[1];
                    String categoria = p[4].split("\t")[1];
                    int min = Integer.parseInt(p[5].split("\t")[1]);
                    int max = Integer.parseInt(p[6].split("\t")[1]);
                    boolean dificil = Boolean.parseBoolean(p[7].split("\t")[1]);
                    boolean menores5 = Boolean.parseBoolean(p[8].split("\t")[1]);
                    boolean menores18 = Boolean.parseBoolean(p[9].split("\t")[1]);
                    boolean disponible = Boolean.parseBoolean(p[10].split("\t")[1]);
                    int veces = Integer.parseInt(p[11].split("\t")[1]);
                    String estado = p[12].split("\t")[1];
                    cafe.getJuegosPrestamo().put(id, new JuegoMesaPrestamo(nombre, anio, editor, categoria, min, max, dificil, menores5, menores18, id, disponible, veces, estado));

                } else if (seccionActual.equals("[juegosVenta]")) {
                    String id = p[0].split("\t")[1];
                    String nombre = p[1].split("\t")[1];
                    int anio = Integer.parseInt(p[2].split("\t")[1]);
                    String editor = p[3].split("\t")[1];
                    String categoria = p[4].split("\t")[1];
                    int min = Integer.parseInt(p[5].split("\t")[1]);
                    int max = Integer.parseInt(p[6].split("\t")[1]);
                    boolean dificil = Boolean.parseBoolean(p[7].split("\t")[1]);
                    boolean menores5 = Boolean.parseBoolean(p[8].split("\t")[1]);
                    boolean menores18 = Boolean.parseBoolean(p[9].split("\t")[1]);
                    double precio = Double.parseDouble(p[10].split("\t")[1]);
                    int stock = Integer.parseInt(p[11].split("\t")[1]);
                    double costo = Double.parseDouble(p[12].split("\t")[1]);
                    cafe.getJuegosVenta().put(id, new JuegoMesaVenta(nombre, anio, editor, categoria, min, max, dificil, menores5, menores18, id, precio, stock, costo));

                } else if (seccionActual.equals("[mesas]")) {
                    String id = p[0].split("\t")[1];
                    int capacidad = Integer.parseInt(p[1].split("\t")[1]);
                    cafe.agregarMesa(new Mesa(id, capacidad));

                } else if (seccionActual.equals("[prestamos]")) {
                    String id = p[0].split("\t")[1];
                    String fechaPrestamo = p[1].split("\t")[1];
                    String fechaDevolucion = p[2].split("\t")[1];
                    boolean explicado = Boolean.parseBoolean(p[3].split("\t")[1]);
                    String idJuego = p[4].split("\t")[1];
                    String loginUsuario = p[5].split("\t")[1];
                    JuegoMesaPrestamo juego = cafe.getJuegosPrestamo().get(idJuego);
                    Usuario usuario = cafe.getUsuarios().get(loginUsuario);
                    Prestamo prestamo = new Prestamo(id, fechaPrestamo, explicado, juego, usuario);
                    if (!fechaDevolucion.equals("null")) {
                        prestamo.registrarDevolucion(fechaDevolucion);
                    }
                    cafe.getPrestamos().add(prestamo);

                } else if (seccionActual.equals("[ventas]")) {
                    String id = p[0].split("\t")[1];
                    String fecha = p[1].split("\t")[1];
                    String loginComprador = p[2].split("\t")[1];
                    double subTotal = Double.parseDouble(p[3].split("\t")[1]);
                    double impuesto = Double.parseDouble(p[4].split("\t")[1]);
                    double total = Double.parseDouble(p[5].split("\t")[1]);
                    int puntos = Integer.parseInt(p[6].split("\t")[1]);
                    Usuario comprador = cafe.getUsuarios().get(loginComprador);
                    VentaJuego venta = new VentaJuego(id, fecha, comprador);
                    venta.setSubTotal(subTotal);
                    venta.setImpuesto(impuesto);
                    venta.setTotal(total);
                    venta.setPuntosGenerados(puntos);
                    cafe.getVentas().add(venta);

                } else if (seccionActual.equals("[pedidos]")) {
                    String id = p[0].split("\t")[1];
                    String fecha = p[1].split("\t")[1];
                    String idMesa = p[2].split("\t")[1];
                    double subtotal = Double.parseDouble(p[3].split("\t")[1]);
                    double impuesto = Double.parseDouble(p[4].split("\t")[1]);
                    double propina = Double.parseDouble(p[5].split("\t")[1]);
                    double total = Double.parseDouble(p[6].split("\t")[1]);
                    Mesa mesa = null;
                    for (Mesa m : cafe.getMesas()) {
                        if (m.getIdMesa().equals(idMesa)) {
                            mesa = m;
                            break;
                        }
                    }
                    Pedido pedido = new Pedido(id, fecha, mesa);
                    pedido.setSubtotal(subtotal);
                    pedido.setImpuestoConsumo(impuesto);
                    pedido.setPropina(propina);
                    pedido.setTotal(total);
                    cafe.getPedidos().add(pedido);
                }
            }
        }
        br.close();
    }
}