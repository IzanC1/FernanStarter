package utilidades;

public class funcionesCadenas {

    /**
     * Devuelve una cadena de texto con los requisitos detallados para una contraseña segura.
     * @return String con los requisitos.
     */
    public static String obtenerRequisitosContrasena() {
        return "La contraseña debe tener al menos 8 caracteres, incluyendo una mayúscula, una minúscula, un número y un símbolo especial (@#$%^&+=).";
    }

    /**
     * Compara si dos contraseñas son iguales.
     * @param p1 Primera contraseña.
     * @param p2 Segunda contraseña.
     * @return true si son iguales, false en caso contrario.
     */
    public static boolean sonContrasenasIguales(String p1, String p2) {
        return p1 != null && p1.equals(p2);
    }

    /**
     * Verifica si una contraseña cumple con los requisitos de fortaleza definidos.
     * @param pass La contraseña a verificar.
     * @return true si la contraseña es fuerte, false en caso contrario.
     */
    public static boolean verificarFortalezaContrasena(String pass) {
        if (pass == null || pass.length() < 8) {
            return false;
        }
        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneNumero = false;
        boolean tieneSimbolo = false;
        String simbolosEspeciales = "@#$%^&+=";

        for (char c : pass.toCharArray()) {
            if (Character.isUpperCase(c)) tieneMayuscula = true;
            else if (Character.isLowerCase(c)) tieneMinuscula = true;
            else if (Character.isDigit(c)) tieneNumero = true;
            else if (simbolosEspeciales.indexOf(c) != -1) tieneSimbolo = true;
        }
        return tieneMayuscula && tieneMinuscula && tieneNumero && tieneSimbolo;
    }

    /**
     * Comprueba si un texto tiene una longitud dentro de un rango especificado.
     * @param texto El texto a comprobar.
     * @param min Longitud mínima.
     * @param max Longitud máxima.
     * @return true si la longitud está en el rango, false en caso contrario.
     */
    public static boolean comprobarLongitudTexto(String texto, int min, int max) {
        return texto != null && texto.length() >= min && texto.length() <= max;
    }

    /**
     * Comprueba si un texto contiene un carácter de formato específico.
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