package modelo;

import java.util.HashMap;

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
import java.time.LocalDateTime;


public class Cafe {
    private Map<String, Usuario> usuarios;
    private Map<String, JuegoMesaPrestamo> juegosPrestamo;
    private Map<String, ProductoMenu> menu;
    private Map<String, Mesa> mesas;
    private List<Prestamo> prestamos;
    private int consecutivoPrestamos;
    private List<Pedido> pedidos;
    private int consecutivoPedidos;
    private Map<String, JuegoMesaVenta> juegosVenta;
    private List<VentaJuego> ventas;
    private int consecutivoVentas;
    private List<SolicitudCambioTurno> solicitudesCambioTurno;
    private int consecutivoSolicitudes;
    private List<SugerenciaPlato> sugerencias;
    private int consecutivoSugerencias;
    

    public Cafe() {
        this.usuarios = new HashMap<>();
        this.juegosPrestamo = new HashMap<>();
        this.menu = new HashMap<>();
        this.mesas = new HashMap<>();
        this.prestamos = new ArrayList<>();
        this.consecutivoPrestamos = 1;
        this.pedidos = new ArrayList<>();
        this.consecutivoPedidos = 1;
        this.juegosVenta = new HashMap<>();
        this.ventas = new ArrayList<>();
        this.consecutivoVentas = 1;
        this.solicitudesCambioTurno = new ArrayList<>();
        this.consecutivoSolicitudes = 1;
        this.sugerencias = new ArrayList<>();
        this.consecutivoSugerencias = 1;
    }

    public Cliente registrarCliente(String login, String contrasena) throws Exception {
        if (usuarios.containsKey(login)) {
            throw new Exception("Ya existe un usuario registrado con ese login.");
        }

        Cliente nuevoCliente = new Cliente(login, contrasena);
        usuarios.put(login, nuevoCliente);
        return nuevoCliente;
    }

    public Usuario iniciarSesion(String login, String contrasena) throws Exception {
        if (!usuarios.containsKey(login)) {
            throw new Exception("El usuario no existe.");
        }

        Usuario usuario = usuarios.get(login);

        if (!usuario.getContrasena().equals(contrasena)) {
            throw new Exception("La contraseña es incorrecta.");
        }

        return usuario;
    }
    
    public void agregarJuegoPrestamo(JuegoMesaPrestamo juego) {
        juegosPrestamo.put(juego.getIdJuegoPrestamo(), juego);
    }

    public Collection<JuegoMesaPrestamo> consultarCatalogoJuegosPrestamo() {
        return juegosPrestamo.values();
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }
    
    public void agregarProductoMenu(ProductoMenu producto) {
        menu.put(producto.getIdProducto(), producto);
    }

    public Collection<ProductoMenu> consultarMenu() {
        return menu.values();
    }
    
    public void agregarMesa(Mesa mesa) {
        mesas.put(mesa.getIdMesa(), mesa);
    }

    public Collection<Mesa> getMesas() {
        return mesas.values();
    }

    public Mesa asignarMesaACliente(Cliente cliente, int cantidadPersonas, boolean hayJovenes, boolean hayNinos) throws Exception {
        Mesa mejorMesa = null;

        for (Mesa mesa : mesas.values()) {
            if (!mesa.isOcupada() && mesa.getCapacidad() >= cantidadPersonas) {
                if (mejorMesa == null || mesa.getCapacidad() < mejorMesa.getCapacidad()) {
                    mejorMesa = mesa;
                }
            }
        }

        if (mejorMesa == null) {
            throw new Exception("No hay mesas disponibles para esa cantidad de personas.");
        }

        mejorMesa.asignarMesa(cantidadPersonas,hayJovenes,hayNinos);
        return mejorMesa;
    }
    
    public Prestamo solicitarPrestamoJuego(Cliente cliente, Mesa mesa, String idJuegoPrestamo, boolean fueExplicado) throws Exception {
        if (mesa == null || !mesa.isOcupada()) {
            throw new Exception("El cliente debe tener una mesa asignada para pedir un préstamo.");
        }

        if (!juegosPrestamo.containsKey(idJuegoPrestamo)) {
            throw new Exception("El juego solicitado no existe.");
        }

        JuegoMesaPrestamo juego = juegosPrestamo.get(idJuegoPrestamo);

        if (!juego.isDisponible()) {
            throw new Exception("El juego no está disponible para préstamo.");
        }

        String idPrestamo = "PR" + consecutivoPrestamos;
        consecutivoPrestamos++;

        Prestamo nuevoPrestamo = new Prestamo(idPrestamo, "2026-04-17", fueExplicado, juego, cliente);
        prestamos.add(nuevoPrestamo);

        juego.setDisponible(false);

        return nuevoPrestamo;
    }
    
    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
    
