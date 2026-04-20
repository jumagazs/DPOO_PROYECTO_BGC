package pedidos;

import java.util.ArrayList;
import java.util.List;
import mesas.Mesa;

public class Pedido {
    private String idPedido;
    private String fecha;
    private double impuestoConsumo;
    private double propina;
    private double subtotal;
    private double total;
    private String estado;
    private Mesa mesa;
    private List<DetallePedido> detalles;

    public Pedido(String idPedido, String fecha, Mesa mesa) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.mesa = mesa;
        this.impuestoConsumo = 0;
        this.propina = 0;
        this.subtotal = 0;
        this.total = 0;
        this.detalles = new ArrayList<>();
        this.estado = "EN PREPARACIÓN";
    }

    public String getIdPedido() {
        return idPedido;
    }

    public String getFecha() {
        return fecha;
    }

    public double getImpuestoConsumo() {
        return impuestoConsumo;
    }

    public double getPropina() {
        return propina;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTotal() {
        return total;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void agregarDetalle(DetallePedido detalle) {
        detalles.add(detalle);
        subtotal += detalle.getSubtotal();
        total = subtotal + impuestoConsumo + propina;
    }

    public void calcularValores(double porcentajeImpuesto, double porcentajePropina) {
        impuestoConsumo = subtotal * porcentajeImpuesto;
        propina = subtotal * porcentajePropina;
        total = subtotal + impuestoConsumo + propina;
    }
    
    public void prepararPedido() {
    		this.estado = "PREPARADO";
    }
}
