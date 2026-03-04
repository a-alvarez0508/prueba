package modelo;

public class InversionPactada extends Instrumento {

    public InversionPactada(double monto, int plazoDias, String moneda) {
        super(monto, plazoDias, moneda);
    }

    @Override
    public double calcularInteresAnual() {
        int n = plazoDias;
        double i = 0.0;
        if (n >= 15 && n <= 29) { i = moneda.equalsIgnoreCase("colones") ? 4.85 : 0.80; }
        else if (n >= 30 && n <= 59) { i = moneda.equalsIgnoreCase("colones") ? 4.94 : 0.91; }
        else if (n >= 60 && n <= 89) { i = moneda.equalsIgnoreCase("colones") ? 5.23 : 1.06; }
        else if (n >= 90 && n <= 179) { i = moneda.equalsIgnoreCase("colones") ? 5.81 : 1.44; }
        else if (n >= 180 && n <= 269) { i = moneda.equalsIgnoreCase("colones") ? 8.83 : 2.21; }
        else if (n >= 270 && n <= 359) { i = moneda.equalsIgnoreCase("colones") ? 8.69 : 2.26; }
        else if (n >= 360 && n <= 719) { i = moneda.equalsIgnoreCase("colones") ? 8.69 : 2.40; }
        else if (n >= 720 && n <= 1079) { i = moneda.equalsIgnoreCase("colones") ? 8.69 : 2.40; }
        else if (n >= 1080 && n <= 1439) { i = moneda.equalsIgnoreCase("colones") ? 8.69 : 2.40; }
        else if (n >= 1440 && n <= 1799) { i = moneda.equalsIgnoreCase("colones") ? 8.69 : 2.40; }
        else if (n >= 1800) { i = moneda.equalsIgnoreCase("colones") ? 8.69 : 2.40; }
        else i = 0.0;
        interesAnual = i;
        return interesAnual;
    }

    @Override
    public double calcularInteres() {
        double iDecimal = calcularInteresAnual() / 100.0;
        interesesGanados = (monto * iDecimal * plazoDias) / 360.0;
        saldoFinal = monto + interesesGanados;
        return interesesGanados;
    }

    @Override
    public String toString(){
        return String.format(
            "Sistema de ahorro e inversión: Inversión a la vista tasa pactada%nInterés anual correspondiente: %.2f %% %nPlazo en días: %d  Monto: %.2f %s  Intereses ganados: %.2f  Saldo final: %.2f  Fecha registro: %s",
            interesAnual, plazoDias, monto, moneda, interesesGanados, saldoFinal, fechaRegistro
        );
    }
}