    public void devolverJuego(String idPrestamo, String fechaDevolucion) throws Exception {
        Prestamo prestamoEncontrado = null;

        for (Prestamo prestamo : prestamos) {
            if (prestamo.getIdPrestamo().equals(idPrestamo)) {
                prestamoEncontrado = prestamo;
                break;
            }
        }

        if (prestamoEncontrado == null) {
            throw new Exception("No existe un préstamo con ese id.");
        }

        if (prestamoEncontrado.fueDevuelto()) {
            throw new Exception("Ese préstamo ya fue devuelto.");
        }

        prestamoEncontrado.registrarDevolucion(fechaDevolucion);
        prestamoEncontrado.getJuego().setDisponible(true);
        prestamoEncontrado.getJuego().aumentarVecesPrestado();
    }
    
    public Pedido crearPedido(Mesa mesa) throws Exception {
        if (mesa == null || !mesa.isOcupada()) {
            throw new Exception("La mesa debe estar ocupada para realizar un pedido.");
        }

        String idPedido = "PED" + consecutivoPedidos;
        consecutivoPedidos++;

        Pedido nuevoPedido = new Pedido(idPedido, "2026-04-17", mesa);
        pedidos.add(nuevoPedido);

        return nuevoPedido;
    }
    
    public void agregarProductoAPedido(Pedido pedido, String idProducto, int cantidad) throws Exception {
        if (pedido == null) {
            throw new Exception("El pedido no existe.");
        }

        if (!menu.containsKey(idProducto)) {
            throw new Exception("El producto no existe en el menú.");
        }

        if (cantidad <= 0) {
            throw new Exception("La cantidad debe ser mayor que cero.");
        }

        ProductoMenu producto = menu.get(idProducto);

        if (!producto.isDisponible()) {
            throw new Exception("El producto no está disponible.");
        }

        DetallePedido detalle = new DetallePedido(cantidad, producto);
        pedido.agregarDetalle(detalle);
    }
    
    public void confirmarPedido(Pedido pedido) throws Exception {
        if (pedido == null) {
            throw new Exception("El pedido no existe.");
        }

        pedido.calcularValores(0.08, 0.10);
    }
    
    public void agregarJuegoVenta(JuegoMesaVenta juego) {
        juegosVenta.put(juego.getIdJuegoVenta(), juego);
    }
    
    public VentaJuego comprarJuegoMesa(Cliente cliente, String idJuegoVenta, int cantidad) throws Exception {
        if (cliente == null) {
            throw new Exception("El cliente no existe.");
        }

        if (!juegosVenta.containsKey(idJuegoVenta)) {
            throw new Exception("El juego no existe en el inventario de venta.");
        }

        if (cantidad <= 0) {
            throw new Exception("La cantidad debe ser mayor que cero.");
        }

        JuegoMesaVenta juego = juegosVenta.get(idJuegoVenta);
        juego.reducirStock(cantidad);

        String idVenta = "V" + consecutivoVentas;
        consecutivoVentas++;

        VentaJuego venta = new VentaJuego(idVenta, "2026-04-17", cliente);
        DetalleVenta detalle = new DetalleVenta(cantidad, juego);

        venta.agregarDetalle(detalle);
        venta.calcularValores(0.19,0,0);

        cliente.agregarPuntos(venta.getPuntosGenerados());
        ventas.add(venta);

        return venta;
    }
    //requermiento 11 //
    public void agregarJuegoFavoritoAUsuario(String login, String idJuego) throws Exception{
        if (!usuarios.containsKey(login)){
            throw new Exception(" El usuario no existe ");

        }
        if (!juegosPrestamo.containsKey(idJuego)){
            throw new Exception(" El juego no existe ");

        }
        Usuario usuario = usuarios.get(login);
        usuario.agregarJuegoFavorito(juegosPrestamo.get(idJuego));
    }
    public List<JuegoMesa> consultarFavoritos(String login) throws Exception {
        if (!usuarios.containsKey(login)) {
            throw new Exception("El usuario no existe.");
        }

        return usuarios.get(login).getJuegosFavoritos();
    }

    //requerimiento 12 //
    public Turno consultarTurnoEmpleado(String login) throws Exception {

        if (!usuarios.containsKey(login)) {
            throw new Exception("El usuario no existe.");
        }

        Usuario usuario = usuarios.get(login);

        if (!(usuario instanceof Empleado)) {
            throw new Exception("El usuario no es un empleado.");
        }

        Empleado empleado = (Empleado) usuario;

        return empleado.consultarTurno();
    }

