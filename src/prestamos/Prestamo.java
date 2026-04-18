package prestamos;

import juegos.JuegoMesaPrestamo;
import usuarios.Cliente;

public class Prestamo {
    private String idPrestamo;
    private String fechaPrestamo;
    private String fechaDevolucion;
    private boolean fueExplicado;
    private JuegoMesaPrestamo juego;
    private Cliente cliente;

    public Prestamo(String idPrestamo, String fechaPrestamo, boolean fueExplicado,
                    JuegoMesaPrestamo juego, Cliente cliente) {
        this.idPrestamo = idPrestamo;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = null;
        this.fueExplicado = fueExplicado;
        this.juego = juego;
        this.cliente = cliente;
    }

    public String getIdPrestamo() {
        return idPrestamo;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public boolean isFueExplicado() {
        return fueExplicado;
    }

    public JuegoMesaPrestamo getJuego() {
        return juego;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void registrarDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public boolean fueDevuelto() {
        return fechaDevolucion != null;
    }
}
