package model.gestion;

import model.Usuario;
import model.enums.TipoUsuario;
import java.util.ArrayList;

public class GestionUsuario {
    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static int proximoId = 0;

    public static void inicializar() {
        if (usuarios.isEmpty()) {
            registrarUsuario("admin", "admin", "izan.cano.0805@fernando3martos.com", TipoUsuario.ADMIN);
            registrarUsuario("gestor", "gestor", "izan.cano.0805@fernando3martos.com", TipoUsuario.GESTOR);
            registrarUsuario("inversor", "inversor", "izan.cano.0805@fernando3martos.com", TipoUsuario.INVERSOR).setSaldo(1000.0);
        }
    }

    public static Usuario registrarUsuario(String nombre, String contrasena, String correo, TipoUsuario tipo) {
        if (buscarPorNombre(nombre) != null) {
            return null;
        }
        Usuario nuevoUsuario = new Usuario(proximoId, nombre, contrasena, correo, tipo);
        usuarios.add(nuevoUsuario);
        proximoId++;
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

    public static boolean eliminarUsuario(int id) {
        Usuario u = buscarPorId(id);
        if (u != null && u.getTipo() != TipoUsuario.ADMIN) {
            usuarios.remove(u);
            // Aquí también se debería eliminar las inversiones y proyectos asociados a este usuario.
            return true;
        }
        return false;
    }
}