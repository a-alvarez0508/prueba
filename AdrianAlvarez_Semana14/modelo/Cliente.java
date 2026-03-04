package modelo;

import java.util.ArrayList;

public class Cliente implements Comparable {
    private static int contadorClientes = 0;
    private String id;
    private String nombreCompleto;
    private String correo;
    private String codigoCliente; 
    private ArrayList<Instrumento> instrumentos;

    public Cliente(String id, String nombreCompleto, String correo) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        contadorClientes++;
        this.codigoCliente = "CLI-" + contadorClientes;
        this.instrumentos = new ArrayList<>();
    }

    public void agregarInstrumento(Instrumento ins) {
        this.instrumentos.add(ins);
    }

    @Override
    public boolean menorQue(Comparable obj){
        Cliente otra = (Cliente) obj;
        return this.getNombreCompleto().compareToIgnoreCase(otra.getNombreCompleto()) < 0;
    }

    public String getId() { return id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getCorreo() { return correo; }
    public String getCodigoCliente() { return codigoCliente; }
    public ArrayList<Instrumento> getInstrumentos() { return instrumentos; }
    
    @Override
    public String toString(){
        return String.format("Código de Cliente %s, ID %s, correo: %s", codigoCliente, id, correo);
    }
}
