package usuarios;

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
	
	
    
    
    
    
    
}
