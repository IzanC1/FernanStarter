import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // --- Variables de Autenticación ---
        String adminNombre = "admin";
        String adminContrasena = "admin";
        boolean logingAdmin = false;

        String gestorNombre = "gestor";
        String gestorContrasena = "gestor";
        int intentosGestor = 3;
        boolean logingGestor = false;
        boolean bloqueadoGestor = false;

        String inversorNombre = "inversor";
        String inversorContrasena = "inversor";
        int intentosInversor = 3;
        boolean logingInversor = false;
        boolean bloqueadoInversor = false;

        // --- Variables del Proyecto (simplificado, para un solo proyecto) ---
        String proyectoNombre = "";
        String proyectoDescripcion = "";
        String proyectoCategoria = "";
        double proyectoCantidadNecesaria = 0;
        double proyectoCantidadFinanciada = 0;
        String proyectoFechaInicio = "";
        String proyectoFechaFin = "";

        // --- Variables de Recompensas (para el proyecto) ---
        String recompensa1 = "";
        String recompensa1Descripcion = "";
        double recompensa1Precio = 0;
        String recompensa2 = "";
        String recompensa2Descripcion = "";
        double recompensa2Precio = 0;
        String recompensa3 = "";
        String recompensa3Descripcion = "";
        double recompensa3Precio = 0;

        // --- Variables del Inversor ---
        boolean haInvertidoInversor = false;
        double inversionInversorProyecto = 0;
        double saldoInversor = 0;
        String amigosInversor = "";

        // --- Inicialización del Scanner para entrada de usuario ---
        Scanner scanner = new Scanner(System.in);

        int opcion0 = 0;
        // --- Bucle principal del programa (Login o Salir) ---
        while (opcion0 != 2) {
            System.out.println("\n------------------------------------");
            System.out.println("          MENÚ PRINCIPAL          ");
            System.out.println("------------------------------------");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Salir");
            System.out.print("Por favor, selecciona una opción: ");

            opcion0 = Integer.parseInt(scanner.nextLine());


            if (opcion0 == 1) {
                // --- Bucle de inicio de sesión ---
                do {
                    System.out.println("\n--- INICIO DE SESIÓN ---");
                    System.out.print("Usuario: ");
                    String usuario = scanner.nextLine();
                    System.out.print("Contraseña: ");
                    String contrasena = scanner.nextLine();

                    // --- Lógica de autenticación del Gestor ---
                    if (usuario.equals(gestorNombre)) {
                        if (bloqueadoGestor) {
                            System.out.println("¡Acceso denegado! Tu usuario ha sido bloqueado.");
                        } else {
                            if (contrasena.equals(gestorContrasena)) {
                                logingGestor = true;
                                System.out.println("¡Inicio de sesión exitoso como Gestor!");
                            } else {
                                System.out.println("Contraseña incorrecta para el usuario 'gestor'.");
                                intentosGestor--;
                                System.out.println("Intentos restantes: " + intentosGestor);
                                if (intentosGestor == 0) {
                                    System.out.println("¡Demasiados intentos fallidos! El usuario 'gestor' ha sido bloqueado.");
                                    bloqueadoGestor = true;
                                }
                            }
                        }
                    }
                    // --- Lógica de autenticación del Inversor ---
                    else if (usuario.equals(inversorNombre)) {
                        if (bloqueadoInversor) {
                            System.out.println("¡Acceso denegado! Tu usuario ha sido bloqueado.");
                        } else {
                            if (contrasena.equals(inversorContrasena)) {
                                logingInversor = true;
                                System.out.println("¡Inicio de sesión exitoso como Inversor!");
                            } else {
                                System.out.println("Contraseña incorrecta para el usuario 'inversor'.");
                                intentosInversor--;
                                System.out.println("Intentos restantes: " + intentosInversor);
                                if (intentosInversor == 0) {
                                    System.out.println("¡Demasiados intentos fallidos! El usuario 'inversor' ha sido bloqueado.");
                                    bloqueadoInversor = true;
                                }
                            }
                        }
                    }
                    // --- Lógica de autenticación del Administrador ---
                    else if (usuario.equals(adminNombre)) {
                        if (contrasena.equals(adminContrasena)) {
                            logingAdmin = true;
                            System.out.println("¡Inicio de sesión exitoso como Administrador!");
                        } else {
                            System.out.println("Contraseña incorrecta para el usuario 'admin'.");
                        }
                    } else {
                        System.out.println("Usuario no reconocido. Por favor, intenta de nuevo.");
                    }

                } while (!logingAdmin && !logingGestor && !logingInversor);

                // --- Panel del Administrador ---
                if (logingAdmin) {
                    System.out.println("\n====================================");
                    System.out.println("     BIENVENIDO, ADMINISTRADOR      ");
                    System.out.println("====================================");
                    int opcion;
                    do {
                        System.out.println("\n--- MENÚ DE ADMINISTRACIÓN ---");
                        System.out.println("1. Gestión de Usuarios");
                        System.out.println("2. Gestión de Proyectos");
                        System.out.println("3. Configuración");
                        System.out.println("4. Cerrar Sesión");
                        System.out.print("Selecciona una opción: ");

                        opcion = Integer.parseInt(scanner.nextLine());

                        switch (opcion) {
                            case 1:
                                System.out.println("\n--- GESTIÓN DE USUARIOS ---");
                                System.out.println("1. Bloquear usuario");
                                System.out.println("2. Desbloquear usuario");
                                System.out.println("3. Volver al menú principal");
                                System.out.print("Selecciona una opción: ");
                                int subOpcion = Integer.parseInt(scanner.nextLine());

                                if (subOpcion == 1) {
                                    System.out.print("Ingrese el nombre del usuario a bloquear ('gestor' o 'inversor'): ");
                                    String usuarioABloquear = scanner.nextLine();
                                    if (usuarioABloquear.equals(gestorNombre)) {
                                        bloqueadoGestor = true;
                                        System.out.println("Usuario '" + gestorNombre + "' bloqueado exitosamente.");
                                    } else if (usuarioABloquear.equals(inversorNombre)) {
                                        bloqueadoInversor = true;
                                        System.out.println("Usuario '" + inversorNombre + "' bloqueado exitosamente.");
                                    } else {
                                        System.out.println("Usuario no reconocido o no bloqueable.");
                                    }
                                } else if (subOpcion == 2) {
                                    System.out.print("Ingrese el nombre del usuario a desbloquear ('gestor' o 'inversor'): ");
                                    String usuarioADesbloquear = scanner.nextLine();
                                    if (usuarioADesbloquear.equals(gestorNombre)) {
                                        bloqueadoGestor = false;
                                        intentosGestor = 3;
                                        System.out.println("Usuario '" + gestorNombre + "' desbloqueado exitosamente. Intentos reiniciados.");
                                    } else if (usuarioADesbloquear.equals(inversorNombre)) {
                                        bloqueadoInversor = false;
                                        intentosInversor = 3;
                                        System.out.println("Usuario '" + inversorNombre + "' desbloqueado exitosamente. Intentos reiniciados.");
                                    } else {
                                        System.out.println("Usuario no reconocido o no desbloqueable.");
                                    }
                                } else if (subOpcion != 3) {
                                    System.out.println("Opción no válida.");
                                }
                                break;

                            case 2:
                                System.out.println("\n--- GESTIÓN DE PROYECTOS ---");
                                if (proyectoNombre.isEmpty()) {
                                    System.out.println("No hay proyectos creados aún.");
                                } else {
                                    // Vista rápida con nombre, categoría, cantidad necesaria y financiada
                                    System.out.println("1. Ver detalles de: " + proyectoNombre + " | Categoría: " + proyectoCategoria + " | Necesario: " + String.format("%.2f", proyectoCantidadNecesaria) + "€ | Financiado: " + String.format("%.2f", proyectoCantidadFinanciada) + "€");
                                }
                                System.out.println("2. Volver al menú principal");
                                System.out.print("Selecciona una opción: ");
                                int subOpcionProyecto = Integer.parseInt(scanner.nextLine());

                                if (subOpcionProyecto == 1 && !proyectoNombre.isEmpty()) {
                                    System.out.println("\n--- DETALLES DEL PROYECTO: " + proyectoNombre.toUpperCase() + " ---");
                                    System.out.println("  Descripción: " + proyectoDescripcion);
                                    System.out.println("  Categoría: " + proyectoCategoria);
                                    System.out.println("  Cantidad necesaria: " + String.format("%.2f", proyectoCantidadNecesaria) + "€");
                                    System.out.println("  Cantidad financiada: " + String.format("%.2f", proyectoCantidadFinanciada) + "€");
                                    System.out.println("  Fecha de inicio: " + proyectoFechaInicio);
                                    System.out.println("  Fecha de fin: " + proyectoFechaFin);

                                    // --- Gráfico de progreso de financiación (ahora debajo de los detalles) ---
                                    double porcentaje = (proyectoCantidadNecesaria > 0) ? (proyectoCantidadFinanciada / proyectoCantidadNecesaria) * 100 : 0;
                                    int barras = (int) (porcentaje / 5);
                                    System.out.println("\n  Progreso de financiación: " + String.format("%.1f", porcentaje) + "%");
                                    System.out.print("  [");
                                    for (int i = 0; i < 20; i++) {
                                        if (i < barras) System.out.print("█");
                                        else System.out.print("░");
                                    }
                                    System.out.println("] " + String.format("%.2f", proyectoCantidadFinanciada) + "€ / " + String.format("%.2f", proyectoCantidadNecesaria) + "€");
                                    // --- Fin del gráfico ---

                                    System.out.println("\n  --- Recompensas Asociadas ---");
                                    System.out.println("  1. " + (recompensa1.isEmpty() ? "N/A" : recompensa1 + " | " + recompensa1Descripcion + " | " + String.format("%.2f", recompensa1Precio) + "€"));
                                    System.out.println("  2. " + (recompensa2.isEmpty() ? "N/A" : recompensa2 + " | " + recompensa2Descripcion + " | " + String.format("%.2f", recompensa2Precio) + "€"));
                                    System.out.println("  3. " + (recompensa3.isEmpty() ? "N/A" : recompensa3 + " | " + recompensa3Descripcion + " | " + String.format("%.2f", recompensa3Precio) + "€"));

                                    System.out.println("\n  --- Opciones de Proyecto ---");
                                    System.out.println("  1. Modificar proyecto");
                                    System.out.println("  2. Eliminar proyecto");
                                    System.out.println("  3. Volver a Gestión de Proyectos");
                                    System.out.print("  Selecciona una opción: ");
                                    int subOpcionAdmin = Integer.parseInt(scanner.nextLine());

                                    if (subOpcionAdmin == 1) {
                                        System.out.print("¿Estás seguro de modificar el proyecto '" + proyectoNombre + "'? (s/n): ");
                                        if (scanner.nextLine().equalsIgnoreCase("s")) {
                                            System.out.println("\n--- MODIFICAR PROYECTO ---");
                                            System.out.print("  Nuevo nombre del proyecto (actual: " + proyectoNombre + "): ");
                                            proyectoNombre = scanner.nextLine();
                                            System.out.print("  Nueva descripción (actual: " + proyectoDescripcion + "): ");
                                            proyectoDescripcion = scanner.nextLine();
                                            System.out.print("  Nueva categoría (actual: " + proyectoCategoria + "): ");
                                            proyectoCategoria = scanner.nextLine();
                                            System.out.print("  Nueva cantidad necesaria (actual: " + String.format("%.2f", proyectoCantidadNecesaria) + "): ");
                                            proyectoCantidadNecesaria = Double.parseDouble(scanner.nextLine());
                                            System.out.print("  Nueva cantidad financiada (actual: " + String.format("%.2f", proyectoCantidadFinanciada) + "): ");
                                            proyectoCantidadFinanciada = Double.parseDouble(scanner.nextLine());
                                            System.out.print("  Nueva fecha de inicio (DD/MM/AAAA - actual: " + proyectoFechaInicio + "): ");
                                            proyectoFechaInicio = scanner.nextLine();
                                            System.out.print("  Nueva fecha de fin (DD/MM/AAAA - actual: " + proyectoFechaFin + "): ");
                                            proyectoFechaFin = scanner.nextLine();

                                            System.out.println("\n  --- Modificar Recompensas ---");
                                            System.out.print("  Nueva recompensa 1 (actual: " + recompensa1 + "): ");
                                            recompensa1 = scanner.nextLine();
                                            System.out.print("  Nueva descripción recompensa 1 (actual: " + recompensa1Descripcion + "): ");
                                            recompensa1Descripcion = scanner.nextLine();
                                            System.out.print("  Nuevo precio recompensa 1 (actual: " + String.format("%.2f", recompensa1Precio) + "): ");
                                            recompensa1Precio = Double.parseDouble(scanner.nextLine());

                                            System.out.print("  Nueva recompensa 2 (actual: " + recompensa2 + "): ");
                                            recompensa2 = scanner.nextLine();
                                            System.out.print("  Nueva descripción recompensa 2 (actual: " + recompensa2Descripcion + "): ");
                                            recompensa2Descripcion = scanner.nextLine();
                                            System.out.print("  Nuevo precio recompensa 2 (actual: " + String.format("%.2f", recompensa2Precio) + "): ");
                                            recompensa2Precio = Double.parseDouble(scanner.nextLine());

                                            System.out.print("  Nueva recompensa 3 (actual: " + recompensa3 + "): ");
                                            recompensa3 = scanner.nextLine();
                                            System.out.print("  Nueva descripción recompensa 3 (actual: " + recompensa3Descripcion + "): ");
                                            recompensa3Descripcion = scanner.nextLine();
                                            System.out.print("  Nuevo precio recompensa 3 (actual: " + String.format("%.2f", recompensa3Precio) + "): ");
                                            recompensa3Precio = Double.parseDouble(scanner.nextLine());
                                            System.out.println("\n¡Proyecto modificado exitosamente!");
                                        } else {
                                            System.out.println("Modificación cancelada.");
                                        }
                                    } else if (subOpcionAdmin == 2) {
                                        System.out.print("¿Estás seguro de eliminar el proyecto '" + proyectoNombre + "'? (s/n): ");
                                        if (scanner.nextLine().equalsIgnoreCase("s")) {
                                            proyectoNombre = "";
                                            proyectoDescripcion = "";
                                            proyectoCategoria = "";
                                            proyectoCantidadNecesaria = 0;
                                            proyectoCantidadFinanciada = 0;
                                            proyectoFechaInicio = "";
                                            proyectoFechaFin = "";
                                            recompensa1 = "";
                                            recompensa1Descripcion = "";
                                            recompensa1Precio = 0;
                                            recompensa2 = "";
                                            recompensa2Descripcion = "";
                                            recompensa2Precio = 0;
                                            recompensa3 = "";
                                            recompensa3Descripcion = "";
                                            recompensa3Precio = 0;
                                            System.out.println("¡Proyecto eliminado exitosamente!");
                                        } else {
                                            System.out.println("Eliminación cancelada.");
                                        }
                                    } else if (subOpcionAdmin != 3) {
                                        System.out.println("Opción no válida.");
                                    }
                                } else if (subOpcionProyecto != 2 && !proyectoNombre.isEmpty()) {
                                    System.out.println("Opción no válida.");
                                }
                                break;

                            case 3:
                                System.out.println("\n--- CONFIGURACIÓN ---");
                                System.out.println("1. Cambiar nombre de usuario");
                                System.out.println("2. Cambiar contraseña");
                                System.out.println("3. Volver al menú principal");
                                System.out.print("Selecciona una opción: ");
                                int subOpcion2 = Integer.parseInt(scanner.nextLine());

                                if (subOpcion2 == 1) {
                                    System.out.print("Ingrese el nombre de usuario a modificar (admin, gestor, inversor): ");
                                    String usuarioAModificar = scanner.nextLine();
                                    if (adminNombre.equals(usuarioAModificar)) {
                                        System.out.print("Nuevo nombre para 'admin': ");
                                        adminNombre = scanner.nextLine();
                                        System.out.println("Nombre de usuario 'admin' cambiado exitosamente.");
                                    } else if (gestorNombre.equals(usuarioAModificar)) {
                                        System.out.print("Nuevo nombre para 'gestor': ");
                                        gestorNombre = scanner.nextLine();
                                        System.out.println("Nombre de usuario 'gestor' cambiado exitosamente.");
                                    } else if (inversorNombre.equals(usuarioAModificar)) {
                                        System.out.print("Nuevo nombre para 'inversor': ");
                                        inversorNombre = scanner.nextLine();
                                        System.out.println("Nombre de usuario 'inversor' cambiado exitosamente.");
                                    } else {
                                        System.out.println("Usuario no reconocido. No se pudo cambiar el nombre.");
                                    }
                                } else if (subOpcion2 == 2) {
                                    System.out.print("Ingrese el nombre de usuario cuya contraseña desea modificar (admin, gestor, inversor): ");
                                    String usuarioAModificar = scanner.nextLine();
                                    if (adminNombre.equals(usuarioAModificar)) {
                                        System.out.print("Nueva contraseña para 'admin': ");
                                        adminContrasena = scanner.nextLine();
                                        System.out.println("Contraseña de 'admin' cambiada exitosamente.");
                                    } else if (gestorNombre.equals(usuarioAModificar)) {
                                        System.out.print("Nueva contraseña para 'gestor': ");
                                        gestorContrasena = scanner.nextLine();
                                        System.out.println("Contraseña de 'gestor' cambiada exitosamente.");
                                    } else if (inversorNombre.equals(usuarioAModificar)) {
                                        System.out.print("Nueva contraseña para 'inversor': ");
                                        inversorContrasena = scanner.nextLine();
                                        System.out.println("Contraseña de 'inversor' cambiada exitosamente.");
                                    } else {
                                        System.out.println("Usuario no reconocido. No se pudo cambiar la contraseña.");
                                    }
                                } else if (subOpcion2 != 3) {
                                    System.out.println("Opción no válida.");
                                }
                                break;

                            case 4:
                                System.out.println("Cerrando sesión de Administrador...");
                                logingAdmin = false;
                                break;
                            default:
                                System.out.println("Opción no válida. Por favor, intenta de nuevo.");
                                break;
                        }
                    } while (opcion != 4);
                }

                // --- Panel del Gestor ---
                if (logingGestor) {
                    System.out.println("\n====================================");
                    System.out.println("       BIENVENIDO, GESTOR           ");
                    System.out.println("====================================");
                    int opcion;
                    do {
                        System.out.println("\n--- MENÚ DE GESTOR ---");
                        System.out.println("1. Mis Proyectos");
                        System.out.println("2. Configuración");
                        System.out.println("3. Cerrar Sesión");
                        System.out.print("Selecciona una opción: ");

                        opcion = Integer.parseInt(scanner.nextLine());

                        switch (opcion) {
                            case 1:
                                System.out.println("\n--- MIS PROYECTOS ---");
                                if (proyectoNombre.isEmpty()) {
                                    System.out.println("No has creado ningún proyecto aún.");
                                    System.out.println("1. Crear nuevo proyecto");
                                    System.out.println("2. Volver al menú de Gestor");
                                } else {
                                    // Vista rápida con nombre, categoría, cantidad necesaria y financiada
                                    System.out.println("1. Ver y Gestionar: " + proyectoNombre + " | Categoría: " + proyectoCategoria + " | Necesario: " + String.format("%.2f", proyectoCantidadNecesaria) + "€ | Financiado: " + String.format("%.2f", proyectoCantidadFinanciada) + "€");
                                    System.out.println("2. Crear nuevo proyecto");
                                    System.out.println("3. Volver al menú de Gestor");
                                }
                                System.out.print("Selecciona una opción: ");
                                int subOpcion = Integer.parseInt(scanner.nextLine());


                                if (subOpcion == 1 && !proyectoNombre.isEmpty()) {
                                    System.out.println("\n--- DETALLES DEL PROYECTO: " + proyectoNombre.toUpperCase() + " ---");
                                    System.out.println("  Descripción: " + proyectoDescripcion);
                                    System.out.println("  Categoría: " + proyectoCategoria);
                                    System.out.println("  Cantidad necesaria: " + String.format("%.2f", proyectoCantidadNecesaria) + "€");
                                    System.out.println("  Cantidad financiada: " + String.format("%.2f", proyectoCantidadFinanciada) + "€");
                                    System.out.println("  Fecha de inicio: " + proyectoFechaInicio);
                                    System.out.println("  Fecha de fin: " + proyectoFechaFin);

                                    // --- Gráfico de progreso de financiación (ahora debajo de los detalles) ---
                                    double porcentaje = (proyectoCantidadNecesaria > 0) ? (proyectoCantidadFinanciada / proyectoCantidadNecesaria) * 100 : 0;
                                    int barras = (int) (porcentaje / 5);
                                    System.out.println("\n  Progreso de financiación: " + String.format("%.1f", porcentaje) + "%");
                                    System.out.print("  [");
                                    for (int i = 0; i < 20; i++) {
                                        if (i < barras) System.out.print("█");
                                        else System.out.print("░");
                                    }
                                    System.out.println("] " + String.format("%.2f", proyectoCantidadFinanciada) + "€ / " + String.format("%.2f", proyectoCantidadNecesaria) + "€");
                                    // --- Fin del gráfico ---

                                    System.out.println("\n  --- Recompensas Asociadas ---");
                                    System.out.println("  1. " + (recompensa1.isEmpty() ? "N/A" : recompensa1 + " | " + recompensa1Descripcion + " | " + String.format("%.2f", recompensa1Precio) + "€"));
                                    System.out.println("  2. " + (recompensa2.isEmpty() ? "N/A" : recompensa2 + " | " + recompensa2Descripcion + " | " + String.format("%.2f", recompensa2Precio) + "€"));
                                    System.out.println("  3. " + (recompensa3.isEmpty() ? "N/A" : recompensa3 + " | " + recompensa3Descripcion + " | " + String.format("%.2f", recompensa3Precio) + "€"));

                                    System.out.println("\n  --- Opciones de Proyecto ---");
                                    System.out.println("  1. Modificar proyecto");
                                    System.out.println("  2. Eliminar proyecto");
                                    System.out.println("  3. Volver a Mis Proyectos");
                                    System.out.print("  Selecciona una opción: ");
                                    int subOpcion2 = Integer.parseInt(scanner.nextLine());


                                    if (subOpcion2 == 1) {
                                        System.out.print("¿Estás seguro de modificar el proyecto '" + proyectoNombre + "'? (s/n): ");
                                        if (scanner.nextLine().equalsIgnoreCase("s")) {
                                            System.out.println("\n--- MODIFICAR PROYECTO ---");
                                            System.out.print("  Nuevo nombre del proyecto (actual: " + proyectoNombre + "): ");
                                            proyectoNombre = scanner.nextLine();
                                            System.out.print("  Nueva descripción (actual: " + proyectoDescripcion + "): ");
                                            proyectoDescripcion = scanner.nextLine();
                                            System.out.print("  Nueva categoría (actual: " + proyectoCategoria + "): ");
                                            proyectoCategoria = scanner.nextLine();
                                            System.out.print("  Nueva cantidad necesaria (actual: " + String.format("%.2f", proyectoCantidadNecesaria) + "): ");
                                            proyectoCantidadNecesaria = Double.parseDouble(scanner.nextLine());
                                            System.out.print("  Nueva cantidad financiada (actual: " + String.format("%.2f", proyectoCantidadFinanciada) + "): ");
                                            proyectoCantidadFinanciada = Double.parseDouble(scanner.nextLine());
                                            System.out.print("  Nueva fecha de inicio (DD/MM/AAAA - actual: " + proyectoFechaInicio + "): ");
                                            proyectoFechaInicio = scanner.nextLine();
                                            System.out.print("  Nueva fecha de fin (DD/MM/AAAA - actual: " + proyectoFechaFin + "): ");
                                            proyectoFechaFin = scanner.nextLine();

                                            System.out.println("\n  --- Modificar Recompensas ---");
                                            System.out.print("  Nueva recompensa 1 (actual: " + recompensa1 + "): ");
                                            recompensa1 = scanner.nextLine();
                                            System.out.print("  Nueva descripción recompensa 1 (actual: " + recompensa1Descripcion + "): ");
                                            recompensa1Descripcion = scanner.nextLine();
                                            System.out.print("  Nuevo precio recompensa 1 (actual: " + String.format("%.2f", recompensa1Precio) + "): ");
                                            recompensa1Precio = Double.parseDouble(scanner.nextLine());

                                            System.out.print("  Nueva recompensa 2 (actual: " + recompensa2 + "): ");
                                            recompensa2 = scanner.nextLine();
                                            System.out.print("  Nueva descripción recompensa 2 (actual: " + recompensa2Descripcion + "): ");
                                            recompensa2Descripcion = scanner.nextLine();
                                            System.out.print("  Nuevo precio recompensa 2 (actual: " + String.format("%.2f", recompensa2Precio) + "): ");
                                            recompensa2Precio = Double.parseDouble(scanner.nextLine());

                                            System.out.print("  Nueva recompensa 3 (actual: " + recompensa3 + "): ");
                                            recompensa3 = scanner.nextLine();
                                            System.out.print("  Nueva descripción recompensa 3 (actual: " + recompensa3Descripcion + "): ");
                                            recompensa3Descripcion = scanner.nextLine();
                                            System.out.print("  Nuevo precio recompensa 3 (actual: " + String.format("%.2f", recompensa3Precio) + "): ");
                                            recompensa3Precio = Double.parseDouble(scanner.nextLine());
                                            System.out.println("\n¡Proyecto modificado exitosamente!");
                                        } else {
                                            System.out.println("Modificación cancelada.");
                                        }
                                    } else if (subOpcion2 == 2) {
                                        System.out.print("¿Estás seguro de eliminar el proyecto '" + proyectoNombre + "'? (s/n): ");
                                        if (scanner.nextLine().equalsIgnoreCase("s")) {
                                            proyectoNombre = "";
                                            proyectoDescripcion = "";
                                            proyectoCategoria = "";
                                            proyectoCantidadNecesaria = 0;
                                            proyectoCantidadFinanciada = 0;
                                            proyectoFechaInicio = "";
                                            proyectoFechaFin = "";
                                            recompensa1 = "";
                                            recompensa1Descripcion = "";
                                            recompensa1Precio = 0;
                                            recompensa2 = "";
                                            recompensa2Descripcion = "";
                                            recompensa2Precio = 0;
                                            recompensa3 = "";
                                            recompensa3Descripcion = "";
                                            recompensa3Precio = 0;
                                            System.out.println("¡Proyecto eliminado exitosamente!");
                                        } else {
                                            System.out.println("Eliminación cancelada.");
                                        }
                                    } else if (subOpcion2 != 3) {
                                        System.out.println("Opción no válida.");
                                    }
                                } else if (subOpcion == 2 || (subOpcion == 1 && proyectoNombre.isEmpty())) {
                                    System.out.println("\n--- CREAR NUEVO PROYECTO ---");
                                    System.out.print("  Nombre del proyecto: ");
                                    proyectoNombre = scanner.nextLine();
                                    System.out.print("  Descripción del proyecto: ");
                                    proyectoDescripcion = scanner.nextLine();
                                    System.out.print("  Categoría del proyecto: ");
                                    proyectoCategoria = scanner.nextLine();
                                    System.out.print("  Cantidad necesaria para el proyecto (€): ");
                                    proyectoCantidadNecesaria = Double.parseDouble(scanner.nextLine());
                                    System.out.print("  Cantidad ya financiada (€): ");
                                    proyectoCantidadFinanciada = Double.parseDouble(scanner.nextLine());
                                    System.out.print("  Fecha de inicio (DD/MM/AAAA): ");
                                    proyectoFechaInicio = scanner.nextLine();
                                    System.out.print("  Fecha de fin (DD/MM/AAAA): ");
                                    proyectoFechaFin = scanner.nextLine();

                                    System.out.println("\n  --- Recompensas del Proyecto ---");
                                    System.out.print("  Nombre Recompensa 1: ");
                                    recompensa1 = scanner.nextLine();
                                    System.out.print("  Descripción Recompensa 1: ");
                                    recompensa1Descripcion = scanner.nextLine();
                                    System.out.print("  Precio Recompensa 1 (€): ");
                                    recompensa1Precio = Double.parseDouble(scanner.nextLine());

                                    System.out.print("  Nombre Recompensa 2: ");
                                    recompensa2 = scanner.nextLine();
                                    System.out.print("  Descripción Recompensa 2: ");
                                    recompensa2Descripcion = scanner.nextLine();
                                    System.out.print("  Precio Recompensa 2 (€): ");
                                    recompensa2Precio = Double.parseDouble(scanner.nextLine());

                                    System.out.print("  Nombre Recompensa 3: ");
                                    recompensa3 = scanner.nextLine();
                                    System.out.print("  Descripción Recompensa 3: ");
                                    recompensa3Descripcion = scanner.nextLine();
                                    System.out.print("  Precio Recompensa 3 (€): ");
                                    recompensa3Precio = Double.parseDouble(scanner.nextLine());
                                    System.out.println("\n¡Proyecto creado exitosamente!");
                                } else if (subOpcion != 3 && subOpcion != 2 && proyectoNombre.isEmpty()) {
                                    System.out.println("Opción no válida.");
                                }
                                break;

                            case 2:
                                System.out.println("\n--- CONFIGURACIÓN ---");
                                System.out.println("1. Cambiar contraseña");
                                System.out.println("2. Cambiar nombre de usuario");
                                System.out.println("3. Volver al menú de Gestor");
                                System.out.print("Selecciona una opción: ");
                                int subOpcion5 = Integer.parseInt(scanner.nextLine());

                                if (subOpcion5 == 1) {
                                    System.out.print("Ingrese la nueva contraseña para 'gestor': ");
                                    gestorContrasena = scanner.nextLine();
                                    System.out.println("Contraseña de 'gestor' cambiada exitosamente.");
                                } else if (subOpcion5 == 2) {
                                    System.out.print("Ingrese el nuevo nombre de usuario para 'gestor': ");
                                    gestorNombre = scanner.nextLine();
                                    System.out.println("Nombre de usuario 'gestor' cambiado exitosamente.");
                                } else if (subOpcion5 != 3) {
                                    System.out.println("Opción no válida.");
                                }
                                break;

                            case 3:
                                System.out.println("Cerrando sesión de Gestor...");
                                logingGestor = false;
                                break;
                            default:
                                System.out.println("Opción no válida. Por favor, intenta de nuevo.");
                                break;
                        }
                    } while (opcion != 3);
                }

                // --- Panel del Inversor ---
                if (logingInversor) {
                    System.out.println("\n====================================");
                    System.out.println("        BIENVENIDO, INVERSOR        ");
                    System.out.println("====================================");
                    int opcion;
                    do {
                        System.out.println("\n--- MENÚ DE INVERSOR ---");
                        System.out.println("1. Mis Inversiones");
                        System.out.println("2. Explorar Proyectos");
                        System.out.println("3. Cartera Digital");
                        System.out.println("4. Invitar a un Amigo");
                        System.out.println("5. Configuración");
                        System.out.println("6. Cerrar Sesión");
                        System.out.print("Selecciona una opción: ");

                        opcion = Integer.parseInt(scanner.nextLine());

                        switch (opcion) {
                            case 1:
                                System.out.println("\n--- MIS INVERSIONES ---");
                                if (haInvertidoInversor) {
                                    System.out.println("1. Proyecto: " + proyectoNombre + " | Categoría: " + proyectoCategoria + " | Invertido: " + String.format("%.2f", inversionInversorProyecto) + "€");
                                } else {
                                    System.out.println("Aún no has realizado ninguna inversión.");
                                }
                                System.out.println("2. Volver al menú de Inversor");
                                System.out.print("Selecciona una opción: ");
                                int subOpcion = Integer.parseInt(scanner.nextLine());

                                if (subOpcion == 1 && haInvertidoInversor) {
                                    System.out.println("\n--- DETALLES DE TU INVERSIÓN EN: " + proyectoNombre.toUpperCase() + " ---");
                                    System.out.println("  Descripción del Proyecto: " + proyectoDescripcion);
                                    System.out.println("  Categoría del Proyecto: " + proyectoCategoria);
                                    System.out.println("  Cantidad Necesaria del Proyecto: " + String.format("%.2f", proyectoCantidadNecesaria) + "€");
                                    System.out.println("  Cantidad Financiada del Proyecto: " + String.format("%.2f", proyectoCantidadFinanciada) + "€");
                                    System.out.println("  Tu Inversión en este Proyecto: " + String.format("%.2f", inversionInversorProyecto) + "€");
                                    System.out.println("  Fecha de inicio del Proyecto: " + proyectoFechaInicio);
                                    System.out.println("  Fecha de fin del Proyecto: " + proyectoFechaFin);

                                    // --- Gráfico de progreso de financiación (ahora debajo de los detalles) ---
                                    double porcentaje = (proyectoCantidadNecesaria > 0) ? (proyectoCantidadFinanciada / proyectoCantidadNecesaria) * 100 : 0;
                                    int barras = (int) (porcentaje / 5);
                                    System.out.println("\n  Progreso de financiación del proyecto: " + String.format("%.1f", porcentaje) + "%");
                                    System.out.print("  [");
                                    for (int i = 0; i < 20; i++) {
                                        if (i < barras) System.out.print("█");
                                        else System.out.print("░");
                                    }
                                    System.out.println("] " + String.format("%.2f", proyectoCantidadFinanciada) + "€ / " + String.format("%.2f", proyectoCantidadNecesaria) + "€");
                                    // --- Fin del gráfico ---

                                    System.out.println("\n  --- Recompensas del Proyecto ---");
                                    System.out.println("  1. " + (recompensa1.isEmpty() ? "N/A" : recompensa1 + " | " + recompensa1Descripcion + " | " + String.format("%.2f", recompensa1Precio) + "€"));
                                    System.out.println("  2. " + (recompensa2.isEmpty() ? "N/A" : recompensa2 + " | " + recompensa2Descripcion + " | " + String.format("%.2f", recompensa2Precio) + "€"));
                                    System.out.println("  3. " + (recompensa3.isEmpty() ? "N/A" : recompensa3 + " | " + recompensa3Descripcion + " | " + String.format("%.2f", recompensa3Precio) + "€"));
                                    System.out.println("\n  Volver al menú anterior (Pulsa Enter)...");
                                    scanner.nextLine();
                                } else if (subOpcion != 2) {
                                    System.out.println("Opción no válida.");
                                }
                                break;
                            case 2:
                                System.out.println("\n--- EXPLORAR PROYECTOS ---");
                                if (proyectoNombre.isEmpty()) {
                                    System.out.println("No hay proyectos disponibles en este momento. Vuelve más tarde.");
                                } else {
                                    // Vista rápida con nombre, categoría, cantidad necesaria y financiada
                                    System.out.println("1. Ver detalles de: " + proyectoNombre + " | Categoría: " + proyectoCategoria + " | Necesario: " + String.format("%.2f", proyectoCantidadNecesaria) + "€ | Financiado: " + String.format("%.2f", proyectoCantidadFinanciada) + "€");
                                }
                                System.out.println("2. Volver al menú de Inversor");
                                System.out.print("Selecciona una opción: ");
                                int subOpcionProyectoInversor = Integer.parseInt(scanner.nextLine());


                                if (subOpcionProyectoInversor == 1 && !proyectoNombre.isEmpty()) {
                                    System.out.println("\n--- DETALLES DEL PROYECTO: " + proyectoNombre.toUpperCase() + " ---");
                                    System.out.println("  Descripción: " + proyectoDescripcion);
                                    System.out.println("  Categoría: " + proyectoCategoria);
                                    System.out.println("  Cantidad necesaria: " + String.format("%.2f", proyectoCantidadNecesaria) + "€");
                                    System.out.println("  Cantidad financiada: " + String.format("%.2f", proyectoCantidadFinanciada) + "€");
                                    System.out.println("  Fecha de inicio: " + proyectoFechaInicio);
                                    System.out.println("  Fecha de fin: " + proyectoFechaFin);

                                    // --- Gráfico de progreso de financiación (ahora debajo de los detalles) ---
                                    double porcentaje = (proyectoCantidadNecesaria > 0) ? (proyectoCantidadFinanciada / proyectoCantidadNecesaria) * 100 : 0;
                                    int barras = (int) (porcentaje / 5);
                                    System.out.println("\n  Progreso de financiación: " + String.format("%.1f", porcentaje) + "%");
                                    System.out.print("  [");
                                    for (int i = 0; i < 20; i++) {
                                        if (i < barras) System.out.print("█");
                                        else System.out.print("░");
                                    }
                                    System.out.println("] " + String.format("%.2f", proyectoCantidadFinanciada) + "€ / " + String.format("%.2f", proyectoCantidadNecesaria) + "€");
                                    // --- Fin del gráfico ---

                                    System.out.println("\n  --- Recompensas Disponibles ---");
                                    System.out.println("  1. " + (recompensa1.isEmpty() ? "N/A" : recompensa1 + " | " + recompensa1Descripcion + " | " + String.format("%.2f", recompensa1Precio) + "€"));
                                    System.out.println("  2. " + (recompensa2.isEmpty() ? "N/A" : recompensa2 + " | " + recompensa2Descripcion + " | " + String.format("%.2f", recompensa2Precio) + "€"));
                                    System.out.println("  3. " + (recompensa3.isEmpty() ? "N/A" : recompensa3 + " | " + recompensa3Descripcion + " | " + String.format("%.2f", recompensa3Precio) + "€"));

                                    System.out.println("\n  --- Opciones de Inversión ---");
                                    System.out.println("  1. Realizar Inversión");
                                    System.out.println("  2. Volver a Explorar Proyectos");
                                    System.out.print("  Selecciona una opción: ");
                                    int subOpcionInvertir = Integer.parseInt(scanner.nextLine());


                                    if (subOpcionInvertir == 1) {
                                        if (saldoInversor <= 0) {
                                            System.out.println("Tu cartera digital está vacía. Por favor, recarga saldo antes de invertir.");
                                            System.out.println("Volviendo al menú de Inversor...");
                                        } else if (haInvertidoInversor) {
                                            System.out.println("Ya has invertido en este proyecto. No puedes invertir de nuevo.");
                                        } else {
                                            System.out.print("  Ingrese la cantidad a invertir en '" + proyectoNombre + "': ");
                                            double cantidadAInvertir = Double.parseDouble(scanner.nextLine());


                                            if (cantidadAInvertir > 0 && cantidadAInvertir <= saldoInversor) {
                                                if (proyectoCantidadFinanciada + cantidadAInvertir <= proyectoCantidadNecesaria) {
                                                    proyectoCantidadFinanciada += cantidadAInvertir;
                                                    inversionInversorProyecto = cantidadAInvertir;
                                                    saldoInversor -= cantidadAInvertir;
                                                    haInvertidoInversor = true;
                                                    System.out.println("¡Inversión de " + String.format("%.2f", cantidadAInvertir) + "€ realizada con éxito en '" + proyectoNombre + "'!");
                                                    System.out.println("Tu saldo actual es: " + String.format("%.2f", saldoInversor) + "€");
                                                } else {
                                                    System.out.println("La cantidad excede lo necesario para el proyecto. Por favor, invierte una cantidad menor o igual a " + String.format("%.2f", (proyectoCantidadNecesaria - proyectoCantidadFinanciada)) + "€.");
                                                }
                                            } else if (cantidadAInvertir > saldoInversor) {
                                                System.out.println("Saldo insuficiente. Tu saldo actual es " + String.format("%.2f", saldoInversor) + "€.");
                                            } else {
                                                System.out.println("La cantidad a invertir debe ser positiva.");
                                            }
                                        }
                                    } else if (subOpcionInvertir != 2) {
                                        System.out.println("Opción no válida.");
                                    }
                                } else if (subOpcionProyectoInversor != 2 && !proyectoNombre.isEmpty()) {
                                    System.out.println("Opción no válida.");
                                }
                                break;
                            case 3:
                                System.out.println("\n--- CARTERA DIGITAL ---");
                                System.out.println("Tu saldo actual es: " + String.format("%.2f", saldoInversor) + "€");
                                System.out.println("\nOpciones:");
                                System.out.println("1. Recargar Saldo");
                                System.out.println("2. Volver al menú de Inversor");
                                System.out.print("Selecciona una opción: ");
                                int subOpcionCartera = Integer.parseInt(scanner.nextLine());


                                if (subOpcionCartera == 1) {
                                    System.out.print("Ingrese la cantidad a recargar: ");
                                    double cantidadRecarga = Double.parseDouble(scanner.nextLine());

                                    if (cantidadRecarga > 0) {
                                        saldoInversor += cantidadRecarga;
                                        System.out.println("¡Recarga de " + String.format("%.2f", cantidadRecarga) + "€ realizada con éxito!");
                                        System.out.println("Tu nuevo saldo es: " + String.format("%.2f", saldoInversor) + "€");
                                    } else {
                                        System.out.println("La cantidad a recargar debe ser positiva.");
                                    }
                                } else if (subOpcionCartera != 2) {
                                    System.out.println("Opción no válida.");
                                }
                                break;
                            case 4:
                                System.out.println("\n--- INVITAR A UN AMIGO ---");
                                if (amigosInversor.isEmpty()) {
                                    System.out.println("Aún no has invitado a ningún amigo.");
                                } else {
                                    System.out.println("Has invitado a: " + amigosInversor);
                                }
                                System.out.print("Ingrese el nombre de un amigo para invitar: ");
                                amigosInversor = scanner.nextLine();
                                System.out.println("¡Has invitado a '" + amigosInversor + "' con éxito!");
                                System.out.println("Volviendo al menú de Inversor...");
                                break;
                            case 5:
                                System.out.println("\n--- CONFIGURACIÓN ---");
                                System.out.println("1. Cambiar contraseña");
                                System.out.println("2. Cambiar nombre de usuario");
                                System.out.println("3. Volver al menú de Inversor");
                                System.out.print("Selecciona una opción: ");
                                int subOpcionConfigInversor = Integer.parseInt(scanner.nextLine());

                                if (subOpcionConfigInversor == 1) {
                                    System.out.print("Ingrese la nueva contraseña para 'inversor': ");
                                    inversorContrasena = scanner.nextLine();
                                    System.out.println("Contraseña de 'inversor' cambiada exitosamente.");
                                } else if (subOpcionConfigInversor == 2) {
                                    System.out.print("Ingrese el nuevo nombre de usuario para 'inversor': ");
                                    inversorNombre = scanner.nextLine();
                                    System.out.println("Nombre de usuario 'inversor' cambiado exitosamente.");
                                } else if (subOpcionConfigInversor != 3) {
                                    System.out.println("Opción no válida.");
                                }
                                break;
                            case 6:
                                System.out.println("Cerrando sesión de Inversor...");
                                logingInversor = false;
                                break;
                            default:
                                System.out.println("Opción no válida. Por favor, intenta de nuevo.");
                                break;
                        }
                    } while (opcion != 6);
                }
            } else if (opcion0 != 2) {
                System.out.println("Opción no válida. Por favor, selecciona '1' para iniciar sesión o '2' para salir.");
            }
        }
        System.out.println("\n------------------------------------");
        System.out.println("     ¡Gracias por usar la aplicación!    ");
        System.out.println("------------------------------------");
        scanner.close();
    }
}
