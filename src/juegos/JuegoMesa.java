package juegos;

public class JuegoMesa {
    private String nombre;
    private int anioPublicacion;
    private String editorJuego;
    private String categoria;
    private int minJugadores;
    private int maxJugadores;
    private boolean esDificil;
    private boolean jueganMenores5;
    private boolean jueganMenores18;
    private String idJuego;

    public JuegoMesa(String nombre, int anioPublicacion, String editorJuego, String categoria, int minJugadores, int maxJugadores, boolean esDificil, boolean jueganMenores5, boolean jueganMenores18, String idJuego) {
        this.nombre = nombre;
        this.anioPublicacion = anioPublicacion;
        this.editorJuego = editorJuego;
        this.categoria = categoria;
        this.minJugadores = minJugadores;
        this.maxJugadores = maxJugadores;
        this.esDificil = esDificil;
        this.jueganMenores5 = jueganMenores5;
        this.jueganMenores18 = jueganMenores18;
        this.idJuego = idJuego;
    }



    public String getNombre() {
		return nombre;
	}


	public String getCategoria() {
        return categoria;
    }

    public boolean isEsDificil() {
        return esDificil;
    }

    public int getMinJugadores() {
        return minJugadores;
    }

    public int getMaxJugadores() {
        return maxJugadores;
    }

    public String getIdJuego() {
        return idJuego;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        JuegoMesa juego = (JuegoMesa) obj;
        return nombre.equals(juego.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
}
