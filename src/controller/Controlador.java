package controller;

import model.*;
import model.enums.CategoriaProyecto;
import model.enums.TipoUsuario;
import model.gestion.*;
import model.interfaces.Bloqueable;
import utilidades.*;
import view.Vista;
import java.time.LocalDate;

public class Controlador {
    private Vista vista;
    private Usuario usuarioLogueado;

    public Controlador(Vista vista) {
        this.vista = vista;
        this.usuarioLogueado = null;
    }

    public void iniciar() {
        GestionUsuario.inicializar();
        GestionProyecto.inicializar();
        int opcion = 0;
        while (opcion != 3) {
            vista.mostrarMenuPrincipal();
            opcion = vista.solicitarEntero("Selecciona una opción: ");
            switch (opcion) {
                case 1: procesarLogin(); break;
                case 2: procesarRegistro(); break;
                case 3: vista.mostrarMensaje("¡Hasta pronto!"); break;
                default: vista.mostrarMensaje("Opción no válida."); break;
            }
        }
    }

    // --- LOGIN, REGISTRO Y PANELES ---
    private void procesarLogin() {
        String nombre = vista.solicitarCadena("Usuario: ");
        String contrasena = vista.solicitarCadena("Contraseña: ");
        Usuario usuario = GestionUsuario.buscarPorNombre(nombre);

        if (usuario == null) {
            vista.mostrarMensaje("Usuario o contraseña incorrectos.");
            return;
        }

        // Comprobar si es bloqueable y si está bloqueado
        if (usuario instanceof Bloqueable && ((Bloqueable) usuario).isBloqueado()) {
            vista.mostrarMensaje("Este usuario está bloqueado.");
            return;
        }

        if (usuario.getContrasena().equals(contrasena)) {
            String token = funcionesCorreos.enviarCodigoDobleFactor(usuario.getNombre(), usuario.getCorreo());
            String tokenIngresado = vista.solicitarCadena("Introduce el código de verificación: ");

            if (funcionesCorreos.verificarCodigoDobleFactor(usuario.getNombre(), tokenIngresado)) {
                this.usuarioLogueado = usuario;
                // Resetea intentos al loguear con éxito, solo para los que tienen intentos
                if (usuario instanceof Gestor) ((Gestor) usuario).setIntentos(3);
                if (usuario instanceof Inversor) ((Inversor) usuario).setIntentos(3);

                vista.mostrarMensaje("¡Login exitoso! Bienvenido, " + usuario.getNombre());
                switch (usuario.getTipo()) {
                    case ADMIN: panelAdmin(); break;
                    case GESTOR: panelGestor(); break;
                    case INVERSOR: panelInversor(); break;
                }
            } else {
                vista.mostrarMensaje("Código incorrecto. El inicio de sesión ha fallado.");
            }
        } else {
            // Lógica de intentos fallidos para usuarios bloqueables
            if (usuario instanceof Gestor) {
                Gestor g = (Gestor) usuario;
                g.decrementarIntentos();
                if (g.getIntentos() <= 0) {
                    g.bloquear();
                    vista.mostrarMensaje("Demasiados intentos fallidos. Usuario bloqueado.");
                } else {
                    vista.mostrarMensaje("Contraseña incorrecta. Intentos restantes: " + g.getIntentos());
                }
            } else if (usuario instanceof Inversor) {
                Inversor i = (Inversor) usuario;
                i.decrementarIntentos();
                if (i.getIntentos() <= 0) {
                    i.bloquear();
                    vista.mostrarMensaje("Demasiados intentos fallidos. Usuario bloqueado.");
                } else {
                    vista.mostrarMensaje("Contraseña incorrecta. Intentos restantes: " + i.getIntentos());
                }
            } else {
                vista.mostrarMensaje("Contraseña incorrecta.");
            }
        }
    }

    private void procesarRegistro() {
        int tipoOpcion = vista.solicitarEntero("Registrarse como:\n1. Gestor\n2. Inversor\nOpción: ");
        if(tipoOpcion != 1 && tipoOpcion != 2) { vista.mostrarMensaje("Opción no válida."); return; }
        TipoUsuario tipo = (tipoOpcion == 1) ? TipoUsuario.GESTOR : TipoUsuario.INVERSOR;

        String nombre;
        do {
            nombre = vista.solicitarCadena("Nombre de usuario: ");
            if (GestionUsuario.buscarPorNombre(nombre) != null) vista.mostrarMensaje("El nombre ya existe.");
        } while (GestionUsuario.buscarPorNombre(nombre) != null);

        String correo = vista.solicitarCadena("Correo electrónico: ");
        String contrasena = solicitarNuevaContrasenaValidada();

        String token = funcionesCorreos.enviarCodigoDobleFactor(nombre, correo);
        String tokenIngresado = vista.solicitarCadena("Introduce el código de validación enviado a tu correo: ");

        if(funcionesCorreos.verificarCodigoDobleFactor(nombre, tokenIngresado)) {
            GestionUsuario.registrarUsuario(nombre, contrasena, correo, tipo);
            vista.mostrarMensaje("¡Registro completado! Ya puedes iniciar sesión.");
        } else {
            vista.mostrarMensaje("Código incorrecto. Registro cancelado.");
        }
    }

