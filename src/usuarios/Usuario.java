package usuarios;

public abstract class Usuario {
	private String login;
	private String contrasena;
	

	public Usuario(String login, String contrasena) {
		super();
		this.login = login;
		this.contrasena = contrasena;
	}

}
