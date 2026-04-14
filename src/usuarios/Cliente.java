package usuarios;

public class Cliente extends Usuario {
		private int puntosFidelidad;
		
	public Cliente(String login, String contrasena) {
			super(login, contrasena);
			this.puntosFidelidad = 0;
			// TODO Auto-generated constructor stub
		}

}