    private void panelAdmin() {
        int opcion = 0;
        while (opcion != 4) {
            vista.mostrarMenuAdmin();
            opcion = vista.solicitarEntero("Selecciona una opción: ");
            switch (opcion) {
                case 1: gestionarUsuarios(); break;
                case 2: gestionarProyectosAdmin(); break;
                case 3: abrirMenuConfiguracionAdmin(); break;
                case 4: this.usuarioLogueado = null; vista.mostrarMensaje("Sesión cerrada."); break;
                default: vista.mostrarMensaje("Opción no válida."); break;
            }
        }
    }

    private void panelGestor() {
        int opcion = 0;
        while (opcion != 4) {
            vista.mostrarMenuGestor();
            opcion = vista.solicitarEntero("Selecciona una opción: ");
            switch (opcion) {
                case 1: gestionarMisProyectos(); break;
                case 2: procesarCrearProyecto(); break;
                case 3: abrirMenuConfiguracion(); break;
                case 4: this.usuarioLogueado = null; vista.mostrarMensaje("Sesión cerrada."); break;
                default: vista.mostrarMensaje("Opción no válida."); break;
            }
        }
    }

    private void panelInversor() {
        int opcion = 0;
        while (opcion != 6) {
            vista.mostrarMenuInversor();
            opcion = vista.solicitarEntero("Selecciona una opción: ");
            switch (opcion) {
                case 1: vista.listarInversiones(GestionInversion.obtenerPorUsuario(usuarioLogueado.getId()), GestionProyecto.obtenerTodos()); break;
                case 2: explorarProyectos(); break;
                case 3: gestionarCartera(); break;
                case 4: procesarInvitarAmigo(); break;
                case 5: abrirMenuConfiguracion(); break;
                case 6: this.usuarioLogueado = null; vista.mostrarMensaje("Sesión cerrada."); break;
                default: vista.mostrarMensaje("Opción no válida."); break;
            }
        }
    }

    // --- MÉTODOS DE LÓGICA ---
    private void gestionarUsuarios() {
        vista.listarUsuarios(GestionUsuario.obtenerTodosMenosAdmin());
        int idUsuario = vista.solicitarEntero("ID del usuario a gestionar (-1 para cancelar): ");
        if (idUsuario == -1) return;
        Usuario u = GestionUsuario.buscarPorId(idUsuario);

        if (u instanceof Bloqueable) {
            Bloqueable usuarioBloqueable = (Bloqueable) u;
            int accion = vista.solicitarEntero("1. Bloquear\n2. Desbloquear\nOpción: ");
            if (accion == 1) {
                usuarioBloqueable.bloquear();
            } else if (accion == 2) {
                usuarioBloqueable.desbloquear();
            }
            vista.mostrarMensaje("Estado del usuario actualizado.");
        } else if (u != null) {
            vista.mostrarMensaje("El administrador no puede ser bloqueado.");
        } else {
            vista.mostrarMensaje("ID no válido.");
        }
    }

    private void gestionarProyectosAdmin() {
        vista.listarProyectos(GestionProyecto.obtenerTodos());
        int idProyecto = vista.solicitarEntero("ID del proyecto a gestionar (-1 para cancelar): ");
        if (idProyecto == -1) return;
        Proyecto p = GestionProyecto.buscarPorId(idProyecto);
        if (p != null) {
            vista.mostrarDetallesProyecto(p);
            int accion = vista.solicitarEntero("1. Modificar\n2. Eliminar\n3. Volver\nOpción: ");
            if (accion == 1) procesarModificarProyecto(p);
            else if (accion == 2) { if(GestionProyecto.eliminarProyecto(p.getId())) vista.mostrarMensaje("Proyecto eliminado."); }
        } else { vista.mostrarMensaje("ID no válido."); }
    }

