package model;

import model.enums.TipoUsuario;
import model.interfaces.Bloqueable;

public class Gestor extends Usuario implements Bloqueable {
    private boolean bloqueado;
    private int intentos;

    public Gestor(int id, String nombre, String contrasena, String correo) {
        super(id, nombre, contrasena, correo, TipoUsuario.GESTOR);
        this.bloqueado = false;
        this.intentos = 3;
    }

    public int getIntentos() { return intentos; }
    public void setIntentos(int intentos) { this.intentos = intentos; }
    public void decrementarIntentos() { this.intentos--; }

    @Override
    public void bloquear() {
        this.bloqueado = true;
    }

    @Override
    public void desbloquear() {
        this.bloqueado = false;
        this.intentos = 3; // Resetea intentos al desbloquear
    }

    @Override
    public boolean isBloqueado() {
        return this.bloqueado;
    }
}