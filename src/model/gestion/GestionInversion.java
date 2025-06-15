package model.gestion;

import model.Inversion;
import model.Inversor;
import model.Proyecto;
import java.util.ArrayList;
import java.util.Comparator;

public class GestionInversion {
    private static ArrayList<Inversion> inversiones = new ArrayList<>();

    public static void registrarInversion(Inversor inversor, Proyecto proyecto, double cantidad) {
        Inversion nuevaInversion = new Inversion(inversor, proyecto, cantidad);
        inversiones.add(nuevaInversion);
    }

    public static ArrayList<Inversion> obtenerTodasLasInversiones() {
        return new ArrayList<>(inversiones);
    }

    public static ArrayList<Inversion> obtenerInversionesOrdenadasPorUsuario() {
        ArrayList<Inversion> inversionesOrdenadas = new ArrayList<>(inversiones);
        // Compara usando el nombre del inversor obtenido a través del getter
        inversionesOrdenadas.sort(Comparator.comparing(inversion -> inversion.getInversor().getNombre()));
        return inversionesOrdenadas;
    }

    public static ArrayList<Inversion> obtenerInversionesOrdenadasPorImporte() {
        ArrayList<Inversion> inversionesOrdenadas = new ArrayList<>(inversiones);
        inversionesOrdenadas.sort(Comparator.comparingDouble(Inversion::getCantidad).reversed());
        return inversionesOrdenadas;
    }

    public static ArrayList<Inversion> obtenerPorUsuario(int idUsuario) {
        ArrayList<Inversion> inversionesUsuario = new ArrayList<>();
        for (Inversion i : inversiones) {
            if (i.getInversor().getId() == idUsuario) {
                inversionesUsuario.add(i);
            }
        }
        return inversionesUsuario;
    }

    public static boolean haInvertidoUsuario(int idUsuario, int idProyecto) {
        for (Inversion i : inversiones) {
            if (i.getInversor().getId() == idUsuario && i.getProyecto().getId() == idProyecto) {
                return true;
            }
        }
        return false;
    }
}