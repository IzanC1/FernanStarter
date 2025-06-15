package model;

import java.time.LocalDateTime;

public class Inversion {
    private int idUsuario;
    private int idProyecto;
    private double cantidad;
    private LocalDateTime fechaInversion;

    public Inversion(int idUsuario, int idProyecto, double cantidad) {
        this.idUsuario = idUsuario;
        this.idProyecto = idProyecto;
        this.cantidad = cantidad;
        this.fechaInversion = LocalDateTime.now();
    }

    public int getIdUsuario() { return idUsuario; }
    public int getIdProyecto() { return idProyecto; }
    public double getCantidad() { return cantidad; }
    public LocalDateTime getFechaInversion() { return fechaInversion; }
}