    //requermiento 13 //
    
    public SolicitudCambioTurno solicitarCambioTurnoGeneral(String login) throws Exception {

        if (!usuarios.containsKey(login) || !(usuarios.get(login) instanceof Empleado)) {
            throw new Exception("El usuario debe ser un empleado.");
        }   

        Empleado emp = (Empleado) usuarios.get(login);

        String id = "SC" + consecutivoSolicitudes++;

        SolicitudCambioTurno sc = emp.solicitarCambioTurnoGeneral(id,"GENERAL");

        solicitudesCambioTurno.add(sc);
        return sc;
    }

    //metodos requerimiento 14 //
    public VentaJuego comprarJuegoConDescuento(String login, String idJuego, int cantidad, int puntosAUsar, String codigoDescuento) throws Exception {

        if (!usuarios.containsKey(login)) {
            throw new Exception("El usuario no existe.");
        }

        Usuario usuario = usuarios.get(login);

        if (!juegosVenta.containsKey(idJuego)) {
            throw new Exception("El juego no existe en el inventario.");
        }

        if (cantidad <= 0) {
            throw new Exception("La cantidad debe ser mayor a 0.");
        }

        JuegoMesaVenta juego = juegosVenta.get(idJuego);

        juego.reducirStock(cantidad);

        String fechaActual = LocalDateTime.now().toString();

        String idVenta = "V" + consecutivoVentas++;
        VentaJuego venta = new VentaJuego(idVenta, fechaActual, usuario);

        DetalleVenta detalle = new DetalleVenta(cantidad, juego);
        venta.agregarDetalle(detalle);

        double descuento = 0;


        if (usuario instanceof Cliente) {

            Cliente cliente = (Cliente) usuario;
            	
            if(codigoDescuento != null && !codigoDescuento.isEmpty()) {
	            	boolean codigoValido = false;
	            	for (Usuario u : this.usuarios.values()) {
	            		if (u instanceof Empleado && ((Empleado) u).getCodigoDescuento().equals(codigoDescuento)) {
	            			codigoValido = true;
	            		}
	            		
            	}
            	if (!codigoValido)
                    throw new Exception("El código de descuento no es válido.");
                if (puntosAUsar > 0)
                    throw new Exception("El descuento y los puntos no son acumulables.");
                descuento = 0.1;
            	
            } else if (puntosAUsar > 0) {
            		cliente.usarPuntos(puntosAUsar);
            }

        }

        else if (usuario instanceof Empleado) {

            Empleado emp = (Empleado) usuario;

            descuento = emp.getDescuentoEMP(); 
            if (puntosAUsar > 0) {
            	throw new Exception("Los empleados no pueden usar puntos.");
            }
        }
        
        venta.calcularValores(0.19, descuento, puntosAUsar);
        if (usuario instanceof Cliente) { 
        	((Cliente) usuario).agregarPuntos(venta.getPuntosGenerados());
        }
        ventas.add(venta);
        
        return venta;
    }

        //requerimiento 15

    public Prestamo solicitarPrestamoJuegoFlexible(String login, String idJuego, boolean fueExplicado) throws Exception {

        if (!usuarios.containsKey(login)) {
            throw new Exception("El usuario no existe.");
        }

        Usuario usuario = usuarios.get(login);

        if (!juegosPrestamo.containsKey(idJuego)) {
            throw new Exception("El juego no existe.");
        }

        JuegoMesaPrestamo juego = juegosPrestamo.get(idJuego);

        if (!juego.isDisponible()) {
            throw new Exception("El juego no está disponible.");
        }

        if (usuario instanceof Cliente) {

            boolean tieneMesa = false;

            for (Mesa mesa : mesas.values()) {
                if (mesa.isOcupada()) {
                    tieneMesa = true;
                    break;
                }
            }

            if (!tieneMesa) {
                throw new Exception("El cliente debe tener una mesa asignada.");
            }
        }

        else if (usuario instanceof Empleado) {

            Empleado emp = (Empleado) usuario;

            if (emp.consultarTurno() != null) {
                throw new Exception("El empleado no puede solicitar juegos durante su turno.");
            }
        }

        String fechaActual = LocalDateTime.now().toString();

        String idPrestamo = "PR" + consecutivoPrestamos++;

        Prestamo prestamo = new Prestamo(
            idPrestamo,
            fechaActual,
            fueExplicado,
            juego,
            usuario
        );

        prestamos.add(prestamo);

        juego.setDisponible(false);

        return prestamo;
    }

    //requerimiento 17 //

