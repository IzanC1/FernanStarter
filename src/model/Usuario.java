package model;

import model.enums.TipoUsuario;

public class Usuario {
    private int id;
    private String nombre;
    private String contrasena;
    private String correo;
    private TipoUsuario tipo;
    private boolean bloqueado;
    private int intentos;
    private double saldo;
    private String amigos;

    public Usuario(int id, String nombre, String contrasena, String correo, TipoUsuario tipo) {
        this.id = id;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.correo = correo;
        this.tipo = tipo;
        this.bloqueado = false;
        this.intentos = 3;
        this.saldo = 0.0;
        this.amigos = "";
    }

    public void anadirAmigo(String correoAmigo) {
        if (this.amigos == null || this.amigos.isEmpty()) {
            this.amigos = correoAmigo;
        } else {
            this.amigos += ", " + correoAmigo;
        }
    }

    public void decrementarIntentos() { this.intentos--; }

    // Getters y Setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getCorreo() { return correo; }
    public TipoUsuario getTipo() { return tipo; }
    public boolean isBloqueado() { return bloqueado; }
    public void setBloqueado(boolean bloqueado) { this.bloqueado = bloqueado; }
    public int getIntentos() { return intentos; }
    public void setIntentos(int intentos) { this.intentos = intentos; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public String getAmigos() { return amigos; }
}