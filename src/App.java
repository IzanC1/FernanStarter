import java.util.Scanner;
import utilidades.funcionesCorreos;

public class App {

    // --- Variables de Autenticación ---
    private static String adminNombre = "admin";
    private static String adminContrasena = "admin";
    private static boolean logingAdmin = false;
    private static String adminCorreo = "izan.cano.0805@fernando3martos.com";

    private static String gestorNombre = "gestor";
    private static String gestorContrasena = "gestor";
    private static int intentosGestor = 3;
    private static boolean logingGestor = false;
    private static boolean bloqueadoGestor = false;
    private static String gestorCorreo = "izan.cano.0805@fernando3martos.com";

    private static String inversorNombre = "inversor";
    private static String inversorContrasena = "inversor";
    private static int intentosInversor = 3;
    private static boolean logingInversor = false;
    private static boolean bloqueadoInversor = false;
    private static String inversorCorreo = "izan.cano.0805@fernando3martos.com";

    // --- Variables del Proyecto ---
    private static String proyectoNombre = "";
    private static String proyectoDescripcion = "";
    private static String proyectoCategoria = "";
    private static double proyectoCantidadNecesaria = 0;
    private static double proyectoCantidadFinanciada = 0;
    private static String proyectoFechaInicio = "";
    private static String proyectoFechaFin = "";

    // --- Variables de Recompensas (para el proyecto) ---
    private static String recompensa1 = "";
    private static String recompensa1Descripcion = "";
    private static double recompensa1Precio = 0;
    private static String recompensa2 = "";
    private static String recompensa2Descripcion = "";
    private static double recompensa2Precio = 0;
    private static String recompensa3 = "";
    private static String recompensa3Descripcion = "";
    private static double recompensa3Precio = 0;

    // --- Variables del Inversor ---
    private static boolean haInvertidoInversor = false;
    private static double inversionInversorProyecto = 0;
    private static double saldoInversor = 0;
    private static String amigosInversor = "";

