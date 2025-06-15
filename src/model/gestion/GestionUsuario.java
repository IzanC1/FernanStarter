package model.gestion;

import model.*;
import model.enums.TipoUsuario;
import java.util.ArrayList;
import java.util.HashMap;

public class GestionUsuario {
    // La clave (Integer) es el ID del usuario.
    // El valor (Usuario) es el objeto de usuario completo.
    private static HashMap<Integer, Usuario> usuarios = new HashMap<>();
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
        // La comprobación de nombre duplicado ahora es diferente: recorremos los valores.
        if (buscarPorNombre(nombre) != null) {
            return null;
        }

        Usuario nuevoUsuario = null;
        int idActual = proximoId; // Guardamos el ID que se va a usar

        switch(tipo) {
            case ADMIN:
                nuevoUsuario = new Administrador(idActual, nombre, contrasena, correo);
                break;
            case GESTOR:
                nuevoUsuario = new Gestor(idActual, nombre, contrasena, correo);
                break;
            case INVERSOR:
                nuevoUsuario = new Inversor(idActual, nombre, contrasena, correo);
                break;
        }

        if (nuevoUsuario != null) {
            // Añadimos al HashMap usando el ID como clave.
            usuarios.put(idActual, nuevoUsuario);
            proximoId++;
        }
        return nuevoUsuario;
    }

    /**
     * Busca un usuario por su nombre.
     * Como el nombre no es la clave, tenemos que iterar sobre los valores del HashMap.
     * La eficiencia aquí es la misma que con el ArrayList (O(n)).
     */
    public static Usuario buscarPorNombre(String nombre) {
        for (Usuario u : usuarios.values()) { // Iteramos sobre los valores (objetos Usuario)
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Busca un usuario por su ID.
     * Esta es la operación ultra-rápida (O(1)) gracias al HashMap.
     */
    public static Usuario buscarPorId(int id) {
        // Simplemente obtenemos el valor asociado a la clave 'id'.
        return usuarios.get(id);
    }

    /**
     * Devuelve todos los usuarios en una lista.
     * Útil para cuando la Vista necesita mostrar a todos los usuarios.
     */
    public static ArrayList<Usuario> obtenerTodos() {
        return new ArrayList<>(usuarios.values());
    }

    /**
     * Devuelve una lista de todos los usuarios excepto el administrador.
     */
    public static ArrayList<Usuario> obtenerTodosMenosAdmin() {
        ArrayList<Usuario> listaFiltrada = new ArrayList<>();
        for (Usuario u : usuarios.values()) {
            if (u.getTipo() != TipoUsuario.ADMIN) {
                listaFiltrada.add(u);
            }
        }
        return listaFiltrada;
    }

    /**
     * Elimina un usuario por su ID.
     * Esta operación también es muy eficiente (O(1)).
     */
    public static boolean eliminarUsuario(int id) {
        // remove() devuelve el objeto eliminado si la clave existía, o null si no.
        return usuarios.remove(id) != null;
    }
}