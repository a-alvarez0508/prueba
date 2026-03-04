package validacion;

import modelo.Cliente;

import java.util.regex.Pattern;

public class ValidadorCliente {
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public static void validarCliente(Cliente c) {
        if (c.getId() == null || c.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("El id del cliente no puede estar vacío.");
        }
        if (c.getNombreCompleto() == null || c.getNombreCompleto().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo del cliente no puede estar vacío.");
        }
        if (c.getCorreo() == null || c.getCorreo().trim().isEmpty() || !EMAIL_PATTERN.matcher(c.getCorreo()).matches()) {
            throw new IllegalArgumentException("Correo electrónico inválido.");
        }
    }
}