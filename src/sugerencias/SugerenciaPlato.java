package sugerencias;

import java.time.LocalDateTime;
import usuarios.Empleado;

public class SugerenciaPlato {

    private String idSugerencia;
    private String nombrePropuesto;
    private LocalDateTime fechaSugerencia;
    private String estadoSugerencia;
    private Empleado empleado;

    public SugerenciaPlato(String idSugerencia, String nombrePropuesto, LocalDateTime fechaSugerencia, String estadoSugerencia, Empleado empleado) {

        this.idSugerencia = idSugerencia;
        this.nombrePropuesto = nombrePropuesto;
        this.fechaSugerencia = fechaSugerencia;
        this.estadoSugerencia = estadoSugerencia;
        this.empleado = empleado;
    }

    public String getIdSugerencia() {
        return idSugerencia;
    }

    public String getNombrePropuesto() {
        return nombrePropuesto;
    }

    public LocalDateTime getFechaSugerencia() {
        return fechaSugerencia;
    }

    public String getEstadoSugerencia() {
        return estadoSugerencia;
    }

    public Empleado getEmpleado() {
        return empleado;
    }
}