    private void gestionarMisProyectos() {
        vista.listarProyectos(GestionProyecto.obtenerPorGestor(usuarioLogueado.getId()));
        int idProyecto = vista.solicitarEntero("ID del proyecto a gestionar (-1 para volver): ");
        if (idProyecto == -1) return;

        Proyecto p = GestionProyecto.buscarPorId(idProyecto);
        if (p != null && p.getIdGestor() == usuarioLogueado.getId()) {
            vista.mostrarDetallesProyecto(p);
            int accion = vista.solicitarEntero("1. Modificar\n2. Eliminar\n3. Volver\nOpción: ");
            if (accion == 1) procesarModificarProyecto(p);
            else if (accion == 2) { if(GestionProyecto.eliminarProyecto(p.getId())) vista.mostrarMensaje("Proyecto eliminado."); }
        } else {
            vista.mostrarMensaje("ID no válido o no tienes permiso sobre este proyecto.");
        }
    }

    private void procesarCrearProyecto() {
        vista.mostrarMensaje("\n--- CREAR NUEVO PROYECTO ---");
        String nombre;
        do {
            nombre = vista.solicitarCadena("Nombre del proyecto (5-50 chars): ");
        } while (!funcionesCadenas.comprobarLongitudTexto(nombre, 5, 50));

        String desc;
        do {
            desc = vista.solicitarCadena("Descripción (20-200 chars): ");
        } while (!funcionesCadenas.comprobarLongitudTexto(desc, 20, 200));

        CategoriaProyecto cat = vista.solicitarCategoria();
        double cant = vista.solicitarDouble("Cantidad necesaria (€): ");

        LocalDate inicio, fin;
        do {
            inicio = funcionesFechas.parsearFecha(vista.solicitarCadena("Fecha de inicio (DD/MM/AAAA): "));
            fin = funcionesFechas.parsearFecha(vista.solicitarCadena("Fecha de fin (DD/MM/AAAA): "));
            if (inicio == null || fin == null || !funcionesFechas.validarRangoFechas(inicio, fin))
                vista.mostrarMensaje("Fechas inválidas o rango incorrecto. Inténtalo de nuevo.");
        } while (inicio == null || fin == null || !funcionesFechas.validarRangoFechas(inicio, fin));

        Proyecto nuevoProyecto = GestionProyecto.crearProyecto(nombre, desc, cat, cant, inicio, fin, usuarioLogueado.getId());
        vista.mostrarMensaje("Proyecto creado. Ahora añade hasta 3 recompensas.");
        for (int i = 0; i < 3; i++) {
            if (vista.solicitarCadena("¿Añadir recompensa " + (i+1) + "? (s/n): ").equalsIgnoreCase("s")) {
                Recompensa r = new Recompensa(vista.solicitarCadena("Nombre recompensa: "), vista.solicitarCadena("Descripción: "), vista.solicitarDouble("Precio (€): "));
                nuevoProyecto.anadirRecompensa(r);
            } else { break; }
        }
        vista.mostrarMensaje("Proyecto y recompensas guardados.");
    }

    private void procesarModificarProyecto(Proyecto p) {
        vista.mostrarMensaje("--- MODIFICAR PROYECTO ---");
        String nuevoNombre;
        do {
            nuevoNombre = vista.solicitarCadena("Nuevo nombre (actual: " + p.getNombre() + "): ");
        } while (!funcionesCadenas.comprobarLongitudTexto(nuevoNombre, 5, 50));
        p.setNombre(nuevoNombre);

        String nuevaDesc;
        do {
            nuevaDesc = vista.solicitarCadena("Nueva descripción (actual: " + p.getDescripcion() + "): ");
        } while (!funcionesCadenas.comprobarLongitudTexto(nuevaDesc, 20, 200));
        p.setDescripcion(nuevaDesc);

        vista.mostrarMensaje("Proyecto modificado.");
    }

    private void explorarProyectos() {
        vista.listarProyectos(GestionProyecto.obtenerTodos());
        int idProyecto = vista.solicitarEntero("ID del proyecto a explorar (-1 para cancelar): ");
        if (idProyecto == -1) return;
        Proyecto p = GestionProyecto.buscarPorId(idProyecto);
        if (p != null) {
            vista.mostrarDetallesProyecto(p);
            if (GestionInversion.haInvertidoUsuario(usuarioLogueado.getId(), p.getId())) {
                vista.mostrarMensaje("Ya has invertido en este proyecto.");
            } else if (vista.solicitarCadena("¿Deseas invertir? (s/n): ").equalsIgnoreCase("s")) {
                procesarInversion(p);
            }
        } else { vista.mostrarMensaje("ID no válido."); }
    }

