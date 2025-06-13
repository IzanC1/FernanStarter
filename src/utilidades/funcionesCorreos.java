package utilidades;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom; // Necesario para generar códigos aleatorios
import java.util.Properties;
import java.util.HashMap;
import java.util.Map; // Para almacenar temporalmente los códigos OTP

public class funcionesCorreos {

    private static final Map<String, String> codigosOTPActivos = new HashMap<>();
    private static final SecureRandom random = new SecureRandom();

    // TU CORREO Y CONTRASEÑA DE APLICACIÓN DE GMAIL.
    // Reemplaza "TU_CORREO_DE_GMAIL" con tu dirección de Gmail.
    // Reemplaza "TU_CLAVE_DE_APLICACION" con la contraseña de aplicación generada por Google.
    // ¡NO USES TU CONTRASEÑA HABITUAL DE GMAIL AQUÍ!
    private static final String REMITENTE_GMAIL = "izan.cano.0805@fernando3martos.com";
    private static final String CLAVE_APLICACION_GMAIL = "eqtz igwn rltl pnyl";

    /**
     * Método interno para configurar y enviar un correo electrónico.
     * Es privado porque solo se usará dentro de esta clase para las funciones de 2FA.
     *
     * @param destinatario La dirección de correo a la que se enviará el mensaje.
     * @param asunto El asunto del correo.
     * @param cuerpo El contenido del correo (puede ser HTML).
     */
    private static void configurarYEnviarCorreo(String destinatario, String asunto, String cuerpo) {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", REMITENTE_GMAIL);
        props.put("mail.smtp.clave", CLAVE_APLICACION_GMAIL);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(REMITENTE_GMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setContent(cuerpo, "text/html; charset=utf-8"); // Soporte para contenido HTML

            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", REMITENTE_GMAIL, CLAVE_APLICACION_GMAIL);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            System.out.println("Correo enviado exitosamente a: " + destinatario);
        } catch (Exception me) {
            System.err.println("Error al enviar el correo:");
            me.printStackTrace();
            // Podrías lanzar una excepción aquí si quieres que el llamador maneje el error
        }
    }

    /**
     * Genera un código OTP (One-Time Password) de 4 dígitos, lo envía al email del usuario
     * y lo almacena temporalmente para su posterior verificación.
     *
     * @param identificadorUsuario Un identificador único para el usuario (ej. su nombre de usuario o email).
     * Este será la clave para almacenar y recuperar el código.
     * @param emailDestinatario La dirección de correo electrónico real del usuario a la que se enviará el código.
     * @return El código OTP generado (String) si el envío fue exitoso, o null si hubo un error.
     */
    public static String enviarCodigoDobleFactor(String identificadorUsuario, String emailDestinatario) {
        String codigoOTP = String.format("%04d", random.nextInt(10000));

        String asunto = "Tu Código de Verificación de Acceso";
        String cuerpoHTML = "<html>"
                + "<body style='font-family: Arial, sans-serif; color: #333;'>"
                + "<h2>Código de Verificación de Seguridad</h2>"
                + "<p>Has solicitado un código para completar tu acceso.</p>"
                + "<p>Tu código de verificación es: <strong style='font-size: 20px; color: #007bff;'>" + codigoOTP + "</strong></p>"
                + "<p>Este código es válido por un corto periodo de tiempo. No lo compartas con nadie.</p>"
                + "<p>Si no solicitaste este código, por favor, ignora este correo.</p>"
                + "<hr style='border: none; border-top: 1px solid #eee;'>"
                + "<p style='font-size: 0.8em; color: #777;'>Este es un mensaje automático, por favor no lo respondas.</p>"
                + "</body>"
                + "</html>";

        try {
            configurarYEnviarCorreo(emailDestinatario, asunto, cuerpoHTML); // Usa el método interno para enviar
            codigosOTPActivos.put(identificadorUsuario, codigoOTP); // Almacena el código asociado al usuario
            System.out.println("Código 2FA enviado y almacenado para el usuario '" + identificadorUsuario + "'.");
            return codigoOTP;
        } catch (Exception e) {
            System.err.println("Fallo al enviar el código de doble factor a " + emailDestinatario + " para el usuario " + identificadorUsuario + ": " + e.getMessage());
            return null; // Retorna null si hubo un error en el proceso de envío
        }
    }

    /**
     * Verifica si el código de doble factor ingresado por el usuario coincide con el almacenado.
     * Después de una verificación exitosa, el código se elimina para evitar su reutilización.
     *
     * @param identificadorUsuario El identificador único del usuario para el que se verifica el código.
     * @param codigoIngresado El código que el usuario ha proporcionado.
     * @return 'true' si el código es correcto, 'false' en caso contrario (código incorrecto, no encontrado o ya usado).
     */
    public static boolean verificarCodigoDobleFactor(String identificadorUsuario, String codigoIngresado) {
        String codigoAlmacenado = codigosOTPActivos.get(identificadorUsuario);

        if (codigoAlmacenado != null && codigoAlmacenado.equals(codigoIngresado)) {
            codigosOTPActivos.remove(identificadorUsuario); // Eliminar el código después de su uso para seguridad
            System.out.println("Verificación de doble factor exitosa para el usuario '" + identificadorUsuario + "'.");
            return true;
        } else {
            System.out.println("Fallo en la verificación de doble factor para el usuario '" + identificadorUsuario + "'. Código ingresado incorrecto o no encontrado.");
            return false;
        }
    }
}
