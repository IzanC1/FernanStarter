package utilidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * Biblioteca de funciones para trabajar con fechas y horas utilizando la API java.time.
 */
public class funcionesFechas {

    /**
     * Formateador estándar para fechas en el formato "día/mes/año".
     * Es una constante para reutilizarla y garantizar consistencia.
     */
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // --- Funciones de Conversión (Parsing y Formateo) ---

    /**
     * Convierte una cadena de texto en formato "dd/MM/yyyy" a un objeto LocalDate.
     * Este proceso se conoce como "parseo".
     *
     * @param fechaString La fecha en formato de texto (ej. "25/12/2023").
     * @return Un objeto LocalDate si el formato es correcto, o null si la cadena es inválida.
     */
    public static LocalDate parsearFecha(String fechaString) {
        if (fechaString == null || fechaString.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(fechaString, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            System.err.println("Error al parsear la fecha. Asegúrese de que el formato es 'dd/MM/yyyy'. Valor recibido: " + fechaString);
            return null; // Indica que el parseo falló
        }
    }

    /**
     * Convierte un objeto LocalDate a una cadena de texto con el formato "dd/MM/yyyy".
     *
     * @param fecha El objeto LocalDate que se va a formatear.
     * @return Una cadena con la fecha formateada, o una cadena vacía si la fecha de entrada es nula.
     */
    public static String formatearFecha(LocalDate fecha) {
        if (fecha == null) {
            return ""; // O "Fecha no disponible", según se prefiera.
        }
        return fecha.format(FORMATO_FECHA);
    }

    // --- Funciones de Comparación ---

    /**
     * Comprueba si una fecha y hora es estrictamente anterior a otra.
     *
     * @param fechaHora1 El primer instante de tiempo.
     * @param fechaHora2 El segundo instante de tiempo.
     * @return `true` si fechaHora1 ocurre antes que fechaHora2, `false` en caso contrario o si alguna es nula.
     */
    public static boolean esAnterior(LocalDateTime fechaHora1, LocalDateTime fechaHora2) {
        if (fechaHora1 == null || fechaHora2 == null) {
            // No se puede comparar si una de las fechas es nula.
            return false;
        }
        return fechaHora1.isBefore(fechaHora2);
    }

    // --- Funciones para Calcular Tiempo Restante ---

    /**
     * Calcula los días completos restantes entre la fecha y hora actual y una fecha futura.
     *
     * @param fechaFutura La fecha y hora del futuro.
     * @return El número de días restantes. Devuelve 0 si la fecha ya ha pasado o es nula.
     */
    public static long obtenerDiasRestantes(LocalDateTime fechaFutura) {
        if (fechaFutura == null || fechaFutura.isBefore(LocalDateTime.now())) {
            return 0;
        }
        return ChronoUnit.DAYS.between(LocalDateTime.now(), fechaFutura);
    }

    /**
     * Calcula las horas completas restantes entre la fecha y hora actual y una fecha futura.
     *
     * @param fechaFutura La fecha y hora del futuro.
     * @return El número de horas restantes. Devuelve 0 si la fecha ya ha pasado o es nula.
     */
    public static long obtenerHorasRestantes(LocalDateTime fechaFutura) {
        if (fechaFutura == null || fechaFutura.isBefore(LocalDateTime.now())) {
            return 0;
        }
        return ChronoUnit.HOURS.between(LocalDateTime.now(), fechaFutura);
    }

    /**
     * Calcula los minutos completos restantes entre la fecha y hora actual y una fecha futura.
     *
     * @param fechaFutura La fecha y hora del futuro.
     * @return El número de minutos restantes. Devuelve 0 si la fecha ya ha pasado o es nula.
     */
    public static long obtenerMinutosRestantes(LocalDateTime fechaFutura) {
        if (fechaFutura == null || fechaFutura.isBefore(LocalDateTime.now())) {
            return 0;
        }
        return ChronoUnit.MINUTES.between(LocalDateTime.now(), fechaFutura);
    }

    /**
     * Calcula los segundos restantes entre la fecha y hora actual y una fecha futura.
     *
     * @param fechaFutura La fecha y hora del futuro.
     * @return El número de segundos restantes. Devuelve 0 si la fecha ya ha pasado o es nula.
     */
    public static long obtenerSegundosRestantes(LocalDateTime fechaFutura) {
        if (fechaFutura == null || fechaFutura.isBefore(LocalDateTime.now())) {
            return 0;
        }
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), fechaFutura);
    }


    // --- Funciones Adicionales Convenientes para la Aplicación ---

    /**
     * Valida que una fecha de fin sea posterior a una fecha de inicio.
     * Muy útil para la creación y modificación de proyectos.
     *
     * @param inicio La fecha de inicio del proyecto.
     * @param fin La fecha de fin del proyecto.
     * @return `true` si el rango es válido (fin es después de inicio), `false` en caso contrario.
     */
    public static boolean validarRangoFechas(LocalDate inicio, LocalDate fin) {
        if (inicio == null || fin == null) {
            return false;
        }
        return fin.isAfter(inicio);
    }

    /**
     * Comprueba si un proyecto se encuentra activo en la fecha actual.
     * Un proyecto está activo si la fecha de hoy está entre la fecha de inicio y fin (ambas inclusive).
     *
     * @param inicio La fecha de inicio del proyecto.
     * @param fin La fecha de fin del proyecto.
     * @return `true` si el proyecto está activo, `false` en caso contrario.
     */
    public static boolean esProyectoActivo(LocalDate inicio, LocalDate fin) {
        if (inicio == null || fin == null) {
            return false;
        }
        LocalDate hoy = LocalDate.now();
        // El proyecto está activo si hoy no es antes del inicio Y no es después del fin.
        return !hoy.isBefore(inicio) && !hoy.isAfter(fin);
    }

    /**
     * Obtiene la fecha actual del sistema ya formateada como "dd/MM/yyyy".
     * Es un atajo conveniente para no tener que llamar a `formatearFecha(LocalDate.now())`.
     *
     * @return La fecha actual como una cadena de texto formateada.
     */
    public static String obtenerFechaActualFormateada() {
        return formatearFecha(LocalDate.now());
    }
}