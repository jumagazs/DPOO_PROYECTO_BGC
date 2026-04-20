package mesas;

public class Mesa {
    private String idMesa;
    private int capacidad;
    private boolean ocupada;
    private int personasActuales;
    private boolean hayNinos;  
    private boolean hayJovenes;

    public Mesa(String idMesa, int capacidad) {
        this.idMesa = idMesa;
        this.capacidad = capacidad;
        this.ocupada = false;
        this.personasActuales = 0;
        this.hayJovenes = false;
        this.hayNinos = false;
    }

    public String getIdMesa() {
        return idMesa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public int getPersonasActuales() {
        return personasActuales;
    }

    public void asignarMesa(int cantidadPersonas,boolean hayJovenes,boolean hayNinos) throws Exception {
        if (ocupada) {
            throw new Exception("La mesa ya está ocupada.");
        }
        if (cantidadPersonas > capacidad) {
            throw new Exception("La cantidad de personas supera la capacidad de la mesa.");
        }

        this.ocupada = true;
        this.personasActuales = cantidadPersonas;
        this.hayJovenes = hayJovenes;
        this.hayNinos = hayNinos;
    }

    public void liberarMesa() {
        this.ocupada = false;
        this.personasActuales = 0;
    }

	public boolean isHayNinos() {
		return hayNinos;
	}

	public void setHayNinos(boolean hayNinos) {
		this.hayNinos = hayNinos;
	}

	public boolean isHayJovenes() {
		return hayJovenes;
	}

	public void setHayJovenes(boolean hayJovenes) {
		this.hayJovenes = hayJovenes;
	}
    
    
}
