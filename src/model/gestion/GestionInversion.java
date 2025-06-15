package model.gestion;

import model.Inversion;
import java.util.ArrayList;

public class GestionInversion {
    private static ArrayList<Inversion> inversiones = new ArrayList<>();

    public static void registrarInversion(int idUsuario, int idProyecto, double cantidad) {
        Inversion nuevaInversion = new Inversion(idUsuario, idProyecto, cantidad);
        inversiones.add(nuevaInversion);
    }

    public static ArrayList<Inversion> obtenerPorUsuario(int idUsuario) {
        ArrayList<Inversion> inversionesUsuario = new ArrayList<>();
        for (Inversion i : inversiones) {
            if (i.getIdUsuario() == idUsuario) {
                inversionesUsuario.add(i);
            }
        }
        return inversionesUsuario;
    }

    public static boolean haInvertidoUsuario(int idUsuario, int idProyecto) {
        for (Inversion i : inversiones) {
            if (i.getIdUsuario() == idUsuario && i.getIdProyecto() == idProyecto) {
                return true;
            }
        }
        return false;
    }
}