    private void procesarInversion(Proyecto p) {
        if (!(usuarioLogueado instanceof Inversor)) {
            vista.mostrarMensaje("Solo los inversores pueden realizar esta acción.");
            return;
        }
        Inversor inversor = (Inversor) usuarioLogueado;

        if (inversor.getSaldo() <= 0) { vista.mostrarMensaje("No tienes saldo suficiente."); return; }
        double cantidadInvertir = 0;
        int tipoInv = vista.solicitarEntero("1. Inversión libre\n2. Por recompensa\nOpción: ");
        if (tipoInv == 1) {
            cantidadInvertir = vista.solicitarDouble("Cantidad a invertir: ");
        } else if (tipoInv == 2) {
            int numRec = vista.solicitarEntero("Número de la recompensa a obtener: ");
            if (numRec > 0 && numRec <= p.getRecompensas().size()) {
                cantidadInvertir = p.getRecompensas().get(numRec - 1).getPrecio();
            } else { vista.mostrarMensaje("Recompensa no válida."); return; }
        } else { vista.mostrarMensaje("Opción no válida."); return; }

        if (cantidadInvertir > 0 && cantidadInvertir <= inversor.getSaldo() && (p.getCantidadFinanciada() + cantidadInvertir <= p.getCantidadNecesaria())) {
            inversor.setSaldo(inversor.getSaldo() - cantidadInvertir);
            p.anadirFinanciacion(cantidadInvertir);
            GestionInversion.registrarInversion(inversor, p, cantidadInvertir);
            vista.mostrarMensaje("¡Inversión realizada con éxito!");
        } else {
            vista.mostrarMensaje("No se pudo realizar la inversión (saldo insuficiente o excede lo necesario).");
        }
    }

    private void gestionarCartera() {
        if(usuarioLogueado instanceof Inversor) {
            Inversor inversor = (Inversor) usuarioLogueado;
            vista.mostrarMensaje(String.format("Tu saldo actual es: %.2f€", inversor.getSaldo()));
            if (vista.solicitarCadena("¿Deseas recargar saldo? (s/n): ").equalsIgnoreCase("s")) {
                double recarga = vista.solicitarDouble("Cantidad a recargar: ");
                if (recarga > 0) {
                    inversor.setSaldo(inversor.getSaldo() + recarga);
                    vista.mostrarMensaje("Saldo actualizado.");
                } else {
                    vista.mostrarMensaje("La cantidad debe ser positiva.");
                }
            }
        }
    }

    private void procesarInvitarAmigo() {
        if (usuarioLogueado instanceof Inversor) {
            Inversor inversor = (Inversor) usuarioLogueado;
            vista.mostrarMensaje("Amigos invitados: " + inversor.getAmigos());
            String correoAmigo = vista.solicitarCadena("Correo del amigo a invitar: ");
            inversor.anadirAmigo(correoAmigo);
            vista.mostrarMensaje("Invitación registrada para " + correoAmigo);
        }
    }

    private void abrirMenuConfiguracion() {
        vista.mostrarMenuConfiguracion();
        int opcion = vista.solicitarEntero("Selecciona una opción: ");
        if (opcion == 1) {
            procesarCambioContrasena();
        }
    }

    private void abrirMenuConfiguracionAdmin() {
        vista.mostrarMenuConfiguracionAdmin();
        int opcion = vista.solicitarEntero("Selecciona una opción: ");
        if (opcion == 1) {
            procesarCambioContrasena();
        }
    }

    private void procesarCambioContrasena() {
        String nuevaPass = solicitarNuevaContrasenaValidada();
        usuarioLogueado.setContrasena(nuevaPass);
        vista.mostrarMensaje("Contraseña actualizada correctamente.");
    }

    private String solicitarNuevaContrasenaValidada() {
        String pass1, pass2;
        do {
            pass1 = vista.solicitarCadena("Introduce la nueva contraseña: ");
            pass2 = vista.solicitarCadena("Confirma la nueva contraseña: ");
            if (!funcionesCadenas.verificarFortalezaContrasena(pass1)) {
                vista.mostrarMensaje("La contraseña es muy débil (mínimo 8 caracteres).");
            } else if (!funcionesCadenas.sonContrasenasIguales(pass1, pass2)) {
                vista.mostrarMensaje("Las contraseñas no coinciden.");
            }
        } while (!funcionesCadenas.verificarFortalezaContrasena(pass1) || !funcionesCadenas.sonContrasenasIguales(pass1, pass2));
        return pass1;
    }
}