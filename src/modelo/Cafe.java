package modelo;

import java.util.HashMap;
import java.util.Map;
import juegos.JuegoMesaPrestamo;
import usuarios.Cliente;
import usuarios.Usuario;
import java.util.Collection;
import productos.ProductoMenu;
import mesas.Mesa;
import prestamos.Prestamo;
import java.util.ArrayList;
import java.util.List;
import pedidos.Pedido;
import pedidos.DetallePedido;
import juegos.JuegoMesaVenta;
import ventas.DetalleVenta;
import ventas.VentaJuego;

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

    public Mesa asignarMesaACliente(Cliente cliente, int cantidadPersonas) throws Exception {
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

        mejorMesa.asignarMesa(cantidadPersonas);
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
        venta.calcularValores(0.19);

        cliente.agregarPuntos(venta.getPuntosGenerados());
        ventas.add(venta);

        return venta;
    }
    public void agregarJuegoFavoritoAUsuario(String login, String idJuego) throws Exception{
        if (!usuarios.containsKey(login)){
            throw new Exception(" El usuario no existe ");

        }
        if (!juegosPrestamo.containsKey(idJuego)){
            throw new Exception(" El juego no existe ");

        }
        Usuario usuario = usuarios.get(login);
        Usuario.agregarJuegoFavorito(juegosPrestamo.get(idJuego))
    }
    public List<JuegoMesa> consultarFavoritos(String login) throws Exception {
        if (!usuarios.containsKey(login)) {
            throw new Exception("El usuario no existe.");
        }

        return usuarios.get(login).getJuegosFavoritos();
    }
}
