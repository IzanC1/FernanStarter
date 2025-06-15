package utilidades;

public class funcionesCadenas {

    /**
     * Comprueba si dos contraseñas son iguales.
     *
     * @param contrasenaUno La primera contraseña.
     * @param contrasenaDos La segunda contraseña.
     * @return true si las contraseñas son iguales, false en caso contrario.
     */
    public static boolean sonContrasenasIguales(String contrasenaUno, String contrasenaDos) {
        if (contrasenaUno == null || contrasenaDos == null) {
            return false;
        }
        return contrasenaUno.equals(contrasenaDos);
    }

    /**
     * Comprueba la fortaleza de una contraseña según los criterios especificados.
     * Criterios: mínimo 8 caracteres, incluye minúsculas, mayúsculas, un número y un símbolo (-_.,*+@).
     *
     * @param contrasena La contraseña a comprobar.
     * @return true si la contraseña cumple con los criterios de fortaleza, false en caso contrario.
     */
    public static boolean verificarFortalezaContrasena(String contrasena) {
        if (contrasena == null || contrasena.length() < 8) {
            return false;
        }

        boolean tieneMinuscula = false;
        boolean tieneMayuscula = false;
        boolean tieneDigito = false;
        boolean tieneSimbolo = false;

        String simbolosPermitidos = "-_.,*+@";

        for (char c : contrasena.toCharArray()) {
            if (Character.isLowerCase(c)) {
                tieneMinuscula = true;
            } else if (Character.isUpperCase(c)) {
                tieneMayuscula = true;
            } else if (Character.isDigit(c)) {
                tieneDigito = true;
            } else if (simbolosPermitidos.indexOf(c) != -1) {
                tieneSimbolo = true;
            }
        }

        return tieneMinuscula && tieneMayuscula && tieneDigito && tieneSimbolo;
    }

    /**
     * Comprueba si la longitud de un texto dado está dentro de los límites mínimos y máximos especificados.
     *
     * @param texto El texto a comprobar.
     * @param longitudMinima La longitud mínima permitida.
     * @param longitudMaxima La longitud máxima permitida.
     * @return true si la longitud del texto está dentro de los límites (inclusive), false en caso contrario.
     */
    public static boolean comprobarLongitudTexto(String texto, int longitudMinima, int longitudMaxima) {
        if (texto == null) {
            return longitudMinima <= 0; // Si el texto es nulo, solo cumple si la longitud mínima es 0.
        }
        int longitud = texto.length();
        return longitud >= longitudMinima && longitud <= longitudMaxima;
    }

    /**
     * Comprueba si un texto contiene un carácter de formato específico.
     *
     * @param texto El texto a comprobar.
     * @param caracterFormato El carácter a buscar (ej. '%', '€').
     * @return true si el texto contiene el carácter de formato, false en caso contrario.
     */
    public static boolean contieneCaracterFormato(String texto, char caracterFormato) {
        if (texto == null) {
            return false;
        }
        return texto.indexOf(caracterFormato) != -1;
    }

    /**
     * Comprueba si un texto contiene una cadena de formato específica.
     * Este método sobrecargado puede usarse para formatos de múltiples caracteres.
     *
     * @param texto La cadena a comprobar.
     * @param cadenaFormato La cadena a buscar (ej. "€", "%").
     * @return true si el texto contiene la cadena de formato, false en caso contrario.
     */
    public static boolean contieneCadenaFormato(String texto, String cadenaFormato) {
        if (texto == null || cadenaFormato == null) {
            return false;
        }
        return texto.contains(cadenaFormato);
    }
}