    public SugerenciaPlato sugerirPlato(String loginEmpleado, String nombrePropuesto) throws Exception {

        if (!usuarios.containsKey(loginEmpleado)) {
            throw new Exception("El usuario no existe.");
        }

        Usuario usuario = usuarios.get(loginEmpleado);

        if (!(usuario instanceof Empleado)) {
            throw new Exception("Solo los empleados pueden sugerir platos.");
        }

        if (nombrePropuesto == null || nombrePropuesto.trim().isEmpty()) {
            throw new Exception("El nombre del plato no puede estar vacío.");
        }

        Empleado empleado = (Empleado) usuario;

        String idSugerencia = "SP" + consecutivoSugerencias++;

        LocalDateTime fechaActual = LocalDateTime.now();

        String estadoInicial = "PENDIENTE";

        SugerenciaPlato sugerencia = new SugerenciaPlato(
            idSugerencia,
            nombrePropuesto,
            fechaActual,
            estadoInicial,
            empleado
        );

        sugerencias.add(sugerencia);

        return sugerencia;
    }

    	//Requerimiento 18 
    
    public Pedido registrarPedidoMesero(String login, String idMesa) throws Exception {
    	
        if (!usuarios.containsKey(login))
            throw new Exception("El usuario no existe.");
        if (!(usuarios.get(login) instanceof Mesero))
            throw new Exception("El usuario no es un mesero.");
        if (!mesas.containsKey(idMesa))
            throw new Exception("La mesa no existe.");

        Mesero mesero = (Mesero) usuarios.get(login);
        Mesa mesa = mesas.get(idMesa);
        String idPedido = "PED" + consecutivoPedidos++;
        Pedido pedido = mesero.registrarPedido(mesa, idPedido, LocalDateTime.now().toString());
        pedidos.add(pedido);
        return pedido;
    }
    
    //REquerimiento 19 prestamo de empleado a cliente
    
    public Prestamo prestamoDeJuegos(String idJuego, String idMesero, String idMesa, String idCliente) throws Exception {
        if (!usuarios.containsKey(idMesero))
            throw new Exception("El Meseroo no existe.");
        if (!usuarios.containsKey(idCliente))
            throw new Exception("El Cliente no existe.");
        if (!(usuarios.get(idMesero) instanceof Mesero))
            throw new Exception("El usuario no es un mesero.");
        if (!(usuarios.get(idCliente) instanceof Cliente))
            throw new Exception("El usuario no es un cliente.");
        if (!mesas.containsKey(idMesa))
            throw new Exception("La mesa no existe.");	
	    	if(!this.juegosPrestamo.containsKey(idJuego)) {
	    			throw new Exception("No existe el juego para prestamos");
	    		}
	    	JuegoMesaPrestamo juegoMesaPrestamo = this.juegosPrestamo.get(idJuego);
	    	Mesa mesa = this.mesas.get(idMesa);
	    	Usuario mesero = this.usuarios.get(idMesero);
	    	Usuario cliente = this.usuarios.get(idCliente);
	    	
	    	
	    	if(!juegoMesaPrestamo.isDisponible()) {
	    		throw new Exception("No está disponible el juego");
	    	}
	    	if(juegoMesaPrestamo.getMinJugadores() > mesa.getPersonasActuales() || juegoMesaPrestamo.getMaxJugadores() < mesa.getPersonasActuales()) {
	    		throw new Exception("El número de personas no es adecuado para jugar");
	    	}
	    	
	    	if (juegoMesaPrestamo.isEsDificil() && !((Mesero) mesero).puedeExplicar(idJuego)) {
	    	    System.out.println("Advertencia: ningún mesero puede explicar este juego.");
	    	}
	    	if( mesa.isHayJovenes() && !juegoMesaPrestamo.isJueganMenores18()) {
	    		throw new Exception("No pueden jugarlo menores de 18");
	    	}
	    	if( mesa.isHayNinos() && !juegoMesaPrestamo.isJueganMenores5()) {
	    		throw new Exception("No pueden jugarlo menores de 5");
	    	}
	    	
	    	String idPrestamo = "PR" + consecutivoPrestamos++;
	    	boolean fueExplicado = juegoMesaPrestamo.isEsDificil() && ((Mesero) mesero).puedeExplicar(idJuego);
	    	Prestamo prestamo = ((Mesero) mesero).realizarPrestamo(juegoMesaPrestamo, mesa, idPrestamo, LocalDateTime.now().toString(), fueExplicado,(Cliente) cliente);
	    	juegoMesaPrestamo.setDisponible(false);
	    	prestamos.add(prestamo);
    		
	    	return prestamo;
    	
    }
    
    
    
}

