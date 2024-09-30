package com.proyecto.aplicativoadministradorypersonalmedico.DB;

public class CitaMedica {

    private String codigo;
    private String dni;
    private String fechaCita;
    private String Asunto;
    private String correoElectronico;
    private String estado;


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(String fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getAsunto() {
        return Asunto;
    }

    public void setAsunto(String asunto) {
        this.Asunto = asunto;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