    // --- Inicialización del Scanner para entrada de usuario ---
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion0 = 0;
        // --- Bucle principal del programa (Login o Salir) ---
        while (opcion0 != 2) {
            mostrarMenuPrincipal();
            opcion0 = solicitarEntero("Por favor, selecciona una opción: ");

            if (opcion0 == 1) {
                iniciarSesion();
            } else if (opcion0 != 2) {
                mostrarMensaje("Opción no válida. Por favor, selecciona '1' para iniciar sesión o '2' para salir.");
            }
        }
        mostrarMensaje("\n------------------------------------");
        mostrarMensaje("     ¡Gracias por usar la aplicación!    ");
        mostrarMensaje("------------------------------------");
        scanner.close();
    }

    // --- Funciones de Interfaz de Usuario (UI) ---

    /**
     * Muestra un mensaje en la consola.
     * @param mensaje El mensaje a mostrar.
     */
    public static void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Solicita una cadena de texto al usuario.
     * @param mensaje El mensaje a mostrar antes de solicitar la entrada.
     * @return La cadena de texto introducida por el usuario.
     */
    public static String solicitarCadena(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    /**
     * Solicita un número entero al usuario.
     * @param mensaje El mensaje a mostrar antes de solicitar la entrada.
     * @return El número entero introducido por el usuario.
     */
    public static int solicitarEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                mostrarMensaje("Entrada no válida. Por favor, ingresa un número entero.");
            }
        }
    }

    /**
     * Solicita un número decimal (double) al usuario.
     * @param mensaje El mensaje a mostrar antes de solicitar la entrada.
     * @return El número decimal introducido por el usuario.
     */
    public static double solicitarDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                mostrarMensaje("Entrada no válida. Por favor, ingresa un número decimal.");
            }
        }
    }

    /**
     * Muestra el menú principal de la aplicación.
     */
    public static void mostrarMenuPrincipal() {
        mostrarMensaje("\n------------------------------------");
        mostrarMensaje("          MENÚ PRINCIPAL          ");
        mostrarMensaje("------------------------------------");
        mostrarMensaje("1. Iniciar Sesión");
        mostrarMensaje("2. Salir");
    }

    /**
     * Maneja el proceso de inicio de sesión con autenticación de doble factor (2FA).
     */
    public static void iniciarSesion() {
        String usuario = "";
        String contrasena = "";
        String correoUsuario = "";
        boolean credencialesCorrectas = false;
        String tipoUsuario = "";

        // Bucle para solicitar usuario y contraseña hasta que sean correctos
        do {
            mostrarMensaje("\n--- INICIO DE SESIÓN ---");
            usuario = solicitarCadena("Usuario: ");
            contrasena = solicitarCadena("Contraseña: ");

            // --- Lógica de autenticación del Administrador ---
            if (usuario.equals(adminNombre)) {
                if (contrasena.equals(adminContrasena)) {
                    credencialesCorrectas = true;
                    correoUsuario = adminCorreo;
                    tipoUsuario = "admin";
                } else {
                    mostrarMensaje("Contraseña incorrecta para el usuario 'admin'.");
                }
            }
            // --- Lógica de autenticación del Gestor ---
            else if (usuario.equals(gestorNombre)) {
                if (bloqueadoGestor) {
                    mostrarMensaje("¡Acceso denegado! Tu usuario ha sido bloqueado.");
                } else {
                    if (contrasena.equals(gestorContrasena)) {
                        credencialesCorrectas = true;
                        correoUsuario = gestorCorreo;
                        tipoUsuario = "gestor";
                    } else {
                        mostrarMensaje("Contraseña incorrecta para el usuario 'gestor'.");
                        intentosGestor--;
                        mostrarMensaje("Intentos restantes: " + intentosGestor);
                        if (intentosGestor == 0) {
                            mostrarMensaje("¡Demasiados intentos fallidos! El usuario 'gestor' ha sido bloqueado.");
                            bloqueadoGestor = true;
                        }
                    }
                }
            }
            // --- Lógica de autenticación del Inversor ---
            else if (usuario.equals(inversorNombre)) {
                if (bloqueadoInversor) {
                    mostrarMensaje("¡Acceso denegado! Tu usuario ha sido bloqueado.");
                } else {
                    if (contrasena.equals(inversorContrasena)) {
                        credencialesCorrectas = true;
                        correoUsuario = inversorCorreo;
                        tipoUsuario = "inversor";
                    } else {
                        mostrarMensaje("Contraseña incorrecta para el usuario 'inversor'.");
                        intentosInversor--;
                        mostrarMensaje("Intentos restantes: " + intentosInversor);
                        if (intentosInversor == 0) {
                            mostrarMensaje("¡Demasiados intentos fallidos! El usuario 'inversor' ha sido bloqueado.");
                            bloqueadoInversor = true;
                        }
                    }
                }
            } else {
                mostrarMensaje("Usuario no reconocido. Por favor, intenta de nuevo.");
            }

            if (!credencialesCorrectas && (bloqueadoGestor || bloqueadoInversor)) {
                // Si está bloqueado, no seguir pidiendo credenciales
                break;
            }

        } while (!credencialesCorrectas);

        // Si las credenciales son correctas, proceder con el 2FA
        if (credencialesCorrectas) {
            mostrarMensaje("Credenciales correctas. Enviando código de verificación a " + correoUsuario + "...");
            String otpEnviado = funcionesCorreos.enviarCodigoDobleFactor(usuario, correoUsuario);

            if (otpEnviado != null) {
                String otpIngresado = solicitarCadena("Por favor, ingresa el código de 4 dígitos enviado a tu correo: ");
                if (funcionesCorreos.verificarCodigoDobleFactor(usuario, otpIngresado)) {
                    mostrarMensaje("¡Verificación de doble factor exitosa! Acceso concedido.");
                    // Establecer el estado de login según el tipo de usuario
                    switch (tipoUsuario) {
                        case "admin":
                            logingAdmin = true;
                            panelAdministrador();
                            break;
                        case "gestor":
                            logingGestor = true;
                            panelGestor();
                            break;
                        case "inversor":
                            logingInversor = true;
                            panelInversor();
                            break;
                    }
                } else {
                    mostrarMensaje("Código OTP incorrecto o caducado. Acceso denegado.");
                    // Si el OTP es incorrecto, resetear los intentos si aplica (Gestor/Inversor)
                    if ("gestor".equals(tipoUsuario)) {
                        intentosGestor = 3; // Resetear intentos al fallar el OTP
                        bloqueadoGestor = false;
                    } else if ("inversor".equals(tipoUsuario)) {
                        intentosInversor = 3; // Resetear intentos al fallar el OTP
                        bloqueadoInversor = false;
                    }
                }
            } else {
                mostrarMensaje("No se pudo enviar el código de doble factor. Por favor, verifica la configuración de correo.");
                // Si el correo no se envía, también se debería denegar el acceso.
                // Resetear estados si se había marcado como correcto para evitar bucles.
                credencialesCorrectas = false;
                logingAdmin = false;
                logingGestor = false;
                logingInversor = false;
            }
        }
        // Si no se llegó a credencialesCorrectas, o si el 2FA falló, la sesión no se inicia.
        // El bucle principal del main se encargará de mostrar el menú de nuevo.
    }


    /**
     * Muestra el panel y maneja las opciones del Administrador.
     */
    public static void panelAdministrador() {
        mostrarMensaje("\n====================================");
        mostrarMensaje("     BIENVENIDO, ADMINISTRADOR      ");
        mostrarMensaje("====================================");
        int opcion;
        do {
            mostrarMenuAdministrador();
            opcion = solicitarEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1:
                    gestionarUsuarios();
                    break;
                case 2:
                    gestionarProyectosAdmin();
                    break;
                case 3:
                    configuracionAdmin();
                    break;
                case 4:
                    mostrarMensaje("Cerrando sesión de Administrador...");
                    logingAdmin = false;
                    break;
                default:
                    mostrarMensaje("Opción no válida. Por favor, intenta de nuevo.");
                    break;
            }
        } while (opcion != 4);
    }

    /**
     * Muestra el menú del Administrador.
     */
    public static void mostrarMenuAdministrador() {
        mostrarMensaje("\n--- MENÚ DE ADMINISTRACIÓN ---");
        mostrarMensaje("1. Gestión de Usuarios");
        mostrarMensaje("2. Gestión de Proyectos");
        mostrarMensaje("3. Configuración");
        mostrarMensaje("4. Cerrar Sesión");
    }

    /**
     * Maneja la gestión de usuarios por parte del Administrador.
     */
    public static void gestionarUsuarios() {
        mostrarMensaje("\n--- GESTIÓN DE USUARIOS ---");
        mostrarMensaje("1. Bloquear usuario");
        mostrarMensaje("2. Desbloquear usuario");
        mostrarMensaje("3. Volver al menú principal");
        int subOpcion = solicitarEntero("Selecciona una opción: ");

        if (subOpcion == 1) {
            String usuarioABloquear = solicitarCadena("Ingrese el nombre del usuario a bloquear ('gestor' o 'inversor'): ");
            if (usuarioABloquear.equals(gestorNombre)) {
                bloqueadoGestor = true;
                mostrarMensaje("Usuario '" + gestorNombre + "' bloqueado exitosamente.");
            } else if (usuarioABloquear.equals(inversorNombre)) {
                bloqueadoInversor = true;
                mostrarMensaje("Usuario '" + inversorNombre + "' bloqueado exitosamente.");
            } else {
                mostrarMensaje("Usuario no reconocido o no bloqueable.");
            }
        } else if (subOpcion == 2) {
            String usuarioADesbloquear = solicitarCadena("Ingrese el nombre del usuario a desbloquear ('gestor' o 'inversor'): ");
            if (usuarioADesbloquear.equals(gestorNombre)) {
                bloqueadoGestor = false;
                intentosGestor = 3;
                mostrarMensaje("Usuario '" + gestorNombre + "' desbloqueado exitosamente. Intentos reiniciados.");
            } else if (usuarioADesbloquear.equals(inversorNombre)) {
                bloqueadoInversor = false;
                intentosInversor = 3;
                mostrarMensaje("Usuario '" + inversorNombre + "' desbloqueado exitosamente. Intentos reiniciados.");
            } else {
                mostrarMensaje("Usuario no reconocido o no desbloqueable.");
            }
        } else if (subOpcion != 3) {
            mostrarMensaje("Opción no válida.");
        }
    }

    /**
     * Muestra los detalles de un proyecto.
     */
    public static void mostrarDetallesProyecto() {
        mostrarMensaje("\n--- DETALLES DEL PROYECTO: " + proyectoNombre.toUpperCase() + " ---");
        mostrarMensaje("  Descripción: " + proyectoDescripcion);
        mostrarMensaje("  Categoría: " + proyectoCategoria);
        mostrarMensaje("  Cantidad necesaria: " + String.format("%.2f", proyectoCantidadNecesaria) + "€");
        mostrarMensaje("  Cantidad financiada: " + String.format("%.2f", proyectoCantidadFinanciada) + "€");
        mostrarMensaje("  Fecha de inicio: " + proyectoFechaInicio);
        mostrarMensaje("  Fecha de fin: " + proyectoFechaFin);

        mostrarProgresoFinanciacion();

        mostrarMensaje("\n  --- Recompensas Asociadas ---");
        mostrarMensaje("  1. " + (recompensa1.isEmpty() ? "N/A" : recompensa1 + " | " + recompensa1Descripcion + " | " + String.format("%.2f", recompensa1Precio) + "€"));
        mostrarMensaje("  2. " + (recompensa2.isEmpty() ? "N/A" : recompensa2 + " | " + recompensa2Descripcion + " | " + String.format("%.2f", recompensa2Precio) + "€"));
        mostrarMensaje("  3. " + (recompensa3.isEmpty() ? "N/A" : recompensa3 + " | " + recompensa3Descripcion + " | " + String.format("%.2f", recompensa3Precio) + "€"));
    }

    /**
     * Muestra el progreso de financiación de un proyecto.
     */
    public static void mostrarProgresoFinanciacion() {
        double porcentaje = (proyectoCantidadNecesaria > 0) ? (proyectoCantidadFinanciada / proyectoCantidadNecesaria) * 100 : 0;
        int barras = (int) (porcentaje / 5);
        mostrarMensaje("\n  Progreso de financiación: " + String.format("%.1f", porcentaje) + "%");
        System.out.print("  [");
        for (int i = 0; i < 20; i++) {
            if (i < barras) System.out.print("█");
            else System.out.print("░");
        }
        mostrarMensaje("] " + String.format("%.2f", proyectoCantidadFinanciada) + "€ / " + String.format("%.2f", proyectoCantidadNecesaria) + "€");
    }

    /**
     * Maneja la gestión de proyectos por parte del Administrador.
     */
    public static void gestionarProyectosAdmin() {
        mostrarMensaje("\n--- GESTIÓN DE PROYECTOS ---");
        if (proyectoNombre.isEmpty()) {
            mostrarMensaje("No hay proyectos creados aún.");
        } else {
            mostrarMensaje("1. Ver detalles de: " + proyectoNombre + " | Categoría: " + proyectoCategoria + " | Necesario: " + String.format("%.2f", proyectoCantidadNecesaria) + "€ | Financiado: " + String.format("%.2f", proyectoCantidadFinanciada) + "€");
        }
        mostrarMensaje("2. Volver al menú principal");
        int subOpcionProyecto = solicitarEntero("Selecciona una opción: ");

        if (subOpcionProyecto == 1 && !proyectoNombre.isEmpty()) {
            mostrarDetallesProyecto();
            mostrarMensaje("\n  --- Opciones de Proyecto ---");
            mostrarMensaje("  1. Modificar proyecto");
            mostrarMensaje("  2. Eliminar proyecto");
            mostrarMensaje("  3. Volver a Gestión de Proyectos");
            int subOpcionAdmin = solicitarEntero("  Selecciona una opción: ");

            if (subOpcionAdmin == 1) {
                modificarProyecto();
            } else if (subOpcionAdmin == 2) {
                eliminarProyecto();
            } else if (subOpcionAdmin != 3) {
                mostrarMensaje("Opción no válida.");
            }
        } else if (subOpcionProyecto != 2 && !proyectoNombre.isEmpty()) {
            mostrarMensaje("Opción no válida.");
        }
    }

    /**
     * Modifica un proyecto existente.
     */
    public static void modificarProyecto() {
        if (solicitarCadena("¿Estás seguro de modificar el proyecto '" + proyectoNombre + "'? (s/n): ").equalsIgnoreCase("s")) {
            mostrarMensaje("\n--- MODIFICAR PROYECTO ---");
            proyectoNombre = solicitarCadena("  Nuevo nombre del proyecto (actual: " + proyectoNombre + "): ");
            proyectoDescripcion = solicitarCadena("  Nueva descripción (actual: " + proyectoDescripcion + "): ");
            proyectoCategoria = solicitarCadena("  Nueva categoría (actual: " + proyectoCategoria + "): ");
            proyectoCantidadNecesaria = solicitarDouble("  Nueva cantidad necesaria (actual: " + String.format("%.2f", proyectoCantidadNecesaria) + "): ");
            proyectoCantidadFinanciada = solicitarDouble("  Nueva cantidad financiada (actual: " + String.format("%.2f", proyectoCantidadFinanciada) + "): ");
            proyectoFechaInicio = solicitarCadena("  Nueva fecha de inicio (DD/MM/AAAA - actual: " + proyectoFechaInicio + "): ");
            proyectoFechaFin = solicitarCadena("  Nueva fecha de fin (DD/MM/AAAA - actual: " + proyectoFechaFin + "): ");

            modificarRecompensas();
            mostrarMensaje("\n¡Proyecto modificado exitosamente!");
        } else {
            mostrarMensaje("Modificación cancelada.");
        }
    }

    /**
     * Modifica las recompensas de un proyecto.
     */
    public static void modificarRecompensas() {
        mostrarMensaje("\n  --- Modificar Recompensas ---");
        recompensa1 = solicitarCadena("  Nueva recompensa 1 (actual: " + recompensa1 + "): ");
        recompensa1Descripcion = solicitarCadena("  Nueva descripción recompensa 1 (actual: " + recompensa1Descripcion + "): ");
        recompensa1Precio = solicitarDouble("  Nuevo precio recompensa 1 (actual: " + String.format("%.2f", recompensa1Precio) + "): ");

        recompensa2 = solicitarCadena("  Nueva recompensa 2 (actual: " + recompensa2 + "): ");
        recompensa2Descripcion = solicitarCadena("  Nueva descripción recompensa 2 (actual: " + recompensa2Descripcion + "): ");
        recompensa2Precio = solicitarDouble("  Nuevo precio recompensa 2 (actual: " + String.format("%.2f", recompensa2Precio) + "): ");

        recompensa3 = solicitarCadena("  Nueva recompensa 3 (actual: " + recompensa3 + "): ");
        recompensa3Descripcion = solicitarCadena("  Nueva descripción recompensa 3 (actual: " + recompensa3Descripcion + "): ");
        recompensa3Precio = solicitarDouble("  Nuevo precio recompensa 3 (actual: " + String.format("%.2f", recompensa3Precio) + "): ");
    }

    /**
     * Elimina un proyecto existente.
     */
    public static void eliminarProyecto() {
        if (solicitarCadena("¿Estás seguro de eliminar el proyecto '" + proyectoNombre + "'? (s/n): ").equalsIgnoreCase("s")) {
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
            mostrarMensaje("¡Proyecto eliminado exitosamente!");
        } else {
            mostrarMensaje("Eliminación cancelada.");
        }
    }

    /**
     * Maneja la configuración por parte del Administrador.
     */
    public static void configuracionAdmin() {
        mostrarMensaje("\n--- CONFIGURACIÓN ---");
        mostrarMensaje("1. Cambiar nombre de usuario");
        mostrarMensaje("2. Cambiar contraseña");
        mostrarMensaje("3. Volver al menú principal");
        int subOpcion = solicitarEntero("Selecciona una opción: ");

        if (subOpcion == 1) {
            String usuarioAModificar = solicitarCadena("Ingrese el nombre de usuario a modificar (admin, gestor, inversor): ");
            if (adminNombre.equals(usuarioAModificar)) {
                adminNombre = solicitarCadena("Nuevo nombre para 'admin': ");
                mostrarMensaje("Nombre de usuario 'admin' cambiado exitosamente.");
            } else if (gestorNombre.equals(usuarioAModificar)) {
                gestorNombre = solicitarCadena("Nuevo nombre para 'gestor': ");
                mostrarMensaje("Nombre de usuario 'gestor' cambiado exitosamente.");
            } else if (inversorNombre.equals(usuarioAModificar)) {
                inversorNombre = solicitarCadena("Nuevo nombre para 'inversor': ");
                mostrarMensaje("Nombre de usuario 'inversor' cambiado exitosamente.");
            } else {
                mostrarMensaje("Usuario no reconocido. No se pudo cambiar el nombre.");
            }
        } else if (subOpcion == 2) {
            String usuarioAModificar = solicitarCadena("Ingrese el nombre de usuario cuya contraseña desea modificar (admin, gestor, inversor): ");
            if (adminNombre.equals(usuarioAModificar)) {
                adminContrasena = solicitarCadena("Nueva contraseña para 'admin': ");
                mostrarMensaje("Contraseña de 'admin' cambiada exitosamente.");
            } else if (gestorNombre.equals(usuarioAModificar)) {
                gestorContrasena = solicitarCadena("Nueva contraseña para 'gestor': ");
                mostrarMensaje("Contraseña de 'gestor' cambiada exitosamente.");
            } else if (inversorNombre.equals(usuarioAModificar)) {
                inversorContrasena = solicitarCadena("Nueva contraseña para 'inversor': ");
                mostrarMensaje("Contraseña de 'inversor' cambiada exitosamente.");
            } else {
                mostrarMensaje("Usuario no reconocido. No se pudo cambiar la contraseña.");
            }
        } else if (subOpcion != 3) {
            mostrarMensaje("Opción no válida.");
        }
    }

    /**
     * Muestra el panel y maneja las opciones del Gestor.
     */
    public static void panelGestor() {
        mostrarMensaje("\n====================================");
        mostrarMensaje("       BIENVENIDO, GESTOR           ");
        mostrarMensaje("====================================");
        int opcion;
        do {
            mostrarMenuGestor();
            opcion = solicitarEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1:
                    misProyectosGestor();
                    break;
                case 2:
                    configuracionGestor();
                    break;
                case 3:
                    mostrarMensaje("Cerrando sesión de Gestor...");
                    logingGestor = false;
                    break;
                default:
                    mostrarMensaje("Opción no válida. Por favor, intenta de nuevo.");
                    break;
            }
        } while (opcion != 3);
    }

    /**
     * Muestra el menú del Gestor.
     */
    public static void mostrarMenuGestor() {
        mostrarMensaje("\n--- MENÚ DE GESTOR ---");
        mostrarMensaje("1. Mis Proyectos");
        mostrarMensaje("2. Configuración");
        mostrarMensaje("3. Cerrar Sesión");
    }

    /**
     * Maneja las opciones de "Mis Proyectos" para el Gestor.
     */
    public static void misProyectosGestor() {
        mostrarMensaje("\n--- MIS PROYECTOS ---");
        if (proyectoNombre.isEmpty()) {
            mostrarMensaje("No has creado ningún proyecto aún.");
            mostrarMensaje("1. Crear nuevo proyecto");
            mostrarMensaje("2. Volver al menú de Gestor");
        } else {
            mostrarMensaje("1. Ver y Gestionar: " + proyectoNombre + " | Categoría: " + proyectoCategoria + " | Necesario: " + String.format("%.2f", proyectoCantidadNecesaria) + "€ | Financiado: " + String.format("%.2f", proyectoCantidadFinanciada) + "€");
            mostrarMensaje("2. Crear nuevo proyecto");
            mostrarMensaje("3. Volver al menú de Gestor");
        }
        int subOpcion = solicitarEntero("Selecciona una opción: ");

        if (subOpcion == 1 && !proyectoNombre.isEmpty()) {
            mostrarDetallesProyecto();
            mostrarMensaje("\n  --- Opciones de Proyecto ---");
            mostrarMensaje("  1. Modificar proyecto");
            mostrarMensaje("  2. Eliminar proyecto");
            mostrarMensaje("  3. Volver a Mis Proyectos");
            int subOpcion2 = solicitarEntero("  Selecciona una opción: ");

            if (subOpcion2 == 1) {
                modificarProyecto();
            } else if (subOpcion2 == 2) {
                eliminarProyecto();
            } else if (subOpcion2 != 3) {
                mostrarMensaje("Opción no válida.");
            }
        } else if (subOpcion == 2 || (subOpcion == 1 && proyectoNombre.isEmpty())) {
            crearNuevoProyecto();
        } else if (subOpcion != 3 && subOpcion != 2 && proyectoNombre.isEmpty()) {
            mostrarMensaje("Opción no válida.");
        }
    }

    /**
     * Permite al Gestor crear un nuevo proyecto.
     */
    public static void crearNuevoProyecto() {
        mostrarMensaje("\n--- CREAR NUEVO PROYECTO ---");
        proyectoNombre = solicitarCadena("  Nombre del proyecto: ");
        proyectoDescripcion = solicitarCadena("  Descripción del proyecto: ");
        proyectoCategoria = solicitarCadena("  Categoría del proyecto: ");
        proyectoCantidadNecesaria = solicitarDouble("  Cantidad necesaria para el proyecto (€): ");
        proyectoCantidadFinanciada = solicitarDouble("  Cantidad ya financiada (€): ");
        proyectoFechaInicio = solicitarCadena("  Fecha de inicio (DD/MM/AAAA): ");
        proyectoFechaFin = solicitarCadena("  Fecha de fin (DD/MM/AAAA): ");

        mostrarMensaje("\n  --- Recompensas del Proyecto ---");
        recompensa1 = solicitarCadena("  Nombre Recompensa 1: ");
        recompensa1Descripcion = solicitarCadena("  Descripción Recompensa 1: ");
        recompensa1Precio = solicitarDouble("  Precio Recompensa 1 (€): ");

        recompensa2 = solicitarCadena("  Nombre Recompensa 2: ");
        recompensa2Descripcion = solicitarCadena("  Descripción Recompensa 2: ");
        recompensa2Precio = solicitarDouble("  Precio Recompensa 2 (€): ");

        recompensa3 = solicitarCadena("  Nombre Recompensa 3: ");
        recompensa3Descripcion = solicitarCadena("  Descripción Recompensa 3: ");
        recompensa3Precio = solicitarDouble("  Precio Recompensa 3 (€): ");
        mostrarMensaje("\n¡Proyecto creado exitosamente!");
    }

    /**
     * Maneja la configuración por parte del Gestor.
     */
    public static void configuracionGestor() {
        mostrarMensaje("\n--- CONFIGURACIÓN ---");
        mostrarMensaje("1. Cambiar contraseña");
        mostrarMensaje("2. Cambiar nombre de usuario");
        mostrarMensaje("3. Volver al menú de Gestor");
        int subOpcion = solicitarEntero("Selecciona una opción: ");

        if (subOpcion == 1) {
            gestorContrasena = solicitarCadena("Ingrese la nueva contraseña para 'gestor': ");
            mostrarMensaje("Contraseña de 'gestor' cambiada exitosamente.");
        } else if (subOpcion == 2) {
            gestorNombre = solicitarCadena("Ingrese el nuevo nombre de usuario para 'gestor': ");
            mostrarMensaje("Nombre de usuario 'gestor' cambiado exitosamente.");
        } else if (subOpcion != 3) {
            mostrarMensaje("Opción no válida.");
        }
    }

    /**
     * Muestra el panel y maneja las opciones del Inversor.
     */
    public static void panelInversor() {
        mostrarMensaje("\n====================================");
        mostrarMensaje("        BIENVENIDO, INVERSOR        ");
        mostrarMensaje("====================================");
        int opcion;
        do {
            mostrarMenuInversor();
            opcion = solicitarEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1:
                    misInversionesInversor();
                    break;
                case 2:
                    explorarProyectosInversor();
                    break;
                case 3:
                    carteraDigitalInversor();
                    break;
                case 4:
                    invitarAmigoInversor();
                    break;
                case 5:
                    configuracionInversor();
                    break;
                case 6:
                    mostrarMensaje("Cerrando sesión de Inversor...");
                    logingInversor = false;
                    break;
                default:
                    mostrarMensaje("Opción no válida. Por favor, intenta de nuevo.");
                    break;
            }
        } while (opcion != 6);
    }

    /**
     * Muestra el menú del Inversor.
     */
    public static void mostrarMenuInversor() {
        mostrarMensaje("\n--- MENÚ DE INVERSOR ---");
        mostrarMensaje("1. Mis Inversiones");
        mostrarMensaje("2. Explorar Proyectos");
        mostrarMensaje("3. Cartera Digital");
        mostrarMensaje("4. Invitar a un Amigo");
        mostrarMensaje("5. Configuración");
        mostrarMensaje("6. Cerrar Sesión");
    }

    /**
     * Muestra las inversiones del Inversor.
     */
    public static void misInversionesInversor() {
        mostrarMensaje("\n--- MIS INVERSIONES ---");
        if (haInvertidoInversor) {
            mostrarMensaje("1. Proyecto: " + proyectoNombre + " | Categoría: " + proyectoCategoria + " | Invertido: " + String.format("%.2f", inversionInversorProyecto) + "€");
        } else {
            mostrarMensaje("Aún no has realizado ninguna inversión.");
        }
        mostrarMensaje("2. Volver al menú de Inversor");
        int subOpcion = solicitarEntero("Selecciona una opción: ");

        if (subOpcion == 1 && haInvertidoInversor) {
            mostrarMensaje("\n--- DETALLES DE TU INVERSIÓN EN: " + proyectoNombre.toUpperCase() + " ---");
            mostrarMensaje("  Descripción del Proyecto: " + proyectoDescripcion);
            mostrarMensaje("  Categoría del Proyecto: " + proyectoCategoria);
            mostrarMensaje("  Cantidad Necesaria del Proyecto: " + String.format("%.2f", proyectoCantidadNecesaria) + "€");
            mostrarMensaje("  Cantidad Financiada del Proyecto: " + String.format("%.2f", proyectoCantidadFinanciada) + "€");
            mostrarMensaje("  Tu Inversión en este Proyecto: " + String.format("%.2f", inversionInversorProyecto) + "€");
            mostrarMensaje("  Fecha de inicio del Proyecto: " + proyectoFechaInicio);
            mostrarMensaje("  Fecha de fin del Proyecto: " + proyectoFechaFin);

            mostrarProgresoFinanciacion();

            mostrarMensaje("\n  --- Recompensas del Proyecto ---");
            mostrarMensaje("  1. " + (recompensa1.isEmpty() ? "N/A" : recompensa1 + " | " + recompensa1Descripcion + " | " + String.format("%.2f", recompensa1Precio) + "€"));
            mostrarMensaje("  2. " + (recompensa2.isEmpty() ? "N/A" : recompensa2 + " | " + recompensa2Descripcion + " | " + String.format("%.2f", recompensa2Precio) + "€"));
            mostrarMensaje("  3. " + (recompensa3.isEmpty() ? "N/A" : recompensa3 + " | " + recompensa3Descripcion + " | " + String.format("%.2f", recompensa3Precio) + "€"));
            solicitarCadena("\n  Volver al menú anterior (Pulsa Enter)...");
        } else if (subOpcion != 2) {
            mostrarMensaje("Opción no válida.");
        }
    }

    /**
     * Permite al Inversor explorar los proyectos disponibles.
     */
    public static void explorarProyectosInversor() {
        mostrarMensaje("\n--- EXPLORAR PROYECTOS ---");
        if (proyectoNombre.isEmpty()) {
            mostrarMensaje("No hay proyectos disponibles en este momento. Vuelve más tarde.");
        } else {
            mostrarMensaje("1. Ver detalles de: " + proyectoNombre + " | Categoría: " + proyectoCategoria + " | Necesario: " + String.format("%.2f", proyectoCantidadNecesaria) + "€ | Financiado: " + String.format("%.2f", proyectoCantidadFinanciada) + "€");
        }
        mostrarMensaje("2. Volver al menú de Inversor");
        int subOpcionProyectoInversor = solicitarEntero("Selecciona una opción: ");

        if (subOpcionProyectoInversor == 1 && !proyectoNombre.isEmpty()) {
            mostrarDetallesProyecto();
            mostrarMensaje("\n  --- Opciones de Inversión ---");
            mostrarMensaje("  1. Invertir libremente");
            mostrarMensaje("  2. Invertir seleccionando una recompensa");
            mostrarMensaje("  3. Volver a Explorar Proyectos");
            int tipoInversion = solicitarEntero("  Selecciona el tipo de inversión: ");

            if (tipoInversion == 1) {
                realizarInversion(0); // 0 indica inversión libre
            } else if (tipoInversion == 2) {
                int recompensaElegida = solicitarEntero("  Selecciona el número de recompensa (1, 2 o 3): ");
                realizarInversion(recompensaElegida);
            } else if (tipoInversion != 3) {
                mostrarMensaje("Opción no válida.");
            }
        } else if (subOpcionProyectoInversor != 2 && !proyectoNombre.isEmpty()) {
            mostrarMensaje("Opción no válida.");
        }
    }

    /**
     * Permite al Inversor realizar una inversión en un proyecto.
     * @param recompensaElegida El número de la recompensa elegida (1, 2, 3) o 0 para inversión libre.
     */
    public static void realizarInversion(int recompensaElegida) {
        if (saldoInversor <= 0) {
            mostrarMensaje("Tu cartera digital está vacía. Por favor, recarga saldo antes de invertir.");
            mostrarMensaje("Volviendo al menú de Inversor...");
        } else if (haInvertidoInversor) {
            mostrarMensaje("Ya has invertido en este proyecto. No puedes invertir de nuevo.");
        } else {
            double cantidadAInvertir = 0;
            String mensajeRecompensa = "";

            if (recompensaElegida == 0) { // Inversión libre
                cantidadAInvertir = solicitarDouble("  Ingrese la cantidad a invertir en '" + proyectoNombre + "': ");
            } else { // Inversión por recompensa
                switch (recompensaElegida) {
                    case 1:
                        if (recompensa1.isEmpty()) {
                            mostrarMensaje("La recompensa 1 no está disponible.");
                            return;
                        }
                        cantidadAInvertir = recompensa1Precio;
                        mensajeRecompensa = " (Recompensa: " + recompensa1 + ")";
                        break;
                    case 2:
                        if (recompensa2.isEmpty()) {
                            mostrarMensaje("La recompensa 2 no está disponible.");
                            return;
                        }
                        cantidadAInvertir = recompensa2Precio;
                        mensajeRecompensa = " (Recompensa: " + recompensa2 + ")";
                        break;
                    case 3:
                        if (recompensa3.isEmpty()) {
                            mostrarMensaje("La recompensa 3 no está disponible.");
                            return;
                        }
                        cantidadAInvertir = recompensa3Precio;
                        mensajeRecompensa = " (Recompensa: " + recompensa3 + ")";
                        break;
                    default:
                        mostrarMensaje("Recompensa no válida.");
                        return;
                }
                mostrarMensaje("  Has seleccionado invertir " + String.format("%.2f", cantidadAInvertir) + "€" + mensajeRecompensa);
            }

            if (cantidadAInvertir > 0 && cantidadAInvertir <= saldoInversor) {
                if (proyectoCantidadFinanciada + cantidadAInvertir <= proyectoCantidadNecesaria) {
                    proyectoCantidadFinanciada += cantidadAInvertir;
                    inversionInversorProyecto = cantidadAInvertir;
                    saldoInversor -= cantidadAInvertir;
                    haInvertidoInversor = true;
                    mostrarMensaje("¡Inversión de " + String.format("%.2f", cantidadAInvertir) + "€ realizada con éxito en '" + proyectoNombre + "'!" + mensajeRecompensa);
                    mostrarMensaje("Tu saldo actual es: " + String.format("%.2f", saldoInversor) + "€");
                } else {
                    mostrarMensaje("La cantidad excede lo necesario para el proyecto. Por favor, invierte una cantidad menor o igual a " + String.format("%.2f", (proyectoCantidadNecesaria - proyectoCantidadFinanciada)) + "€.");
                }
            } else if (cantidadAInvertir > saldoInversor) {
                mostrarMensaje("Saldo insuficiente. Tu saldo actual es " + String.format("%.2f", saldoInversor) + "€.");
            } else {
                mostrarMensaje("La cantidad a invertir debe ser positiva.");
            }
        }
    }

    /**
     * Muestra la cartera digital del Inversor y permite recargar saldo.
     */
    public static void carteraDigitalInversor() {
        mostrarMensaje("\n--- CARTERA DIGITAL ---");
        mostrarMensaje("Tu saldo actual es: " + String.format("%.2f", saldoInversor) + "€");
        mostrarMensaje("\nOpciones:");
        mostrarMensaje("1. Recargar Saldo");
        mostrarMensaje("2. Volver al menú de Inversor");
        int subOpcionCartera = solicitarEntero("Selecciona una opción: ");

        if (subOpcionCartera == 1) {
            double cantidadRecarga = solicitarDouble("Ingrese la cantidad a recargar: ");

            if (cantidadRecarga > 0) {
                saldoInversor += cantidadRecarga;
                mostrarMensaje("¡Recarga de " + String.format("%.2f", cantidadRecarga) + "€ realizada con éxito!");
                mostrarMensaje("Tu nuevo saldo es: " + String.format("%.2f", saldoInversor) + "€");
            } else {
                mostrarMensaje("La cantidad a recargar debe ser positiva.");
            }
        } else if (subOpcionCartera != 2) {
            mostrarMensaje("Opción no válida.");
        }
    }

    /**
     * Permite al Inversor invitar a un amigo.
     * Muestra un listado de amigos referidos y tiene una función para
     * añadir nuevos amigos mediante su correo electrónico.
     * El listado de amigos se mantiene como una cadena de texto a la
     * que se le concatenan los correos electrónicos.
     */
    public static void invitarAmigoInversor() {
        mostrarMensaje("\n--- INVITAR A UN AMIGO ---");

        // Mostrar el listado actual de amigos invitados
        if (amigosInversor.isEmpty()) {
            mostrarMensaje("Aún no has invitado a ningún amigo.");
        } else {
            mostrarMensaje("Amigos invitados: " + amigosInversor);
        }

        // Solicitar el correo electrónico del nuevo amigo
        String nuevoAmigoEmail = solicitarCadena("Ingrese el correo electrónico del amigo para invitar: ");

        // Añadir el nuevo correo electrónico al listado existente
        // Si el listado está vacío, el nuevo correo es el primero.
        // Si no está vacío, se concatena con ", " para separarlos.
        if (amigosInversor.isEmpty()) {
            amigosInversor = nuevoAmigoEmail;
        } else {
            amigosInversor += ", " + nuevoAmigoEmail;
        }

        mostrarMensaje("¡Has invitado a '" + nuevoAmigoEmail + "' con éxito!");
        mostrarMensaje("Volviendo al menú de Inversor...");
    }

    /**
     * Maneja la configuración por parte del Inversor.
     */
    public static void configuracionInversor() {
        mostrarMensaje("\n--- CONFIGURACIÓN ---");
        mostrarMensaje("1. Cambiar contraseña");
        mostrarMensaje("2. Cambiar nombre de usuario");
        mostrarMensaje("3. Volver al menú de Inversor");
        int subOpcionConfigInversor = solicitarEntero("Selecciona una opción: ");

        if (subOpcionConfigInversor == 1) {
            inversorContrasena = solicitarCadena("Ingrese la nueva contraseña para 'inversor': ");
            mostrarMensaje("Contraseña de 'inversor' cambiada exitosamente.");
        } else if (subOpcionConfigInversor == 2) {
            inversorNombre = solicitarCadena("Ingrese el nuevo nombre de usuario para 'inversor': ");
            mostrarMensaje("Nombre de usuario 'inversor' cambiado exitosamente.");
        } else if (subOpcionConfigInversor != 3) {
            mostrarMensaje("Opción no válida.");
        }
    }
}
