package usuarios;

public class Cliente extends Usuario {
		private int puntosFidelidad;
		
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

}
