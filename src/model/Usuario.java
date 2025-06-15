package model;

import model.enums.TipoUsuario;

public abstract class Usuario {
    protected int id;
    protected String nombre;
    protected String contrasena;
    protected String correo;
    protected TipoUsuario tipo;

    public Usuario(int id, String nombre, String contrasena, String correo, TipoUsuario tipo) {
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.correo = correo;
        this.tipo = tipo;
    }

    // Getters y Setters comunes
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getCorreo() { return correo; }
    public TipoUsuario getTipo() { return tipo; }
}