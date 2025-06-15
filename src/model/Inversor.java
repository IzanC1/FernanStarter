package model;

import model.enums.TipoUsuario;
import model.interfaces.Bloqueable;

public class Inversor extends Usuario implements Bloqueable {
    private boolean bloqueado;
    private int intentos;
    private double saldo;
    private String amigos;

    public Inversor(int id, String nombre, String contrasena, String correo) {
        super(id, nombre, contrasena, correo, TipoUsuario.INVERSOR);
        this.bloqueado = false;
        this.intentos = 3;
        this.saldo = 0.0;
        this.amigos = "";
    }

    public int getIntentos() { return intentos; }
    public void setIntentos(int intentos) { this.intentos = intentos; }
    public void decrementarIntentos() { this.intentos--; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public String getAmigos() { return amigos; }

    public void anadirAmigo(String correoAmigo) {
        if (this.amigos == null || this.amigos.isEmpty()) {
            this.amigos = correoAmigo;
        } else {
            this.amigos += ", " + correoAmigo;
        }
    }

    @Override
    public void bloquear() {
        this.bloqueado = true;
    }

    @Override
    public void desbloquear() {
        this.bloqueado = false;
        this.intentos = 3;
    }

    @Override
    public boolean isBloqueado() {
        return this.bloqueado;
    }
}