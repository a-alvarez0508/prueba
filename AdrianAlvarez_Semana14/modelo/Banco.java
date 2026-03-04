package modelo;

import java.util.ArrayList;

import validacion.*;
import util.Ordenamiento;

public class Banco {
    private ArrayList<Cliente> clientes;
    private ArrayList<Instrumento> instrumentos;

    public Banco() {
        this.clientes = new ArrayList<>();
        this.instrumentos = new ArrayList<>();
    }

    public void registrarCliente(Cliente c) {
        ValidadorCliente.validarCliente(c);
        this.clientes.add(c);
    }

    public Cliente consultarClientePorId(String id) {
        for (Cliente c : clientes) {
            if (c.getId().equals(id)) return c;
        }
        return null;
    }

    // tipo corriente, pactada o certificado
    public Instrumento registrarInstrumento(String clienteId, String tipo, double monto, int plazoDias, String moneda) {
        Cliente c = consultarClientePorId(clienteId);
        if (c == null) throw new IllegalArgumentException("No existe un cliente registrado con ese ID.");

        ValidadorInstrumento.validarInstrumento(tipo, monto, plazoDias, moneda);

        Instrumento ins;
        if (tipo.equalsIgnoreCase("corriente")) {
            ins = new CuentaCorriente(monto, plazoDias, moneda);
        } else if (tipo.equalsIgnoreCase("pactada")) {
            ins = new InversionPactada(monto, plazoDias, moneda);
        } else {
            ins = new CertificadoPlazo(monto, plazoDias, moneda);
        }

        ins.calcularInteres();

        this.instrumentos.add(ins);
        c.agregarInstrumento(ins);

        return ins;
    }

    public ArrayList<Instrumento> listarInstrumentosCliente(String clienteId) {
        Cliente c = consultarClientePorId(clienteId);
        if (c == null) throw new IllegalArgumentException("No existe un cliente con ese ID.");

        ArrayList<Instrumento> lista = c.getInstrumentos();
        
        Ordenamiento.ordenar((ArrayList<Comparable>)(ArrayList<?>) lista);

        return lista;
    }

    public ArrayList<Instrumento> listarInstrumentosGlobal() {
        Ordenamiento.ordenar((ArrayList<Comparable>)(ArrayList<?>) instrumentos);
        return instrumentos;
    }

    public ArrayList<Cliente> listarClientesAlfabeticamente() {
        Ordenamiento.ordenar((ArrayList<Comparable>)(ArrayList<?>) clientes);
        return clientes;
    }
    
    public void eliminarCliente(String clienteId) {
        Cliente c = consultarClientePorId(clienteId);
        if (c == null) throw new IllegalArgumentException("No existe un cliente con ese ID.");
        this.clientes.remove(c);
    }
}

