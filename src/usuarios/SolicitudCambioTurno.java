package usuarios;

import java.time.LocalDateTime;

public class SolicitudCambioTurno {

    private String idSolicitud;
    private String tipoSolicitud;
    private LocalDateTime fechaSolicitud;
    private boolean aprobada;

    private Empleado solicitante;
    private Empleado empleadoDestino;

    private Turno turnoSolicitante;
    private Turno turnoDestino;

    public SolicitudCambioTurno(String idSolicitud, String tipoSolicitud,
                                LocalDateTime fechaSolicitud,
                                Empleado solicitante,
                                Empleado empleadoDestino,
                                Turno turnoSolicitante,
                                Turno turnoDestino) {

        this.idSolicitud = idSolicitud;
        this.tipoSolicitud = tipoSolicitud;
        this.fechaSolicitud = fechaSolicitud;
        this.aprobada = false;

        this.solicitante = solicitante;
        this.empleadoDestino = empleadoDestino;
        this.turnoSolicitante = turnoSolicitante;
        this.turnoDestino = turnoDestino;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public boolean isAprobada() {
        return aprobada;
    }

    public void aprobar() {
        this.aprobada = true;
    }

    public void rechazar() {
        this.aprobada = false;
    }

    public Empleado getSolicitante() {
        return solicitante;
    }

    public Empleado getEmpleadoDestino() {
        return empleadoDestino;
    }

    public Turno getTurnoSolicitante() {
        return turnoSolicitante;
    }

    public Turno getTurnoDestino() {
        return turnoDestino;
    }
}