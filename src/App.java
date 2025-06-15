import utilidades.funcionesCadenas;
import utilidades.funcionesCorreos;

import java.util.Scanner;

public class App {

    // --- Constantes de Configuración ---
    private static final int MAX_USUARIOS = 10;
    private static final int MAX_PROYECTOS = 20;
    private static final int MAX_RECOMPENSAS_POR_PROYECTO = 3;

    // --- Estructuras de Datos (Arrays Estáticos) ---
    private static String[] userNombres = new String[MAX_USUARIOS];
    private static String[] userContrasenas = new String[MAX_USUARIOS];
    private static String[] userTipos = new String[MAX_USUARIOS];
    private static String[] userCorreos = new String[MAX_USUARIOS];
    private static boolean[] userBloqueados = new boolean[MAX_USUARIOS];
    private static int[] userIntentos = new int[MAX_USUARIOS];
    private static double[] userSaldos = new double[MAX_USUARIOS];
    private static String[] userAmigos = new String[MAX_USUARIOS];
    private static int numUsuariosActuales = 0;

    private static String[] proyectoNombres = new String[MAX_PROYECTOS];
    private static String[] proyectoDescripciones = new String[MAX_PROYECTOS];
    private static String[] proyectoCategorias = new String[MAX_PROYECTOS];
    private static double[] proyectoCantidadesNecesarias = new double[MAX_PROYECTOS];
    private static double[] proyectoCantidadesFinanciadas = new double[MAX_PROYECTOS];
    private static String[] proyectoFechasInicio = new String[MAX_PROYECTOS];
    private static String[] proyectoFechasFin = new String[MAX_PROYECTOS];
    private static int[] proyectoGestorId = new int[MAX_PROYECTOS];
    private static int numProyectosActuales = 0;

    private static String[][] recompensaNombres = new String[MAX_PROYECTOS][MAX_RECOMPENSAS_POR_PROYECTO];
    private static String[][] recompensaDescripciones = new String[MAX_PROYECTOS][MAX_RECOMPENSAS_POR_PROYECTO];
    private static double[][] recompensaPrecios = new double[MAX_PROYECTOS][MAX_RECOMPENSAS_POR_PROYECTO];
    private static double[][] inversiones = new double[MAX_USUARIOS][MAX_PROYECTOS];

