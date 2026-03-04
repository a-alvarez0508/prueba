package vista;

import java.util.ArrayList;
import java.util.Scanner;

import modelo.*;
import validacion.*;

public class VistaCli {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Banco banco = new Banco();
        Cliente clienteActual = null;

        System.out.println("===================");
        System.out.println("   CLI - Banco");
        System.out.println("===================");

        boolean salir = false;
        while (!salir) {
            System.out.println("\nMenú principal");
            System.out.println("1) Registrar cliente");
            System.out.println("2) Consultar cliente por ID");
            System.out.println("3) Registrar instrumento a cliente actual");
            System.out.println("4) Consultar instrumentos de cliente actual");
            System.out.println("5) Consultar instrumentos globales");
            System.out.println("6) Eliminar cliente actual");
            System.out.println("7) Listar clientes alfabéticamente");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            String op = sc.nextLine().trim();

            switch (op) {
                case "1": { // Registrar cliente
                    System.out.print("ID del cliente: ");
                    String id = sc.nextLine().trim();
                    System.out.print("Nombre completo: ");
                    String nombre = sc.nextLine().trim();
                    System.out.print("Correo electrónico: ");
                    String correo = sc.nextLine().trim();
                    try {
                        Cliente c = new Cliente(id, nombre, correo);
                        ValidadorCliente.validarCliente(c);
                        banco.registrarCliente(c);
                        clienteActual = c;
                        System.out.println("Cliente registrado exitosamente. Código asignado: " + c.getCodigoCliente());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case "2": { // Consultar cliente por ID
                    System.out.print("Ingrese el ID a consultar: ");
                    String id = sc.nextLine().trim();
                    Cliente c = banco.consultarClientePorId(id);
                    if (c != null) {
                        System.out.println("Cliente encontrado:");
                        System.out.println("Código: " + c.getCodigoCliente() + " | Nombre: " + c.getNombreCompleto());
                        clienteActual = c;
                    } else {
                        System.out.println("No existe un cliente registrado con ese ID.");
                    }
                    break;
                }
                case "3": { // Registrar instrumento
                    if (clienteActual == null) {
                        System.out.println("Debe consultar o registrar primero un cliente.");
                        break;
                    }
                    System.out.print("Tipo de instrumento (corriente/pactada/certificado): ");
                    String tipo = sc.nextLine().trim();
                    System.out.print("Monto de la inversión: ");
                    String sMonto = sc.nextLine().trim();
                    System.out.print("Plazo total (en días): ");
                    String sPlazo = sc.nextLine().trim();
                    System.out.print("Moneda (colones/dólares): ");
                    String moneda = sc.nextLine().trim();
                    try {
                        double monto = Double.parseDouble(sMonto.replace(",", ""));
                        int plazo = Integer.parseInt(sPlazo);

                        Instrumento ins = banco.registrarInstrumento(
                                clienteActual.getId(), tipo, monto, plazo, moneda);

                        System.out.println("\n--- Datos del cliente y su operación bancaria ---");
                        System.out.println("Cliente: " + clienteActual.getNombreCompleto());
                        System.out.println("Código de Cliente " + clienteActual.getCodigoCliente()
                                + ", ID " + clienteActual.getId()
                                + ", correo: " + clienteActual.getCorreo());
                        System.out.printf("Monto de ahorro e inversión: %, .0f %s%n", monto, moneda);
                        System.out.println("Plazo de la inversión días: " + plazo + " días");

                        // Nombre formal del sistema según el tipo
                        String nombreSistema;
                        if (tipo.equalsIgnoreCase("corriente")) {
                            nombreSistema = "Cuenta corriente";
                        } else if (tipo.equalsIgnoreCase("pactada")) {
                            nombreSistema = "Inversión a la vista tasa pactada";
                        } else if (tipo.equalsIgnoreCase("certificado")) {
                            nombreSistema = "Certificado de inversión";
                        } else {
                            nombreSistema = tipo;
                        }
                        System.out.println("Sistema de ahorro e inversión: " + nombreSistema);
                        System.out.printf("Interés anual correspondiente: %.2f %%\n", ins.getInteresAnual());
                        System.out.println("Rendimiento");

                        if (tipo.equalsIgnoreCase("certificado")) {
                            System.out.println("Plazo en días Monto de ahorro e inversión Intereses ganados Impuesto de renta Saldo Final");
                            double impuesto = ins.getInteresesGanados() * 0.08;
                            System.out.printf("%d %, .0f %s %, .0f %s %, .0f %s %, .2f%n",
                                    plazo,
                                    monto, moneda,
                                    ins.getInteresesGanados(), moneda,
                                    impuesto, moneda,
                                    ins.getSaldoFinal());
                        } else {
                            System.out.println("Plazo en días Monto de ahorro e inversión Intereses ganados Saldo Final");
                            System.out.printf("%d %, .0f %s %, .2f %s %, .2f%n",
                                    plazo,
                                    monto, moneda,
                                    ins.getInteresesGanados(), moneda,
                                    ins.getSaldoFinal());
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Error: monto o plazo inválido.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case "4": { // Listar instrumentos del cliente actual
                    if (clienteActual == null) {
                        System.out.println("Debe seleccionar un cliente primero.");
                        break;
                    }
                    try {
                        ArrayList<Instrumento> lista = banco.listarInstrumentosCliente(clienteActual.getId());
                        if (lista.isEmpty()) {
                            System.out.println("El cliente no tiene instrumentos registrados.");
                        } else {
                            System.out.println("Instrumentos del cliente (orden ascendente por monto):");
                            for (Instrumento i : lista) {
                                System.out.println(i.toString());
                                System.out.println("------------------------------------");
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }
                case "5": { // Listar instrumentos globales
                    ArrayList<Instrumento> lista = banco.listarInstrumentosGlobal();
                    if (lista.isEmpty()) {
                        System.out.println("No hay instrumentos registrados en el sistema.");
                    } else {
                        System.out.println("Instrumentos globales (orden ascendente por monto):");
                        for (Instrumento i : lista) {
                            System.out.println(i.toString());
                            System.out.println("------------------------------------");
                        }
                    }
                    break;
                }
                case "6": { // Eliminar cliente actual
                    if (clienteActual == null) {
                        System.out.println("Debe seleccionar un cliente primero.");
                        break;
                    }
                    System.out.print("¿Está seguro de eliminar al cliente actual? (s/n): ");
                    String conf = sc.nextLine().trim();
                    if (conf.equalsIgnoreCase("s")) {
                        try {
                            banco.eliminarCliente(clienteActual.getId());
                            System.out.println("Cliente eliminado. Los instrumentos permanecen en el sistema global.");
                            clienteActual = null;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Operación cancelada.");
                    }
                    break;
                }
                case "7": { // Listar clientes alfabéticamente
                    ArrayList<Cliente> lista = banco.listarClientesAlfabeticamente();
                    if (lista.isEmpty()) {
                        System.out.println("No hay clientes registrados.");
                    } else {
                        System.out.println("Clientes (ordenados por nombre):");
                        for (Cliente c : lista) {
                            System.out.printf("%s | %s | %s | %s%n",
                                    c.getCodigoCliente(), c.getId(), c.getNombreCompleto(), c.getCorreo());
                        }
                    }
                    break;
                }
                case "0": {
                    salir = true;
                    System.out.println("¡Hasta luego!");
                    break;
                }
                default:
                    System.out.println("Opción inválida.");
            }
        }
        sc.close();
    }
}
