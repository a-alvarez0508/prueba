package modelo;

public class CuentaCorriente extends Instrumento {

    public CuentaCorriente(double monto, int plazoDias, String moneda) {
        super(monto, plazoDias, moneda);
    }

    @Override
    public double calcularInteresAnual() {
        double v = this.monto;
        if (v >= 25000 && v <= 500000) interesAnual = 1.00;
        else if (v >= 500001 && v <= 1000000) interesAnual = 2.00;
        else if (v >= 1000001 && v <= 2500000) interesAnual = 2.25;
        else if (v >= 2500001 && v <= 10000000) interesAnual = 2.50;
        else if (v >= 10000001) interesAnual = 2.75;
        else interesAnual = 0.0; 
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
            "Sistema de ahorro e inversión: Cuenta corriente%nInterés anual correspondiente: %.2f %% %nPlazo en días: %d  Monto: %.2f %s  Intereses ganados: %.2f  Saldo final: %.2f  Fecha registro: %s",
            interesAnual, plazoDias, monto, moneda, interesesGanados, saldoFinal, fechaRegistro
        );
    }
}
