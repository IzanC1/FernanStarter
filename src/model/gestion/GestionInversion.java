package model.gestion;

import model.Inversion;
import model.Inversor;
import model.Proyecto;
import java.util.ArrayList;

public class GestionInversion {
    private static ArrayList<Inversion> inversiones = new ArrayList<>();

    public static void registrarInversion(Inversor inversor, Proyecto proyecto, double cantidad) {
        Inversion nuevaInversion = new Inversion(inversor, proyecto, cantidad);
        inversiones.add(nuevaInversion);
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