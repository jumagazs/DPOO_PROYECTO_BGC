package usuarios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import juegos.*;
import mesas.*;
import pedidos.*;
import prestamos.*;
import productos.*;
import sugerencias.*;
import usuarios.*;
import ventas.*;


public class Administrador extends Usuario {


	public Administrador(String login, String contrasena) {
		super(login, contrasena);
	}
	
	public JuegoMesaPrestamo agregarJuegoPrestamo(String nombre, int anioPublicacion, String editorJuego, String categoria, int minJugadores, int maxJugadores, boolean esDificil, boolean jueganMenores5, boolean jueganMenores18,  String idJuegoPrestamo, boolean disponible, int vecesPrestado, String estado) {
		JuegoMesaPrestamo juegoMesaPrestamo = new JuegoMesaPrestamo( nombre, anioPublicacion, editorJuego, categoria, minJugadores, maxJugadores,  esDificil, jueganMenores5,  jueganMenores18,   idJuegoPrestamo,  disponible,  vecesPrestado,  estado);
		return juegoMesaPrestamo;
	}
	
	public JuegoMesaVenta agregarJuegoVenta(String nombre, int anioPublicacion, String editorJuego, String categoria, int minJugadores, int maxJugadores, boolean esDificil, boolean jueganMenores5, boolean jueganMenores18 , String idJuegoVenta, double precioVenta, int cantidadStock, double costoBase) throws Exception{
		JuegoMesaVenta juegoMesaVenta = new JuegoMesaVenta( nombre, anioPublicacion, editorJuego, categoria, minJugadores, maxJugadores, esDificil, jueganMenores5, jueganMenores18, idJuegoVenta,precioVenta,cantidadStock, costoBase);
		return juegoMesaVenta;
	}
	
	public Mesero registrarMesero(String login,String contraseña) {
		Mesero mesero = new Mesero(login,contraseña);
		return mesero;
	}
	
	public Cocinero registrarCocinero(String login,String contraseña) {
		Cocinero cocinero = new Cocinero(login,contraseña);
		return cocinero;
	}
	
	public Bebida registrarBebida(String idProducto, String nombre, double precio, boolean disponible, boolean alcoholica, boolean caliente) {
		Bebida bebida = new Bebida( idProducto,  nombre,  precio,  disponible,  alcoholica,  caliente);
		return bebida;
	}
	
	public Pasteleria registrarPasteleria(String idProducto, String nombre, double precio, boolean disponible, List<String> alergenos) {
		Pasteleria pasteleria = new Pasteleria( idProducto,  nombre,  precio,  disponible,  alergenos);
		return pasteleria;
	}
	
	public void asignarTurno(Empleado empleado, Turno turno) {
	    empleado.asignarTurno(turno);
	}
	
	public void aprobarCambioTurno(SolicitudCambioTurno solicitud, int meseros, int cocineros, int consecutivo) throws Exception {
	    if (solicitud.getEmpleadoDestino() != null) {
	        Turno turnoSolicitante = solicitud.getTurnoSolicitante();
	        Turno turnoDestino = solicitud.getTurnoDestino();
	        turnoDestino.setEmpleado(solicitud.getSolicitante());
	        turnoSolicitante.setEmpleado(solicitud.getEmpleadoDestino());
	        solicitud.getSolicitante().eliminarTurno(turnoSolicitante.getIdTurno());
	        solicitud.getSolicitante().asignarTurno(turnoDestino);
	        solicitud.getEmpleadoDestino().eliminarTurno(turnoDestino.getIdTurno());
	        solicitud.getEmpleadoDestino().asignarTurno(turnoSolicitante);
	    } else {
	        if (solicitud.getSolicitante() instanceof Mesero && meseros < 3) {
	            throw new Exception("No se puede aprobar, quedarían menos de 2 meseros.");
	        }
	        if (solicitud.getSolicitante() instanceof Cocinero && cocineros < 2) {
	            throw new Exception("No se puede aprobar, quedaría menos de 1 cocinero.");
	        }
	        Turno turnoActual = solicitud.getTurnoSolicitante();
	        String nuevoId = "T" + consecutivo;
	        Turno nuevoTurno = new Turno(nuevoId, turnoActual.getHoraInicio().plusMonths(1), turnoActual.getHoraFin().plusMonths(1), turnoActual.getDia(), solicitud.getSolicitante());
	        solicitud.getSolicitante().eliminarTurno(turnoActual.getIdTurno());
	        solicitud.getSolicitante().asignarTurno(nuevoTurno);
	    }
	    solicitud.aprobar();
	}

	        


