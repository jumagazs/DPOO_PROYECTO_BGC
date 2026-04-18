package usuarios;

public abstract class Usuario {
	private String login;
	private String contrasena;
	

	public Usuario(String login, String contrasena) {
		super();
		this.login = login;
		this.contrasena = contrasena;
	}
	
	public String getLogin() {
        return login;
    }

    public String getContrasena() {
        return contrasena;
    }
}
