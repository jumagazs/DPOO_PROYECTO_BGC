package prestamos;

import java.time.LocalDateTime;

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
    
    @Override
    public String toString() {
        return "id\t" + this.idPrestamo + "|fechaPrestamo\t" + this.fechaPrestamo + "|fechaDevolucion\t" + this.fechaDevolucion + "|explicado\t" + this.fueExplicado + "|juego\t" + this.juego.getIdJuegoPrestamo() + "|usuario\t" + this.usuario.getLogin();
    }
    
    public void devolver() throws Exception {
        if (this.fueDevuelto()) {
            throw new Exception("Ese préstamo ya fue devuelto.");
        }
        this.fechaDevolucion = LocalDateTime.now().toString();
        this.juego.setDisponible(true);
        this.juego.aumentarVecesPrestado();
    }
}
