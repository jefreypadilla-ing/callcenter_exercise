package com.almundo.bean;

/**
 * @author Jefrey Padilla
 */
public class Empleado{

    private String codigo;
    private String nombres;
    private String apellidos;
    private String tipoEmpleado;
    private int prioridadTipoEmpleado;
    private String disponibilidad;

    public Empleado(){}

    public Empleado(String codigo, String nombres, String apellidos, String tipoEmpleado, int prioridadTipoEmpleado, String disponibilidad) {
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoEmpleado = tipoEmpleado;
        this.prioridadTipoEmpleado = prioridadTipoEmpleado;
        this.disponibilidad = disponibilidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(String tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public int getPrioridadTipoEmpleado() {
        return prioridadTipoEmpleado;
    }

    public void setPrioridadTipoEmpleado(int prioridadTipoEmpleado) {
        this.prioridadTipoEmpleado = prioridadTipoEmpleado;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "codigo='" + codigo + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", tipoEmpleado='" + tipoEmpleado + '\'' +
                ", prioridadTipoEmpleado=" + prioridadTipoEmpleado +
                '}';
    }
}