    private static int usuarioLogueadoIndex = -1;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarDatosDePrueba();
        int opcion0 = 0;
        while (opcion0 != 3) { // Actualizado a 3 para la opción Salir
            mostrarMenuPrincipal();
            opcion0 = solicitarEntero("Por favor, selecciona una opción: ");
            switch (opcion0) {
                case 1:
                    iniciarSesion();
                    break;
                case 2:
                    registrarNuevoUsuario();
                    break;
                case 3:
                    break; // Salir
                default:
                    mostrarMensaje("Opción no válida.");
                    break;
            }
        }
        mostrarMensaje("\n¡Gracias por usar la aplicación!");
        scanner.close();
    }

    public static void inicializarDatosDePrueba() {
        // Usuario 0: Admin
        userNombres[0] = "admin";
        userContrasenas[0] = "admin";
        userTipos[0] = "admin";
        userCorreos[0] = "izan.cano.0805@fernando3martos.com";
        userIntentos[0] = 3;
        numUsuariosActuales++;
        // Usuario 1: Gestor
        userNombres[1] = "gestor";
        userContrasenas[1] = "gestor";
        userTipos[1] = "gestor";
        userCorreos[1] = "izan.cano.0805@fernando3martos.com";
        userIntentos[1] = 3;
        numUsuariosActuales++;
        // Usuario 2: Inversor
        userNombres[2] = "inversor";
        userContrasenas[2] = "inversor";
        userTipos[2] = "inversor";
        userCorreos[2] = "izan.cano.0805@fernando3martos.com";
        userIntentos[2] = 3;
        userSaldos[2] = 1000.0;
        numUsuariosActuales++;
        // Proyecto 0
        proyectoNombres[0] = "Dron de Riego Inteligente";
        proyectoDescripciones[0] = "Un dron autónomo que utiliza IA para optimizar el riego.";
        proyectoCategorias[0] = "Tecnología";
        proyectoCantidadesNecesarias[0] = 5000.0;
        proyectoCantidadesFinanciadas[0] = 250.0;
        proyectoFechasInicio[0] = "01/01/2024";
        proyectoFechasFin[0] = "31/12/2024";
        proyectoGestorId[0] = 1;
        recompensaNombres[0][0] = "Agradecimiento Digital";
        recompensaDescripciones[0][0] = "Correo de agradecimiento.";
        recompensaPrecios[0][0] = 20.0;
        recompensaNombres[0][1] = "Kit de Pegatinas";
        recompensaDescripciones[0][1] = "Un kit exclusivo de pegatinas.";
        recompensaPrecios[0][1] = 50.0;
        recompensaNombres[0][2] = "Acceso Beta";
        recompensaDescripciones[0][2] = "Prueba la tecnología del dron.";
        recompensaPrecios[0][2] = 250.0;
        numProyectosActuales++;
    }

    public static int buscarUsuarioPorNombre(String nombre) {
        for (int i = 0; i < numUsuariosActuales; i++) {
            if (userNombres[i] != null && userNombres[i].equalsIgnoreCase(nombre))
                return i;
        }
        return -1;
    }

    // --- REGISTRO, LOGIN Y SESIÓN ---
    public static void registrarNuevoUsuario() {
        mostrarMensaje("\n--- REGISTRO DE NUEVO USUARIO ---");
        if (numUsuariosActuales >= MAX_USUARIOS) {
            mostrarMensaje("Lo sentimos, se ha alcanzado el número máximo de usuarios registrados.");
            return;
        }

        int opcionTipo = solicitarEntero("¿Cómo deseas registrarte?\n1. Como Gestor\n2. Como Inversor\nSelecciona: ");
        if (opcionTipo != 1 && opcionTipo != 2) {
            mostrarMensaje("Opción no válida. Registro cancelado.");
            return;
        }
        String tipoUsuario = (opcionTipo == 1) ? "gestor" : "inversor";

        String nuevoNombre;
        do {
            nuevoNombre = solicitarCadena("Elige un nombre de usuario: ");
            if (buscarUsuarioPorNombre(nuevoNombre) != -1) {
                mostrarMensaje("Ese nombre de usuario ya está en uso. Por favor, elige otro.");
            }
        } while (buscarUsuarioPorNombre(nuevoNombre) != -1);

        String nuevoCorreo = solicitarCadena("Introduce tu correo electrónico: ");
        String nuevaContrasena = solicitarNuevaContrasenaValidada();

        // Validar correo enviando token
        String tokenEnviado = funcionesCorreos.enviarCodigoDobleFactor(nuevoNombre, nuevoCorreo);
        String tokenIngresado = solicitarCadena("Ingresa el código de validación enviado a tu correo: ");

        if (funcionesCorreos.verificarCodigoDobleFactor(nuevoNombre, tokenIngresado)) {
            // Guardar el nuevo usuario
            int index = numUsuariosActuales;
            userNombres[index] = nuevoNombre;
            userContrasenas[index] = nuevaContrasena;
            userCorreos[index] = nuevoCorreo;
            userTipos[index] = tipoUsuario;
            userBloqueados[index] = false;
            userIntentos[index] = 3;
            userSaldos[index] = 0.0; // Saldo inicial es 0
            userAmigos[index] = "";

            numUsuariosActuales++;
            mostrarMensaje("¡Registro completado exitosamente! Ya puedes iniciar sesión.");
        } else {
            mostrarMensaje("Código de validación incorrecto. El registro ha sido cancelado.");
        }
    }

    public static void iniciarSesion() {
        String usuario = solicitarCadena("Usuario: ");
        String contrasena = solicitarCadena("Contraseña: ");
        int userIndex = buscarUsuarioPorNombre(usuario);

        if (userIndex == -1) {
            mostrarMensaje("Usuario no reconocido.");
            return;
        }
        if (userBloqueados[userIndex]) {
            mostrarMensaje("¡Acceso denegado! Tu usuario ha sido bloqueado.");
            return;
        }

        if (contrasena.equals(userContrasenas[userIndex])) {
            String otpEnviado = funcionesCorreos.enviarCodigoDobleFactor(userNombres[userIndex], userCorreos[userIndex]);
            String otpIngresado = solicitarCadena("Ingresa el código de verificación: ");
            if (funcionesCorreos.verificarCodigoDobleFactor(userNombres[userIndex], otpIngresado)) {
                mostrarMensaje("¡Verificación exitosa! Acceso concedido.");
                usuarioLogueadoIndex = userIndex;
                userIntentos[userIndex] = 3;
                switch (userTipos[userIndex]) {
                    case "admin":
                        panelAdministrador();
                        break;
                    case "gestor":
                        panelGestor();
                        break;
                    case "inversor":
                        panelInversor();
                        break;
                }
            } else {
                mostrarMensaje("Código incorrecto. Acceso denegado.");
            }
        } else {
            if (!userTipos[userIndex].equals("admin")) {
                userIntentos[userIndex]--;
                mostrarMensaje("Contraseña incorrecta. Intentos restantes: " + userIntentos[userIndex]);
                if (userIntentos[userIndex] <= 0) {
                    userBloqueados[userIndex] = true;
                    mostrarMensaje("Usuario '" + userNombres[userIndex] + "' bloqueado.");
                }
            } else {
                mostrarMensaje("Contraseña incorrecta.");
            }
        }
    }

    public static void cerrarSesion() {
        if (usuarioLogueadoIndex != -1) {
            mostrarMensaje("Cerrando sesión de " + userNombres[usuarioLogueadoIndex] + "...");
            usuarioLogueadoIndex = -1;
        }
    }

    // --- PANELES ---
    public static void panelAdministrador() {
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
                    cerrarSesion();
                    break;
                default:
                    mostrarMensaje("Opción no válida.");
                    break;
            }
        } while (opcion != 4);
    }

    public static void panelGestor() {
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
                    cerrarSesion();
                    break;
                default:
                    mostrarMensaje("Opción no válida.");
                    break;
            }
        } while (opcion != 3);
    }

    public static void panelInversor() {
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
                    cerrarSesion();
                    break;
                default:
                    mostrarMensaje("Opción no válida.");
                    break;
            }
        } while (opcion != 6);
    }

    // --- OTRAS FUNCIONES (restantes sin cambios significativos) ---

    public static void gestionarUsuarios() {
        mostrarMensaje("\n--- GESTIÓN DE USUARIOS ---");
        for (int i = 0; i < numUsuariosActuales; i++) {
            if (!userTipos[i].equals("admin")) {
                System.out.println((i + 1) + ". " + userNombres[i] + " (" + userTipos[i] + ") - Bloqueado: " + (userBloqueados[i] ? "Sí" : "No"));
            }
        }
        int userIndex = solicitarEntero("Ingresa el número del usuario a gestionar (0 para cancelar): ") - 1;
        if (userIndex < 0 || userIndex >= numUsuariosActuales || userTipos[userIndex].equals("admin")) {
            mostrarMensaje("Selección no válida.");
            return;
        }
        int accion = solicitarEntero("Gestionando a '" + userNombres[userIndex] + "'. 1. Bloquear 2. Desbloquear: ");
        if (accion == 1) {
            userBloqueados[userIndex] = true;
            mostrarMensaje("Usuario bloqueado.");
        } else if (accion == 2) {
            userBloqueados[userIndex] = false;
            userIntentos[userIndex] = 3;
            mostrarMensaje("Usuario desbloqueado.");
        }
    }

    public static void gestionarProyectosAdmin() {
        mostrarMensaje("\n--- GESTIÓN DE PROYECTOS (Admin) ---");
        if (numProyectosActuales == 0) {
            mostrarMensaje("No hay proyectos.");
            return;
        }
        listarProyectos();
        int proyectoIndex = solicitarEntero("Selecciona un proyecto (0 para volver): ") - 1;
        if (proyectoIndex >= 0 && proyectoIndex < numProyectosActuales) {
            mostrarDetallesProyecto(proyectoIndex);
            int subOpcion = solicitarEntero("1. Modificar 2. Eliminar 3. Volver: ");
            if (subOpcion == 1)
                modificarProyecto(proyectoIndex);
            else if (subOpcion == 2)
                eliminarProyecto(proyectoIndex);
        }
    }

    public static void configuracionAdmin() {
        int subOpcion = solicitarEntero("\n--- CONFIGURACIÓN ADMIN ---\n1. Cambiar mi contraseña\n2. Volver\nSelecciona: ");
        if (subOpcion == 1)
            cambiarContrasenaUsuario(usuarioLogueadoIndex);
    }

    public static void misProyectosGestor() {
        mostrarMensaje("\n--- MIS PROYECTOS ---");
        boolean tieneProyectos = false;
        for (int i = 0; i < numProyectosActuales; i++) {
            if (proyectoGestorId[i] == usuarioLogueadoIndex) {
                if (!tieneProyectos) {
                    mostrarMensaje("Proyectos que gestionas:");
                    tieneProyectos = true;
                }
                System.out.printf("%d. %s\n", (i + 1), proyectoNombres[i]);
            }
        }
        if (!tieneProyectos)
            mostrarMensaje("No has creado ningún proyecto aún.");

        int subOpcion = solicitarEntero("\n1. Crear nuevo proyecto\n2. Gestionar proyecto existente\n3. Volver\nSelecciona: ");
        if (subOpcion == 1)
            crearNuevoProyecto();
        else if (subOpcion == 2) {
            int proyectoIndex = solicitarEntero("Número del proyecto a gestionar: ") - 1;
            if (proyectoIndex >= 0 && proyectoIndex < numProyectosActuales && proyectoGestorId[proyectoIndex] == usuarioLogueadoIndex) {
                mostrarDetallesProyecto(proyectoIndex);
                int accion = solicitarEntero("1. Modificar 2. Eliminar 3. Volver: ");
                if (accion == 1)
                    modificarProyecto(proyectoIndex);
                else if (accion == 2)
                    eliminarProyecto(proyectoIndex);
            } else {
                mostrarMensaje("Selección no válida o no tienes permiso.");
            }
        }
    }

    public static void configuracionGestor() {
        int subOpcion = solicitarEntero("\n--- CONFIGURACIÓN GESTOR ---\n1. Cambiar mi contraseña\n2. Volver\nSelecciona: ");
        if (subOpcion == 1)
            cambiarContrasenaUsuario(usuarioLogueadoIndex);
    }

    public static void misInversionesInversor() {
        mostrarMensaje("\n--- MIS INVERSIONES ---");
        boolean haInvertido = false;
        for (int i = 0; i < numProyectosActuales; i++) {
            if (inversiones[usuarioLogueadoIndex][i] > 0) {
                if (!haInvertido) {
                    mostrarMensaje("Has invertido en:");
                    haInvertido = true;
                }
                System.out.printf("%d. %s - Invertido: %.2f€\n", (i + 1), proyectoNombres[i], inversiones[usuarioLogueadoIndex][i]);
            }
        }
        if (!haInvertido)
            mostrarMensaje("Aún no has realizado ninguna inversión.");
        solicitarCadena("\nPulsa Enter para volver...");
    }

    public static void explorarProyectosInversor() {
        mostrarMensaje("\n--- EXPLORAR PROYECTOS ---");
        if (numProyectosActuales == 0) {
            mostrarMensaje("No hay proyectos disponibles.");
            return;
        }
        listarProyectos();
        int proyectoIndex = solicitarEntero("Selecciona un proyecto (0 para volver): ") - 1;
        if (proyectoIndex >= 0 && proyectoIndex < numProyectosActuales) {
            mostrarDetallesProyecto(proyectoIndex);
            if (inversiones[usuarioLogueadoIndex][proyectoIndex] > 0) {
                mostrarMensaje("\nYa has invertido en este proyecto.");
            } else if (solicitarCadena("\n¿Deseas invertir? (s/n): ").equalsIgnoreCase("s")) {
                realizarInversion(proyectoIndex);
            }
        }
    }

    public static void realizarInversion(int proyectoIndex) {
        if (userSaldos[usuarioLogueadoIndex] <= 0) {
            mostrarMensaje("Tu cartera está vacía.");
            return;
        }
        int tipoInversion = solicitarEntero("1. Invertir libremente 2. Invertir por recompensa: ");
        double cantidadAInvertir = 0;
        if (tipoInversion == 1) {
            cantidadAInvertir = solicitarDouble("Cantidad a invertir: ");
        } else if (tipoInversion == 2) {
            int recompensaElegida = solicitarEntero("Número de recompensa: ") - 1;
            if (recompensaElegida >= 0 && recompensaElegida < MAX_RECOMPENSAS_POR_PROYECTO && recompensaNombres[proyectoIndex][recompensaElegida] != null) {
                cantidadAInvertir = recompensaPrecios[proyectoIndex][recompensaElegida];
            } else {
                mostrarMensaje("Recompensa no válida.");
                return;
            }
        } else {
            mostrarMensaje("Opción no válida.");
            return;
        }

        if (cantidadAInvertir <= 0) {
            mostrarMensaje("La cantidad debe ser positiva.");
        } else if (cantidadAInvertir > userSaldos[usuarioLogueadoIndex]) {
            System.out.printf("Saldo insuficiente (%.2f€).\n", userSaldos[usuarioLogueadoIndex]);
        } else if (proyectoCantidadesFinanciadas[proyectoIndex] + cantidadAInvertir > proyectoCantidadesNecesarias[proyectoIndex]) {
            mostrarMensaje("La inversión excede lo necesario.");
        } else {
            userSaldos[usuarioLogueadoIndex] -= cantidadAInvertir;
            proyectoCantidadesFinanciadas[proyectoIndex] += cantidadAInvertir;
            inversiones[usuarioLogueadoIndex][proyectoIndex] = cantidadAInvertir;
            mostrarMensaje("¡Inversión realizada con éxito!");
            System.out.printf("Tu nuevo saldo es: %.2f€\n", userSaldos[usuarioLogueadoIndex]);
        }
    }

    public static void carteraDigitalInversor() {
        System.out.printf("\n--- CARTERA DIGITAL ---\nSaldo actual: %.2f€\n", userSaldos[usuarioLogueadoIndex]);
        if (solicitarEntero("1. Recargar Saldo 2. Volver: ") == 1) {
            double recarga = solicitarDouble("Cantidad a recargar: ");
            if (recarga > 0) {
                userSaldos[usuarioLogueadoIndex] += recarga;
                System.out.printf("Recarga exitosa. Nuevo saldo: %.2f€\n", userSaldos[usuarioLogueadoIndex]);
            } else {
                mostrarMensaje("La cantidad debe ser positiva.");
            }
        }
    }

    public static void invitarAmigoInversor() {
        mostrarMensaje("\n--- INVITAR A UN AMIGO ---");
        if (userAmigos[usuarioLogueadoIndex] != null && !userAmigos[usuarioLogueadoIndex].isEmpty()) {
            mostrarMensaje("Amigos invitados: " + userAmigos[usuarioLogueadoIndex]);
        }
        String nuevoAmigo = solicitarCadena("Correo del amigo a invitar: ");
        if (userAmigos[usuarioLogueadoIndex] == null || userAmigos[usuarioLogueadoIndex].isEmpty()) {
            userAmigos[usuarioLogueadoIndex] = nuevoAmigo;
        } else {
            userAmigos[usuarioLogueadoIndex] += ", " + nuevoAmigo;
        }
        mostrarMensaje("Invitación enviada a " + nuevoAmigo);
    }

    public static void configuracionInversor() {
        int subOpcion = solicitarEntero("\n--- CONFIGURACIÓN INVERSOR ---\n1. Cambiar mi contraseña\n2. Volver\nSelecciona: ");
        if (subOpcion == 1)
            cambiarContrasenaUsuario(usuarioLogueadoIndex);
    }

    public static void crearNuevoProyecto() {
        if (numProyectosActuales >= MAX_PROYECTOS) {
            mostrarMensaje("No se pueden crear más proyectos.");
            return;
        }
        mostrarMensaje("\n--- CREAR NUEVO PROYECTO ---");
        int index = numProyectosActuales;
        proyectoNombres[index] = solicitarCadena("Nombre del proyecto: ");
        proyectoDescripciones[index] = solicitarCadena("Descripción: ");
        proyectoCategorias[index] = solicitarCadena("Categoría: ");
        proyectoCantidadesNecesarias[index] = solicitarDouble("Cantidad necesaria (€): ");
        proyectoFechasInicio[index] = solicitarCadena("Fecha de inicio (DD/MM/AAAA): ");
        proyectoFechasFin[index] = solicitarCadena("Fecha de fin (DD/MM/AAAA): ");
        proyectoGestorId[index] = usuarioLogueadoIndex;
        for (int i = 0; i < MAX_RECOMPENSAS_POR_PROYECTO; i++) {
            recompensaNombres[index][i] = solicitarCadena("Nombre Recompensa " + (i + 1) + ": ");
            recompensaDescripciones[index][i] = solicitarCadena("Descripción Recompensa " + (i + 1) + ": ");
            recompensaPrecios[index][i] = solicitarDouble("Precio Recompensa " + (i + 1) + " (€): ");
        }
        numProyectosActuales++;
        mostrarMensaje("¡Proyecto creado exitosamente!");
    }

    public static void modificarProyecto(int proyectoIndex) {
        mostrarMensaje("\n--- MODIFICAR PROYECTO: " + proyectoNombres[proyectoIndex] + " ---");
        proyectoNombres[proyectoIndex] = solicitarCadena("Nuevo nombre: ");
        proyectoDescripciones[proyectoIndex] = solicitarCadena("Nueva descripción: ");
        mostrarMensaje("¡Proyecto modificado!");
    }

    public static void eliminarProyecto(int proyectoIndex) {
        if (solicitarCadena("¿Seguro que quieres eliminar '" + proyectoNombres[proyectoIndex] + "'? (s/n): ").equalsIgnoreCase("s")) {
            for (int i = proyectoIndex; i < numProyectosActuales - 1; i++) {
                proyectoNombres[i] = proyectoNombres[i + 1];
                proyectoDescripciones[i] = proyectoDescripciones[i + 1];
                // ... (y así con todos los arrays de proyecto, recompensas e inversiones)
            }
            numProyectosActuales--;
            mostrarMensaje("¡Proyecto eliminado!");
        }
    }

    public static void cambiarContrasenaUsuario(int userIndex) {
        userContrasenas[userIndex] = solicitarNuevaContrasenaValidada();
        mostrarMensaje("Contraseña cambiada exitosamente.");
    }

    public static String solicitarNuevaContrasenaValidada() {
        String nuevaContrasena, confirmarContrasena;
        do {
            nuevaContrasena = solicitarCadena("Nueva contraseña: ");
            confirmarContrasena = solicitarCadena("Confirma la nueva contraseña: ");
            if (!funcionesCadenas.verificarFortalezaContrasena(nuevaContrasena)) {
                mostrarMensaje("La contraseña no es suficientemente fuerte (mínimo 8 caracteres).");
            } else if (!funcionesCadenas.sonContrasenasIguales(nuevaContrasena, confirmarContrasena)) {
                mostrarMensaje("Las contraseñas no coinciden.");
            }
        } while (!funcionesCadenas.verificarFortalezaContrasena(nuevaContrasena) || !funcionesCadenas.sonContrasenasIguales(nuevaContrasena, confirmarContrasena));
        return nuevaContrasena;
    }

    public static void listarProyectos() {
        System.out.println("Proyectos disponibles:");
        for (int i = 0; i < numProyectosActuales; i++) {
            System.out.printf("%d. %s | Cat: %s | %.2f€ / %.2f€\n", (i + 1), proyectoNombres[i], proyectoCategorias[i], proyectoCantidadesFinanciadas[i], proyectoCantidadesNecesarias[i]);
        }
    }

    public static void mostrarDetallesProyecto(int proyectoIndex) {
        mostrarMensaje("\n--- DETALLES: " + proyectoNombres[proyectoIndex].toUpperCase() + " ---");
        System.out.printf("  Descripción: %s\n  Categoría: %s\n  Necesario: %.2f€\n  Financiado: %.2f€\n  Inicio: %s | Fin: %s\n",
                proyectoDescripciones[proyectoIndex], proyectoCategorias[proyectoIndex], proyectoCantidadesNecesarias[proyectoIndex], proyectoCantidadesFinanciadas[proyectoIndex], proyectoFechasInicio[proyectoIndex], proyectoFechasFin[proyectoIndex]);
        mostrarProgresoFinanciacion(proyectoIndex);
        mostrarMensaje("\n  --- Recompensas ---");
        for (int i = 0; i < MAX_RECOMPENSAS_POR_PROYECTO; i++) {
            if (recompensaNombres[proyectoIndex][i] != null && !recompensaNombres[proyectoIndex][i].isEmpty()) {
                System.out.printf("  %d. %s (%.2f€) - %s\n", (i + 1), recompensaNombres[proyectoIndex][i], recompensaPrecios[proyectoIndex][i], recompensaDescripciones[proyectoIndex][i]);
            }
        }
    }

    public static void mostrarProgresoFinanciacion(int proyectoIndex) {
        double necesario = proyectoCantidadesNecesarias[proyectoIndex], financiado = proyectoCantidadesFinanciadas[proyectoIndex];
        double porcentaje = (necesario > 0) ? (financiado / necesario) * 100 : 0;
        int barras = (int) (porcentaje / 5);
        System.out.printf("\n  Progreso: %.1f%% [", porcentaje);
        for (int i = 0; i < 20; i++)
            System.out.print(i < barras ? "█" : "░");
        System.out.printf("] %.2f€ / %.2f€\n", financiado, necesario);
    }

    public static void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public static String solicitarCadena(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public static int solicitarEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(solicitarCadena(mensaje));
            } catch (NumberFormatException e) {
                mostrarMensaje("Entrada no válida. Ingresa un número entero.");
            }
        }
    }

    public static double solicitarDouble(String mensaje) {
        while (true) {
            try {
                return Double.parseDouble(solicitarCadena(mensaje));
            } catch (NumberFormatException e) {
                mostrarMensaje("Entrada no válida. Ingresa un número decimal.");
            }
        }
    }

    public static void mostrarMenuPrincipal() {
        mostrarMensaje("\n--- MENÚ PRINCIPAL ---\n1. Iniciar Sesión\n2. Registrarse\n3. Salir");
    }

    public static void mostrarMenuAdministrador() {
        mostrarMensaje("\n--- MENÚ ADMIN ---\n1. Gestión de Usuarios\n2. Gestión de Proyectos\n3. Configuración\n4. Cerrar Sesión");
    }

    public static void mostrarMenuGestor() {
        mostrarMensaje("\n--- MENÚ GESTOR ---\n1. Mis Proyectos\n2. Configuración\n3. Cerrar Sesión");
    }

    public static void mostrarMenuInversor() {
        mostrarMensaje("\n--- MENÚ INVERSOR ---\n1. Mis Inversiones\n2. Explorar Proyectos\n3. Cartera Digital\n4. Invitar a un Amigo\n5. Configuración\n6. Cerrar Sesión");
    }
}
