package view;

import model.Inversion;
import model.Proyecto;
import model.Recompensa;
import model.Usuario;
import model.enums.CategoriaProyecto;
import model.interfaces.Bloqueable;
import utilidades.funcionesFechas;
import java.util.ArrayList;
import java.util.Scanner;

public class Vista {
    private Scanner scanner = new Scanner(System.in);

    // --- Métodos de Interacción Básica ---
    public void mostrarMensaje(String mensaje) { System.out.println(mensaje); }
    public String solicitarCadena(String mensaje) { System.out.print(mensaje); return scanner.nextLine(); }
    public int solicitarEntero(String mensaje) { while (true) { try { return Integer.parseInt(solicitarCadena(mensaje)); } catch (NumberFormatException e) { mostrarMensaje("Error: Ingresa un número entero."); } } }
    public double solicitarDouble(String mensaje) { while (true) { try { return Double.parseDouble(solicitarCadena(mensaje)); } catch (NumberFormatException e) { mostrarMensaje("Error: Ingresa un número decimal."); } } }

    // --- Menús ---
    public void mostrarMenuPrincipal() { mostrarMensaje("\n--- MENÚ PRINCIPAL ---\n1. Iniciar Sesión\n2. Registrarse\n3. Salir"); }
    public void mostrarMenuAdmin() { mostrarMensaje("\n--- MENÚ ADMIN ---\n1. Gestión de Usuarios\n2. Gestión de Proyectos\n3. Configuración\n4. Cerrar Sesión"); }
    public void mostrarMenuGestor() { mostrarMensaje("\n--- MENÚ GESTOR ---\n1. Gestionar mis Proyectos\n2. Crear Nuevo Proyecto\n3. Configuración\n4. Cerrar Sesión"); }
    public void mostrarMenuInversor() { mostrarMensaje("\n--- MENÚ INVERSOR ---\n1. Mis Inversiones\n2. Explorar Proyectos\n3. Cartera Digital\n4. Invitar a un Amigo\n5. Configuración\n6. Cerrar Sesión"); }
    public void mostrarMenuConfiguracion() { mostrarMensaje("\n--- CONFIGURACIÓN ---\n1. Cambiar Contraseña\n2. Volver"); }
    public void mostrarMenuConfiguracionAdmin() { mostrarMensaje("\n--- CONFIGURACIÓN ADMIN ---\n1. Cambiar mi Contraseña\n2. Volver"); }

    // --- Vistas de Listados ---
    public void listarUsuarios(ArrayList<Usuario> usuarios) {
        mostrarMensaje("\n--- LISTA DE USUARIOS ---");
        for (Usuario u : usuarios) {
            String estadoBloqueo = "N/A"; // Valor por defecto para los no bloqueables (Admin)

            // Comprobamos si el usuario implementa la interfaz Bloqueable
            if (u instanceof Bloqueable) {
                // Si la implementa, hacemos un casting para poder llamar a isBloqueado()
                estadoBloqueo = ((Bloqueable) u).isBloqueado() ? "Sí" : "No";
            }

            System.out.printf("ID: %d | Nombre: %s (%s) - Bloqueado: %s\n",
                    u.getId(),
                    u.getNombre(),
                    u.getTipo(),
                    estadoBloqueo);
        }
    }

    public void listarProyectos(ArrayList<Proyecto> proyectos) {
        mostrarMensaje("\n--- LISTA DE PROYECTOS ---");
        if (proyectos.isEmpty()) { mostrarMensaje("No hay proyectos para mostrar."); return; }
        for(Proyecto p : proyectos) { System.out.printf("ID: %d | Nombre: %s | Cat: %s | %.2f€ / %.2f€\n", p.getId(), p.getNombre(), p.getCategoria(), p.getCantidadFinanciada(), p.getCantidadNecesaria()); }
    }

    public void listarInversiones(ArrayList<Inversion> inversiones, ArrayList<Proyecto> todosProyectos) {
        mostrarMensaje("\n--- MIS INVERSIONES ---");
        if (inversiones.isEmpty()) { mostrarMensaje("No has realizado ninguna inversión."); return; }
        for (Inversion inv : inversiones) {
            // Buscamos el proyecto correspondiente a la inversión para mostrar su nombre
            Proyecto p = todosProyectos.stream()
                    .filter(pr -> pr.getId() == inv.getProyecto().getId())
                    .findFirst()
                    .orElse(null);
            if (p != null) {
                System.out.printf("- En '%s': %.2f€\n", p.getNombre(), inv.getCantidad());
            }
        }
    }

    // --- Vistas de Detalles ---
    public void mostrarDetallesProyecto(Proyecto p) {
        mostrarMensaje("\n--- DETALLES: " + p.getNombre().toUpperCase() + " ---");
        System.out.printf("  Descripción: %s\n  Categoría: %s\n  Necesario: %.2f€\n  Financiado: %.2f€\n", p.getDescripcion(), p.getCategoria(), p.getCantidadNecesaria(), p.getCantidadFinanciada());
        mostrarMensaje("  Inicio: " + funcionesFechas.formatearFecha(p.getFechaInicio()) + " | Fin: " + funcionesFechas.formatearFecha(p.getFechaFin()));
        mostrarProgresoFinanciacion(p);
        mostrarMensaje("\n  --- Recompensas ---");
        int i = 1;
        for (Recompensa r : p.getRecompensas()) { System.out.printf("  %d. %s (%.2f€): %s\n", i++, r.getNombre(), r.getPrecio(), r.getDescripcion()); }
    }

    public void mostrarProgresoFinanciacion(Proyecto p) {
        double necesario = p.getCantidadNecesaria(), financiado = p.getCantidadFinanciada();
        double porcentaje = (necesario > 0) ? (financiado / necesario) * 100 : 0;
        int barras = (int) (porcentaje / 5);
        System.out.printf("  Progreso: %.1f%% [", porcentaje);
        for (int i = 0; i < 20; i++) System.out.print(i < barras ? "█" : "░");
        System.out.printf("] %.2f€ / %.2f€\n", financiado, necesario);
    }

    // --- Vistas de Formularios ---
    public CategoriaProyecto solicitarCategoria() {
        mostrarMensaje("Selecciona una categoría:");
        for(CategoriaProyecto cat : CategoriaProyecto.values()) { System.out.printf("%d. %s\n", cat.ordinal() + 1, cat.name()); }
        int opcion = solicitarEntero("Opción: ");
        if (opcion > 0 && opcion <= CategoriaProyecto.values().length) {
            return CategoriaProyecto.values()[opcion - 1];
        }
        return CategoriaProyecto.OTRA; // Valor por defecto
    }
}