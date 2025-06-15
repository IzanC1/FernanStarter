package model;

import model.enums.CategoriaProyecto;
import java.time.LocalDate;
import java.util.ArrayList;

public class Proyecto {
    private int id;
    private String nombre;
    private String descripcion;
    private CategoriaProyecto categoria;
    private double cantidadNecesaria;
    private double cantidadFinanciada;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int idGestor;
    private ArrayList<Recompensa> recompensas;

    public Proyecto(int id, String nombre, String descripcion, CategoriaProyecto categoria, double cantidadNecesaria, LocalDate fechaInicio, LocalDate fechaFin, int idGestor) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.cantidadNecesaria = cantidadNecesaria;
        this.cantidadFinanciada = 0;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idGestor = idGestor;
        this.recompensas = new ArrayList<>();
    }

    public void anadirRecompensa(Recompensa recompensa) {
        if (recompensas.size() < 3) {
            recompensas.add(recompensa);
        }
    }

    public void anadirFinanciacion(double cantidad) {
        this.cantidadFinanciada += cantidad;
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public CategoriaProyecto getCategoria() { return categoria; }
    public double getCantidadNecesaria() { return cantidadNecesaria; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public int getIdGestor() { return idGestor; }
    public ArrayList<Recompensa> getRecompensas() { return recompensas; }
    public double getCantidadFinanciada() { return cantidadFinanciada; }
}