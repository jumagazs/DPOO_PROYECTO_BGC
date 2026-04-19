package juegos;

public class JuegoMesaVenta extends JuegoMesa {
    private String idJuegoVenta;
    private double precioVenta;
    private int cantidadStock;
    private double costoBase;

    public JuegoMesaVenta(String nombre, int anioPublicacion, String editorJuego, String categoria, int minJugadores, int maxJugadores, boolean esDificil, boolean jueganMenores5, boolean jueganMenores18, String idJuegoVenta, double precioVenta, int cantidadStock, double costoBase) {
        super(nombre, anioPublicacion, editorJuego, categoria, minJugadores, maxJugadores, esDificil, jueganMenores5, jueganMenores18,idJuegoVenta);
        this.idJuegoVenta = idJuegoVenta;
        this.precioVenta = precioVenta;
        this.cantidadStock = cantidadStock;
        this.costoBase = costoBase;
    }

    public String getIdJuegoVenta() {
        return idJuegoVenta;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public double getCostoBase() {
        return costoBase;
    }

    public void reducirStock(int cantidad) throws Exception {
        if (cantidad > cantidadStock) {
            throw new Exception("No hay stock suficiente del juego.");
        }
        cantidadStock -= cantidad;
    }
}
