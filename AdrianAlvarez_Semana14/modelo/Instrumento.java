package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Instrumento implements Comparable {
    protected double monto;
    protected int plazoDias;
    protected String moneda; // colones o dolares
    protected String fechaRegistro; // dd/MM/yyyy HH:mm:ss
    protected Cliente cliente;

    protected double interesAnual; 
    protected double interesesGanados; 
    protected double saldoFinal;

    public Instrumento(double monto, int plazoDias, String moneda) {
        this.monto = monto;
        this.plazoDias = plazoDias;
        this.moneda = moneda;
        this.cliente = cliente;
        this.fechaRegistro = registrarFecha();
    }

    private String registrarFecha(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.now().format(fmt);
    }

    public abstract double calcularInteresAnual(); 
    public abstract double calcularInteres(); 

    @Override
    public boolean menorQue(Comparable obj) {
        Instrumento otro = (Instrumento) obj;
        return this.monto < otro.monto;
    }
    
    public double getMonto() { return monto; }
    public int getPlazoDias() { return plazoDias; }
    public String getMoneda() { return moneda; }
    public String getFechaRegistro() { return fechaRegistro; }
    //public Cliente getCliente() { return cliente; }
    public double getInteresAnual() { return interesAnual; }
    public double getInteresesGanados() { return interesesGanados; }
    public double getSaldoFinal() { return saldoFinal; }
    
    @Override
    public String toString() {
        return String.format("Monto: %.2f %s, Plazo: %d días, Fecha registro: %s",
            monto, moneda, plazoDias, fechaRegistro);
    }
}
