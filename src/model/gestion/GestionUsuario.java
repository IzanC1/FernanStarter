package model.gestion;

import model.*;
import model.enums.TipoUsuario;
import java.util.ArrayList;

public class GestionUsuario {
    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static int proximoId = 0;

    public static void inicializar() {
        if (usuarios.isEmpty()) {
            registrarUsuario("admin", "admin", "izan.cano.0805@fernando3martos.com", TipoUsuario.ADMIN);
            registrarUsuario("gestor", "gestor", "izan.cano.0805@fernando3martos.com", TipoUsuario.GESTOR);
            Usuario inversor = registrarUsuario("inversor", "inversor", "izan.cano.0805@fernando3martos.com", TipoUsuario.INVERSOR);
            if (inversor instanceof Inversor) {
                ((Inversor) inversor).setSaldo(1000.0);
            }
        }
    }

    public static Usuario registrarUsuario(String nombre, String contrasena, String correo, TipoUsuario tipo) {
        if (buscarPorNombre(nombre) != null) {
            return null;
        }

        Usuario nuevoUsuario = null;
        switch(tipo) {
            case ADMIN:
                nuevoUsuario = new Administrador(proximoId, nombre, contrasena, correo);
                break;
            case GESTOR:
                nuevoUsuario = new Gestor(proximoId, nombre, contrasena, correo);
                break;
            case INVERSOR:
                nuevoUsuario = new Inversor(proximoId, nombre, contrasena, correo);
                break;
        }

        if (nuevoUsuario != null) {
            usuarios.add(nuevoUsuario);
            proximoId++;
        }
        return nuevoUsuario;
    }

    public static Usuario buscarPorNombre(String nombre) {
        for (Usuario u : usuarios) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                return u;
            }
        }
        return null;
    }

    public static Usuario buscarPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public static ArrayList<Usuario> obtenerTodosMenosAdmin() {
        ArrayList<Usuario> listaFiltrada = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getTipo() != TipoUsuario.ADMIN) {
                listaFiltrada.add(u);
            }
        }
        return listaFiltrada;
    }
}