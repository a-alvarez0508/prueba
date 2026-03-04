package validacion;

public class ValidadorInstrumento {

    public static void validarInstrumento(String tipo, double monto, int plazoDias, String moneda) {
        if (tipo == null || (!tipo.equalsIgnoreCase("corriente")
                && !tipo.equalsIgnoreCase("pactada")
                && !tipo.equalsIgnoreCase("certificado"))) {
            throw new IllegalArgumentException("Tipo de instrumento inválido. Debe ser 'corriente', 'pactada' o 'certificado'.");
        }
        
        if (plazoDias <= 0) throw new IllegalArgumentException("Plazo debe ser mayor a 0.");

        if (tipo.equalsIgnoreCase("corriente")) {
            if (!moneda.equalsIgnoreCase("colones"))
                throw new IllegalArgumentException("Cuenta corriente: solo en colones.");
            if (monto < 25000) throw new IllegalArgumentException("Cuenta corriente: monto mínimo 25,000 colones.");
            
        } else if (tipo.equalsIgnoreCase("pactada")) {
            
            if (plazoDias < 15) throw new IllegalArgumentException("Inversión pactada: plazo mínimo 15 días.");
            if (!(moneda.equalsIgnoreCase("colones") || moneda.equalsIgnoreCase("dólares") || moneda.equalsIgnoreCase("dolares")))
                throw new IllegalArgumentException("Inversión pactada: moneda debe ser 'colones' o 'dólares'.");
            
            if (moneda.equalsIgnoreCase("colones") && monto < 100000)
                throw new IllegalArgumentException("Inversión pactada en colones: monto mínimo 100,000 colones.");
            if ((moneda.equalsIgnoreCase("dólares") || moneda.equalsIgnoreCase("dolares")) && monto < 500)
                throw new IllegalArgumentException("Inversión pactada en dólares: monto mínimo 500 dólares.");
        } else if (tipo.equalsIgnoreCase("certificado")) {
            
            if (!moneda.equalsIgnoreCase("colones"))
                throw new IllegalArgumentException("Certificado: solo inversiones en colones.");
            if (plazoDias < 30) throw new IllegalArgumentException("Certificado: plazo mínimo 30 días.");
            
            if (plazoDias >= 30 && plazoDias <= 89) {
                if (monto < 100000) throw new IllegalArgumentException("Certificado (30-89 días): monto mínimo 100,000 colones.");
            } else if (plazoDias >= 90) {
                if (monto < 50000) throw new IllegalArgumentException("Certificado (>=90 días): monto mínimo 50,000 colones.");
            }
        }
    }
}
