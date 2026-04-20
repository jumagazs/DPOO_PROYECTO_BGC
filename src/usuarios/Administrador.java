package usuarios;

import java.time.LocalDateTime;
import java.util.List;

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
	
	public void aprobarCambioTurno(SolicitudCambioTurno solicitud, int meseros, int cocineros) throws Exception {
	    if (solicitud.getSolicitante() instanceof Mesero && meseros < 3)
	        throw new Exception("No se puede aprobar, quedarían menos de 2 meseros.");
	    if (solicitud.getSolicitante() instanceof Cocinero && cocineros < 2)
	        throw new Exception("No se puede aprobar, quedaría menos de 1 cocinero.");
	    if (solicitud.getEmpleadoDestino() != null) {
	    	Turno turnoTemp = solicitud.getSolicitante().getTurno();
	    	Turno turnoDestino = solicitud.getEmpleadoDestino().getTurno();

	    	turnoDestino.setEmpleado(solicitud.getSolicitante());
	    	turnoTemp.setEmpleado(solicitud.getEmpleadoDestino());

	    	solicitud.getSolicitante().asignarTurno(turnoDestino);
	    	solicitud.getEmpleadoDestino().asignarTurno(turnoTemp);
	    } else {
	        Turno turnoActual = solicitud.getSolicitante().getTurno();
	        LocalDateTime siguienteMesInicio = turnoActual.getHoraInicio().plusDays(1);
	        LocalDateTime siguienteMesFin = turnoActual.getHoraFin().plusDays(1);
	        
	        Turno nuevoTurno = new Turno(siguienteMesInicio , siguienteMesFin ,  turnoActual.getDia(), turnoActual.getEmpleado());
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
	
	
	
	
}
