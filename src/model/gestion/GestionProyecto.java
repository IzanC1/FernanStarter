package model.gestion;

import model.Proyecto;
import model.Recompensa;
import model.enums.CategoriaProyecto;
import utilidades.funcionesFechas;
import java.time.LocalDate;
import java.util.ArrayList;

public class GestionProyecto {
    private static ArrayList<Proyecto> proyectos = new ArrayList<>();
    private static int proximoId = 0;

    public static void inicializar() {
        if (proyectos.isEmpty()) {
            Proyecto p = crearProyecto("Dron de Riego Inteligente", "Dron autónomo con IA para optimizar riego.", CategoriaProyecto.TECNOLOGIA, 5000, funcionesFechas.parsearFecha("01/01/2024"), funcionesFechas.parsearFecha("31/12/2024"), 1); // Gestor con ID 1
            if (p != null) {
                p.anadirRecompensa(new Recompensa("Agradecimiento", "Correo de agradecimiento", 20));
                p.anadirRecompensa(new Recompensa("Kit Pegatinas", "Un kit de pegatinas", 50));
                p.anadirRecompensa(new Recompensa("Acceso Beta", "Prueba la tecnología", 250));
            }
        }
    }

    public static Proyecto crearProyecto(String nombre, String desc, CategoriaProyecto cat, double cant, LocalDate inicio, LocalDate fin, int idGestor) {
        Proyecto nuevoProyecto = new Proyecto(proximoId, nombre, desc, cat, cant, inicio, fin, idGestor);
        proyectos.add(nuevoProyecto);
        proximoId++;
        return nuevoProyecto;
    }

    public static ArrayList<Proyecto> obtenerTodos() {
        return proyectos;
    }

    public static ArrayList<Proyecto> obtenerPorGestor(int idGestor) {
        ArrayList<Proyecto> proyectosGestor = new ArrayList<>();
        for(Proyecto p : proyectos) {
            if (p.getIdGestor() == idGestor) {
                proyectosGestor.add(p);
            }
        }
        return proyectosGestor;
    }

    public static Proyecto buscarPorId(int id) {
        for (Proyecto p : proyectos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public static boolean eliminarProyecto(int id) {
        Proyecto p = buscarPorId(id);
        if (p != null) {
            // Antes de eliminar el proyecto, se deberían gestionar las inversiones (ej. devolver dinero).
            proyectos.remove(p);
            return true;
        }
        return false;
    }
}