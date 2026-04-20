package usuarios;

import juegos.*;
import mesas.*;
import pedidos.*;
import prestamos.*;
import productos.*;
import sugerencias.*;
import usuarios.*;
import ventas.*;

public class Mesero extends Empleado {

	public Mesero(String login, String contrasena) {
		super(login, contrasena);
	}
	
	
    //requerimiento 18 //


    public Pedido registrarPedido(Mesa mesa, String idPedido, String fecha) throws Exception {
        if (!mesa.isOcupada())
            throw new Exception("La mesa debe estar ocupada.");
        return new Pedido(idPedido, fecha, mesa);
    	
    }

}
