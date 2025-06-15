package model.gestion;

import model.Proyecto;
import model.Recompensa;
import model.enums.CategoriaProyecto;
import utilidades.funcionesFechas;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class GestionProyecto {
    private static ArrayList<Proyecto> proyectos = new ArrayList<>();
    private static int proximoId = 0;

    public static void inicializar() {
        if (proyectos.isEmpty()) {
            Proyecto p1 = crearProyecto("Dron de Riego Inteligente", "Dron autónomo con IA para optimizar riego.", CategoriaProyecto.TECNOLOGIA, 5000, funcionesFechas.parsearFecha("01/01/2024"), funcionesFechas.parsearFecha("31/12/2024"), 1);
            if (p1 != null) {
                p1.anadirRecompensa(new Recompensa("Agradecimiento", "Correo de agradecimiento", 20));
                p1.anadirFinanciacion(1500);
            }
            Proyecto p2 = crearProyecto("Película de Ciencia Ficción", "Cortometraje independiente.", CategoriaProyecto.CINE, 2500, funcionesFechas.parsearFecha("15/03/2024"), funcionesFechas.parsearFecha("15/09/2024"), 1);
            if (p2 != null) {
                p2.anadirRecompensa(new Recompensa("Créditos", "Apareces en los créditos", 30));
                p2.anadirFinanciacion(2000);
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
        return new ArrayList<>(proyectos); // Devolvemos una copia para evitar modificaciones externas
    }

    public static ArrayList<Proyecto> obtenerProyectosOrdenadosPorFinanciacion() {
        ArrayList<Proyecto> proyectosOrdenados = new ArrayList<>(proyectos);
        proyectosOrdenados.sort(Comparator.comparingDouble(Proyecto::getCantidadFinanciada).reversed());
        return proyectosOrdenados;
    }

    public static ArrayList<Proyecto> obtenerProyectosOrdenadosPorFecha() {
        ArrayList<Proyecto> proyectosOrdenados = new ArrayList<>(proyectos);
        proyectosOrdenados.sort(Comparator.comparing(Proyecto::getFechaInicio).reversed());
        return proyectosOrdenados;
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
            proyectos.remove(p);
            return true;
        }
        return false;
    }
}