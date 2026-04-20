package usuarios;

import java.util.Collection;

public class Cliente extends Usuario {
		private int puntosFidelidad;
		private String codigoDescuento;
		
	public Cliente(String login, String contrasena) {
			super(login, contrasena);
			this.puntosFidelidad = 0;

		}
	
	public int getPuntosFidelidad() {
        return puntosFidelidad;
    }

    public void agregarPuntos(int puntos) {
        this.puntosFidelidad += puntos;
    }

    public void usarPuntos(int puntos) throws Exception {
        if (puntos > puntosFidelidad) {
            throw new Exception("El cliente no tiene suficientes puntos de fidelidad.");
        }
        this.puntosFidelidad -= puntos;
    }
    
    public void aplicarCodigoDescuento(String codigo) throws Exception {
        if (codigo == null || codigo.isEmpty())
            throw new Exception("Código inválido.");
        this.codigoDescuento = codigo;
    }

	public String getCodigoDescuento() {
		return codigoDescuento;
	}

	public void borrarCodigoDescuento() {
		this.codigoDescuento = null;
	}
	
	@Override
	public String toString() {
	    return "login\t" + this.login + "|contrasena\t" + this.contrasena + "|puntos\t" + this.puntosFidelidad;
	}
	
	public double calcularDescuento(String codigoDescuento, int puntosAUsar, Collection<Usuario> usuarios) throws Exception {
	    if (codigoDescuento != null && !codigoDescuento.isEmpty()) {
	        boolean codigoValido = false;
	        for (Usuario u : usuarios) {
	            if (u instanceof Empleado && ((Empleado) u).getCodigoDescuento().equals(codigoDescuento)) {
	                codigoValido = true;
	                break;
	            }
	        }
	        if (!codigoValido) {
	            throw new Exception("El código de descuento no es válido.");
	        }
	        if (puntosAUsar > 0) {
	            throw new Exception("El descuento y los puntos no son acumulables.");
	        }
	        return 0.10;
	    } else if (puntosAUsar > 0) {
	        this.usarPuntos(puntosAUsar);
	    }
	    return 0;
	}
	

    
}
