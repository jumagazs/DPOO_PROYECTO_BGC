package prestamos;

import juegos.JuegoMesaPrestamo;
import usuarios.*;

public class Prestamo {
    private String idPrestamo;
    private String fechaPrestamo;
    private String fechaDevolucion;
    private boolean fueExplicado;
    private JuegoMesaPrestamo juego;
    private Usuario usuario;

    public Prestamo(String idPrestamo, String fechaPrestamo, boolean fueExplicado,
                    JuegoMesaPrestamo juego, Usuario usuario) {
        this.idPrestamo = idPrestamo;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = null;
        this.fueExplicado = fueExplicado;
        this.juego = juego;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void registrarDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public boolean fueDevuelto() {
        return fechaDevolucion != null;
    }
}
