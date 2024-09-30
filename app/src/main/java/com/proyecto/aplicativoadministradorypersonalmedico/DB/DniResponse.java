package com.proyecto.aplicativoadministradorypersonalmedico.DB;

public class DniResponse {

    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String direccion;

    public String getNombres(){
        return nombres;
    }
    public void setNombre(String nombres) {
        this.nombres = nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
