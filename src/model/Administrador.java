package model;

import model.enums.TipoUsuario;

public class Administrador extends Usuario {
    public Administrador(int id, String nombre, String contrasena, String correo) {
        super(id, nombre, contrasena, correo, TipoUsuario.ADMIN);
    }
    // No tiene atributos ni métodos específicos en este momento.
}