	public void rechazarCambioTurno(SolicitudCambioTurno solicitud) {
	    solicitud.rechazar();
	}
	

	public ProductoMenu  aprobarSugerenciaPlato(SugerenciaPlato sugerenciaPlato,String idSugerencia) {
		sugerenciaPlato.setEstadoSugerencia("APROBADA");
	    if (sugerenciaPlato.getTipo().equals("BEBIDA"))
	        return new Bebida(idSugerencia, sugerenciaPlato.getNombrePropuesto(), sugerenciaPlato.getPrecio(), 
	                true, sugerenciaPlato.isEsAlcoholica(), sugerenciaPlato.isEsCaliente());
	    else
	        return new Pasteleria(idSugerencia, sugerenciaPlato.getNombrePropuesto(), sugerenciaPlato.getPrecio(), 
	                true, sugerenciaPlato.getAlergenos());
	}
	
	public void rechazarSugerenciaPlato(SugerenciaPlato sugerencia) {
	    sugerencia.setEstadoSugerencia("RECHAZADA");
	}
	
	public void cambiarEstadoJuego(JuegoMesaPrestamo juego, String nuevoEstado) {
	    juego.setEstado(nuevoEstado);
	}

	public void eliminarJuegoPrestamo(Map<String, JuegoMesaPrestamo> juegosPrestamo, String idJuego) throws Exception {
	    if (!juegosPrestamo.containsKey(idJuego)) {
	        throw new Exception("El juego no existe en el inventario de préstamo.");
	    }
	    juegosPrestamo.remove(idJuego);
	}

	public void eliminarJuegoVenta(Map<String, JuegoMesaVenta> juegosVenta, String idJuego) throws Exception {
	    if (!juegosVenta.containsKey(idJuego)) {
	        throw new Exception("El juego no existe en el inventario de venta.");
	    }
	    juegosVenta.remove(idJuego);
	}
	
	public void consultarInforme(List<VentaJuego> ventas, List<Pedido> pedidos, String granularidad) {
	    LocalDateTime ahora = LocalDateTime.now();
	    double totalJuegos = 0, impuestosJuegos = 0;
	    double totalComida = 0, impuestosComida = 0, propinasComida = 0;
	    for (VentaJuego v : ventas) {
	        if (dentroDeGranularidad(LocalDateTime.parse(v.getFecha()), ahora, granularidad)) {
	            totalJuegos += v.getTotal();
	            impuestosJuegos += v.getImpuesto();
	        }
	    }
	    for (Pedido p : pedidos) {
	        if (dentroDeGranularidad(LocalDateTime.parse(p.getFecha()), ahora, granularidad)) {
	            totalComida += p.getTotal();
	            impuestosComida += p.getImpuestoConsumo();
	            propinasComida += p.getPropina();
	        }
	    }
	    System.out.println("Informe " + granularidad);
	    System.out.println("Ventas juegos | Total: " + totalJuegos + " | Impuestos: " + impuestosJuegos);
	    System.out.println("Ventas comida | Total: " + totalComida + " | Impuestos: " + impuestosComida + " | Propinas: " + propinasComida);
	}

	private boolean dentroDeGranularidad(LocalDateTime fecha, LocalDateTime ahora, String granularidad) {
	    if (granularidad.equals("diaria")) {
	        return fecha.toLocalDate().equals(ahora.toLocalDate());
	    } else if (granularidad.equals("semanal")) {
	        return fecha.isAfter(ahora.minusDays(7));
	    } else if (granularidad.equals("mensual")) {
	        return fecha.getMonth() == ahora.getMonth() && fecha.getYear() == ahora.getYear();
	    }
	    return false;
	}
	
	public void agregarJuegoDificil(Mesero mesero, String idJuego) {
	    mesero.agregarJuegoDificil(idJuego);
	}
	
	@Override
	public String toString() {
	    return "login\t" + this.login + "|contrasena\t" + this.contrasena;
	}
	
	
	
	
}
