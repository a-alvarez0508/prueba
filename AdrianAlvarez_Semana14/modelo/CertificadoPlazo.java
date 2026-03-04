package modelo;

public class CertificadoPlazo extends Instrumento {
    private static final double RETENCION = 0.08; // 8%

    public CertificadoPlazo(double monto, int plazoDias, String moneda) {
        super(monto, plazoDias, moneda);
    }

    @Override
    public double calcularInteresAnual() {
        
        int n = plazoDias;
        double i = 0.0;
        if (n >= 30 && n <= 59) i = 5.40;
        else if (n >= 60 && n <= 89) i = 5.70;
        else if (n >= 90 && n <= 149) i = 6.30;
        else if (n >= 150 && n <= 179) i = 9.45;
        else if (n >= 180 && n <= 209) i = 9.95;
        else if (n >= 210 && n <= 239) i = 10.00;
        else if (n >= 240 && n <= 269) i = 9.30;
        else if (n >= 270 && n <= 359) i = 9.30;
        else if (n >= 360 && n <= 719) i = 9.30;
        else if (n >= 720 && n <= 1079) i = 9.30;
        else if (n >= 1080 && n <= 1439) i = 9.30;
        else if (n >= 1440 && n <= 1799) i = 9.30;
        else if (n >= 1800) i = 9.30;
        else i = 0.0;
        interesAnual = i;
        return interesAnual;
    }

    @Override
    public double calcularInteres() {
        double iDecimal = calcularInteresAnual() / 100.0;
        interesesGanados = (monto * iDecimal * plazoDias) / 360.0;
        double impuesto = interesesGanados * RETENCION;
        saldoFinal = monto + interesesGanados - impuesto;
        return interesesGanados;
    }

    @Override
    public String toString(){
        double impuesto = interesesGanados * RETENCION;
        return String.format(
            "Sistema de ahorro e inversión: Certificado de depósito a plazo%nInterés anual correspondiente: %.2f %% %nPlazo en días: %d  Monto: %.2f %s  Intereses ganados: %.2f  Impuesto de renta: %.2f  Saldo final: %.2f  Fecha registro: %s",
            interesAnual, plazoDias, monto, moneda, interesesGanados, impuesto, saldoFinal, fechaRegistro
        );
    }
}
