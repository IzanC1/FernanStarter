package controller;

import model.*;
import model.enums.CategoriaProyecto;
import model.enums.TipoUsuario;
import model.gestion.*;
import model.interfaces.Bloqueable;
import utilidades.*;
import view.Vista;
import java.time.LocalDate;
import java.util.ArrayList;

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
        while (true) {
            String nombre = vista.solicitarCadena("Usuario (o escribe 'cancelar' para volver): ");
            if (nombre.equalsIgnoreCase("cancelar")) {
                break;
            }

            String contrasena = vista.solicitarCadena("Contraseña: ");
            Usuario usuario = GestionUsuario.buscarPorNombre(nombre);

            if (usuario == null) {
                vista.mostrarMensaje("Usuario o contraseña incorrectos.\n");
                continue;
            }

            if (usuario instanceof Bloqueable && ((Bloqueable) usuario).isBloqueado()) {
                vista.mostrarMensaje("Este usuario está bloqueado.\n");
                break;
            }

            if (usuario.getContrasena().equals(contrasena)) {
                boolean loginExitoso = procesar2FA(usuario);
                if (loginExitoso) {
                    break;
                }
            } else {
                if (usuario instanceof Gestor) {
                    Gestor g = (Gestor) usuario;
                    g.decrementarIntentos();
                    if (g.getIntentos() <= 0) {
                        g.bloquear();
                        vista.mostrarMensaje("Demasiados intentos fallidos. Usuario bloqueado.\n");
                        break;
                    } else {
                        vista.mostrarMensaje("Contraseña incorrecta. Intentos restantes: " + g.getIntentos() + "\n");
                    }
                } else if (usuario instanceof Inversor) {
                    Inversor i = (Inversor) usuario;
                    i.decrementarIntentos();
                    if (i.getIntentos() <= 0) {
                        i.bloquear();
                        vista.mostrarMensaje("Demasiados intentos fallidos. Usuario bloqueado.\n");
                        break;
                    } else {
                        vista.mostrarMensaje("Contraseña incorrecta. Intentos restantes: " + i.getIntentos() + "\n");
                    }
                } else {
                    vista.mostrarMensaje("Contraseña incorrecta.\n");
                }
            }
        }
    }

    private boolean procesar2FA(Usuario usuario) {
        String tokenEnviado = funcionesCorreos.enviarCodigoDobleFactor(usuario.getNombre(), usuario.getCorreo());
        int intentos2FA = 3;
        while (intentos2FA > 0) {
            String tokenIngresado = vista.solicitarCadena("Introduce el código de verificación (" + intentos2FA + " intentos): ");
            if (funcionesCorreos.verificarCodigoDobleFactor(usuario.getNombre(), tokenIngresado)) {
                this.usuarioLogueado = usuario;
                if (usuario instanceof Gestor) ((Gestor) usuario).setIntentos(3);
                if (usuario instanceof Inversor) ((Inversor) usuario).setIntentos(3);
                vista.mostrarMensaje("¡Verificación exitosa! Bienvenido, " + usuario.getNombre());
                switch (usuario.getTipo()) {
                    case ADMIN: panelAdmin(); break;
                    case GESTOR: panelGestor(); break;
                    case INVERSOR: panelInversor(); break;
                }
                return true;
            } else {
                intentos2FA--;
                if (intentos2FA > 0) vista.mostrarMensaje("Código incorrecto.");
            }
        }
        vista.mostrarMensaje("Demasiados intentos fallidos con el código. Inicio de sesión cancelado.");
        return false;
    }

    private void procesarRegistro() {
        int tipoOpcion = vista.solicitarTipoUsuarioRegistro();
        if (tipoOpcion == 0) { vista.mostrarMensaje("Registro cancelado."); return; }
        if (tipoOpcion != 1 && tipoOpcion != 2) { vista.mostrarMensaje("Opción no válida."); return; }
        TipoUsuario tipo = (tipoOpcion == 1) ? TipoUsuario.GESTOR : TipoUsuario.INVERSOR;

        String nombre;
        do {
            nombre = vista.solicitarCadena("Nombre de usuario: ");
            if (GestionUsuario.buscarPorNombre(nombre) != null) vista.mostrarMensaje("El nombre ya existe.");
        } while (GestionUsuario.buscarPorNombre(nombre) != null);

        String correo = vista.solicitarCadena("Correo electrónico: ");
        String contrasena = solicitarNuevaContrasenaValidada();
        if (contrasena == null) { vista.mostrarMensaje("Registro cancelado."); return; }

        String tokenEnviado = funcionesCorreos.enviarCodigoDobleFactor(nombre, correo);
        boolean registroExitoso = false;
        int intentosToken = 3;

        while(intentosToken > 0 && !registroExitoso) {
            String tokenIngresado = vista.solicitarCadena("Introduce el código de validación enviado a tu correo (" + intentosToken + " intentos): ");
            if(funcionesCorreos.verificarCodigoDobleFactor(nombre, tokenIngresado)) {
                GestionUsuario.registrarUsuario(nombre, contrasena, correo, tipo);
                vista.mostrarMensaje("¡Registro completado! Ya puedes iniciar sesión.");
                registroExitoso = true;
            } else {
                intentosToken--;
                if (intentosToken > 0) vista.mostrarMensaje("Código incorrecto.");
            }
        }

        if (!registroExitoso) {
            vista.mostrarMensaje("Demasiados intentos fallidos. El registro ha sido cancelado.");
        }
    }

    private void panelAdmin() {
        int opcion = 0;
        while (opcion != 5) {
            vista.mostrarMenuAdmin();
            opcion = vista.solicitarEntero("Selecciona una opción: ");
            switch (opcion) {
                case 1: gestionarUsuarios(); break;
                case 2: gestionarProyectosAdmin(); break;
                case 3: visualizarInversionesAdmin(); break;
                case 4: abrirMenuConfiguracionAdmin(); break;
                case 5: this.usuarioLogueado = null; vista.mostrarMensaje("Sesión cerrada."); break;
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
                case 1: vista.listarMisInversiones(GestionInversion.obtenerPorUsuario(usuarioLogueado.getId())); break;
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
            if (accion == 1) usuarioBloqueable.bloquear();
            else if (accion == 2) usuarioBloqueable.desbloquear();
            vista.mostrarMensaje("Estado del usuario actualizado.");
        } else if (u != null) {
            vista.mostrarMensaje("El administrador no puede ser bloqueado.");
        } else {
            vista.mostrarMensaje("ID no válido.");
        }
    }

    private void gestionarProyectosAdmin() {
        int orden = vista.solicitarOrdenacionProyectos();
        ArrayList<Proyecto> proyectosAMostrar;
        switch (orden) {
            case 2: proyectosAMostrar = GestionProyecto.obtenerProyectosOrdenadosPorFinanciacion(); break;
            case 3: proyectosAMostrar = GestionProyecto.obtenerProyectosOrdenadosPorFecha(); break;
            default: proyectosAMostrar = GestionProyecto.obtenerTodos(); break;
        }
        vista.listarProyectos(proyectosAMostrar);

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

    private void visualizarInversionesAdmin() {
        int orden = vista.solicitarOrdenacionInversiones();
        ArrayList<Inversion> inversionesAMostrar;
        switch(orden) {
            case 2: inversionesAMostrar = GestionInversion.obtenerInversionesOrdenadasPorUsuario(); break;
            case 3: inversionesAMostrar = GestionInversion.obtenerInversionesOrdenadasPorImporte(); break;
            default: inversionesAMostrar = GestionInversion.obtenerTodasLasInversiones(); break;
        }
        vista.listarInversiones(inversionesAMostrar);
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
        if (cat == null) { vista.mostrarMensaje("Creación de proyecto cancelada."); return; }

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
        int orden = vista.solicitarOrdenacionProyectos();
        ArrayList<Proyecto> proyectosAMostrar;
        switch (orden) {
            case 2: proyectosAMostrar = GestionProyecto.obtenerProyectosOrdenadosPorFinanciacion(); break;
            case 3: proyectosAMostrar = GestionProyecto.obtenerProyectosOrdenadosPorFecha(); break;
            default: proyectosAMostrar = GestionProyecto.obtenerTodos(); break;
        }
        vista.listarProyectos(proyectosAMostrar);

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

        int tipoInv = vista.solicitarTipoInversion();
        if (tipoInv == 0) { vista.mostrarMensaje("Inversión cancelada."); return; }

        double cantidadInvertir = 0;
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
        if (nuevaPass != null) {
            usuarioLogueado.setContrasena(nuevaPass);
            vista.mostrarMensaje("Contraseña actualizada correctamente.");
        } else {
            vista.mostrarMensaje("Cambio de contraseña cancelado.");
        }
    }

    private String solicitarNuevaContrasenaValidada() {
        String pass1, pass2;
        do {
            pass1 = vista.solicitarCadena("Introduce la nueva contraseña (o 'cancelar' para salir): ");
            if (pass1.equalsIgnoreCase("cancelar")) return null;

            pass2 = vista.solicitarCadena("Confirma la nueva contraseña: ");
            if (!funcionesCadenas.verificarFortalezaContrasena(pass1)) {
                vista.mostrarMensaje(funcionesCadenas.obtenerRequisitosContrasena());
            } else if (!funcionesCadenas.sonContrasenasIguales(pass1, pass2)) {
                vista.mostrarMensaje("Las contraseñas no coinciden.");
            }
        } while (!funcionesCadenas.verificarFortalezaContrasena(pass1) || !funcionesCadenas.sonContrasenasIguales(pass1, pass2));
        return pass1;
    }
}