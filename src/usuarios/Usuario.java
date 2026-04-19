package usuarios;

import java.util.ArrayList;
import java.util.List;
import Juegos.JuegoMesa;

public abstract class Usuario {
	private String login;
	private String contrasena;
	private String nombre;
	private List<JuegoMesa> favoritos;
	

	public Usuario(String login, String contrasena) {
		super();
		this.login = login;
		this.contrasena = contrasena;
		this.favoritos = new ArrayList<>();
	}
	
	public String getLogin() {
        return login;
    }

    public String getContrasena() {
        return contrasena;
    }

	// Metodos para RF11 //

	Public void agregarJuegoFavorito(JuegoMesa juego) throws Exception{
		if (juego == null) {
			throw new Exception(" No puede ser nulo un juego ");
		}
		if (favoritos.contains(juego)){
			throw new Exception( " Este juego ya esta dentro de sus favoritos");
		}
		favoritos.add(juego);
	}
	
	Public void eliminarJuegoFavorito(JuegoMesa juego) throws Exception{
		if(!favoritos.contains(juego)) {
			throw new Exception(" Este juego no hace parte de favoritos ");
		}

		favoritos.remove(juego);
	}
	
	Public List<JuegoMesa> getJuegosFavoritos(){
		return new ArrayList<>(favoritos);
	}
	
	Public void cambiarPreferencias(List<JuegoMesa> nuevosFavoritos) throw Exception{
		if (nuevosFavoritos == null) {
			throw new Exception (" No puedes pasar una informacion nula ");
		
		}
		this.favoritos.clear();

		for(JuegoMesa juego : nuevosFavoritos){
			if (juego == null){
				throw new Exception ("No se permiten juegos con valor nulo ");

			}
			if (!favoritos.contains(juego)){
				favoritos.add(juego);
			}
		}
	}

	public boolean esFavorito(JuegoMesa juego){
		return favoritos.contains(juego);
	}
}

