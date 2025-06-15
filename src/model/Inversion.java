package model;

import model.interfaces.Modificable;
import java.time.LocalDateTime;

public class Inversion implements Modificable {
    private Inversor inversor; // Ahora se relaciona con un objeto Inversor
    private Proyecto proyecto; // Y un objeto Proyecto
    private double cantidad;
    private LocalDateTime fechaInversion;

    public Inversion(Inversor inversor, Proyecto proyecto, double cantidad) {
        this.inversor = inversor;
        this.proyecto = proyecto;
        this.cantidad = cantidad;
        this.fechaInversion = LocalDateTime.now();
    }

    @Override
    public void aumentaInversion(double cantidadAdicional) {
        if (cantidadAdicional > 0) {
            this.cantidad += cantidadAdicional;
        }
    }

    @Override
    public void disminuyeInversion(double cantidadAReducir) {
        if (cantidadAReducir > 0 && this.cantidad - cantidadAReducir >= 0) {
            this.cantidad -= cantidadAReducir;
        }
    }

    // Getters
    public Inversor getInversor() { return inversor; }
    public Proyecto getProyecto() { return proyecto; }
    public double getCantidad() { return cantidad; }
    public LocalDateTime getFechaInversion() { return fechaInversion; }
}