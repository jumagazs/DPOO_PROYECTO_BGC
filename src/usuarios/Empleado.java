package usuarios;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public abstract class Empleado extends Usuario {

    private Turno turno;
    private List<SolicitudCambioTurno> solicitudes;
	private static double DESCUENTO_EMPlEADO = 0.20;
	private static double DESCUENTO_CLIENTE = 0.10;
	private String codigoDescuento;

    public Empleado(String login, String contrasena) {
        super(login, contrasena);
        this.solicitudes = new ArrayList<>();
        this.codigoDescuento= "EMP-" + login.hashCode();
    }

   // Requeriminto 12 //
    public Turno consultarTurno() throws Exception {
        if (turno == null) {
            throw new Exception("El empleado no tiene turno asignado.");
        }
        return turno;
    }

    public void asignarTurno(Turno turno) {
        this.turno = turno;
    }
	//requermiento 13//
    public SolicitudCambioTurno solicitarCambioTurnoGeneral(String idSolicitud, String tipoSolicitud) throws Exception {

    if (this.turno == null) {
        throw new Exception("El empleado no tiene turno asignado.");
    }

    SolicitudCambioTurno solicitud = new SolicitudCambioTurno(
            idSolicitud,
            tipoSolicitud,
            java.time.LocalDateTime.now(),
            this,
            null,
            this.turno,
            null
    );

    solicitudes.add(solicitud);
    return solicitud;
	}

    public List<SolicitudCambioTurno> getSolicitudes() {
        return solicitudes;
    }

	public String getCodigoDescuento() {
		return codigoDescuento;
	}
    
	public double getDescuentoEMP() { 
		return DESCUENTO_EMPlEADO;
	}

	public double getDescuentoCLI() { 
		return DESCUENTO_CLIENTE;
	}
	
}