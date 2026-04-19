package ventas;

import java.util.ArrayList;
import java.util.List;

import usuarios.*;

public class VentaJuego {
    private String idVenta;
    private String fecha;
    private double impuesto;
    private double propina;
    private double subTotal;
    private double total;
    private Usuario comprador;
    private int puntosGenerados;
    private int puntosUsados;
    private boolean descuentoAplicado;
    private List<DetalleVenta> detalles;

    public VentaJuego(String idVenta, String fecha, Usuario comprador) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.comprador = comprador;
        this.impuesto = 0;
        this.propina = 0;
        this.subTotal = 0;
        this.total = 0;
        this.puntosGenerados = 0;
        this.puntosUsados = 0;
        this.descuentoAplicado = false;
        this.detalles = new ArrayList<>();
    }

    public String getIdVenta() {
        return idVenta;
    }

    public String getFecha() {
        return fecha;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public double getPropina() {
        return propina;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public double getTotal() {
        return total;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public int getPuntosGenerados() {
        return puntosGenerados;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void agregarDetalle(DetalleVenta detalle) {
        detalles.add(detalle);
        subTotal += detalle.getSubtotal();
        total = subTotal + impuesto + propina;
    }

    public void calcularValores(double porcentajeImpuesto) {
        impuesto = subTotal * porcentajeImpuesto;
        total = subTotal + impuesto + propina;
        puntosGenerados = (int) (total / 1000);
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
