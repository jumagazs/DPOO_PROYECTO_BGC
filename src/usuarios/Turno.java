package usuarios;

import java.time.LocalDateTime;

public class Turno {

    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private String dia;
    private Empleado empleado;

    public Turno(LocalDateTime horaInicio, LocalDateTime horaFin, String dia, Empleado empleado) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.dia = dia;
        this.empleado = empleado;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public LocalDateTime getHoraFin() {
        return horaFin;
    }

    public String getDia() {
        return dia;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
}