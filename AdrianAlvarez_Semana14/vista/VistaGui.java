package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import modelo.*;
import validacion.*;

public class VistaGui extends JFrame {
    private Banco banco;
    private Cliente clienteActual;

    public VistaGui() {
        super("Banco");
        banco = new Banco();
        clienteActual = null;

        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 1, 10, 10));

        // Botones principales
        JButton btnRegistrarCliente = new JButton("Registrar Cliente");
        JButton btnConsultarCliente = new JButton("Consultar Cliente");
        JButton btnRegistrarInstrumento = new JButton("Registrar Instrumento");
        JButton btnInstrumentosCliente = new JButton("Instrumentos del Cliente");
        JButton btnInstrumentosGlobales = new JButton("Instrumentos Globales");
        JButton btnEliminarCliente = new JButton("Eliminar Cliente");
        JButton btnListarClientes = new JButton("Listar Clientes");
        JButton btnSalir = new JButton("Salir");

        add(btnRegistrarCliente);
        add(btnConsultarCliente);
        add(btnRegistrarInstrumento);
        add(btnInstrumentosCliente);
        add(btnInstrumentosGlobales);
        add(btnEliminarCliente);
        add(btnListarClientes);
        add(btnSalir);

        // Acciones
        btnRegistrarCliente.addActionListener(e -> ventanaRegistrarCliente());
        btnConsultarCliente.addActionListener(e -> ventanaConsultarCliente());
        btnRegistrarInstrumento.addActionListener(e -> ventanaRegistrarInstrumento());
        btnInstrumentosCliente.addActionListener(e -> ventanaInstrumentosCliente());
        btnInstrumentosGlobales.addActionListener(e -> ventanaInstrumentosGlobales());
        btnEliminarCliente.addActionListener(e -> ventanaEliminarCliente());
        btnListarClientes.addActionListener(e -> ventanaListarClientes());
        btnSalir.addActionListener(e -> dispose());

        setVisible(true);
    }

    // =====================================================
    // Ventanas contextuales
    // =====================================================

    private void ventanaRegistrarCliente() {
        JDialog dialog = new JDialog(this, "Registrar Cliente", true);
        dialog.setSize(350, 250);
        dialog.setLayout(new GridLayout(4, 2, 5, 5));
        dialog.setLocationRelativeTo(this);

        JTextField txtId = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtCorreo = new JTextField();

        JButton btnGuardar = new JButton("Registrar");
        JButton btnCancelar = new JButton("Cancelar");

        dialog.add(new JLabel("ID:"));
        dialog.add(txtId);
        dialog.add(new JLabel("Nombre:"));
        dialog.add(txtNombre);
        dialog.add(new JLabel("Correo:"));
        dialog.add(txtCorreo);
        dialog.add(btnGuardar);
        dialog.add(btnCancelar);

        btnGuardar.addActionListener(e -> {
            try {
                Cliente c = new Cliente(txtId.getText(), txtNombre.getText(), txtCorreo.getText());
                ValidadorCliente.validarCliente(c);
                banco.registrarCliente(c);
                clienteActual = c;
                JOptionPane.showMessageDialog(dialog, "Cliente registrado. Código: " + c.getCodigoCliente());
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    private void ventanaConsultarCliente() {
        JDialog dialog = new JDialog(this, "Consultar Cliente", true);
        dialog.setSize(350, 200);
        dialog.setLayout(new GridLayout(2, 2, 5, 5));
        dialog.setLocationRelativeTo(this);

        JTextField txtId = new JTextField();
        JButton btnBuscar = new JButton("Buscar");

        dialog.add(new JLabel("ID del cliente:"));
        dialog.add(txtId);
        dialog.add(btnBuscar);

        btnBuscar.addActionListener(e -> {
            Cliente c = banco.consultarClientePorId(txtId.getText());
            if (c != null) {
                clienteActual = c;
                JOptionPane.showMessageDialog(dialog,
                        "Cliente encontrado:\nCódigo: " + c.getCodigoCliente() +
                                "\nNombre: " + c.getNombreCompleto() +
                                "\nCorreo: " + c.getCorreo());
            } else {
                JOptionPane.showMessageDialog(dialog, "No existe cliente con ese ID.");
            }
        });

        dialog.setVisible(true);
    }

    private void ventanaRegistrarInstrumento() {
        if (clienteActual == null) {
            JOptionPane.showMessageDialog(this, "Debe consultar o registrar un cliente primero.");
            return;
        }

        JDialog dialog = new JDialog(this, "Registrar Instrumento", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(6, 2, 5, 5));
        dialog.setLocationRelativeTo(this);

        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"corriente", "pactada", "certificado"});
        JTextField txtMonto = new JTextField();
        JTextField txtPlazo = new JTextField();
        JTextField txtMoneda = new JTextField();

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnCancelar = new JButton("Cancelar");

        dialog.add(new JLabel("Tipo:"));
        dialog.add(comboTipo);
        dialog.add(new JLabel("Monto:"));
        dialog.add(txtMonto);
        dialog.add(new JLabel("Plazo (días):"));
        dialog.add(txtPlazo);
        dialog.add(new JLabel("Moneda:"));
        dialog.add(txtMoneda);
        dialog.add(btnRegistrar);
        dialog.add(btnCancelar);

        btnRegistrar.addActionListener(e -> {
            try {
                String tipo = comboTipo.getSelectedItem().toString();
                double monto = Double.parseDouble(txtMonto.getText());
                int plazo = Integer.parseInt(txtPlazo.getText());
                String moneda = txtMoneda.getText();

                Instrumento ins = banco.registrarInstrumento(
                        clienteActual.getId(), tipo, monto, plazo, moneda);

                JOptionPane.showMessageDialog(dialog,
                        String.format("""
                                --- Datos del cliente y su operación bancaria ---
                                Cliente: %s
                                Código Cliente: %s, ID %s, correo: %s
                                Monto: %, .0f %s
                                Plazo: %d días
                                Interés anual: %.2f %%
                                Intereses ganados: %, .2f %s
                                Saldo final: %, .2f %s
                                """,
                                clienteActual.getNombreCompleto(),
                                clienteActual.getCodigoCliente(),
                                clienteActual.getId(),
                                clienteActual.getCorreo(),
                                monto, moneda, plazo,
                                ins.getInteresAnual(),
                                ins.getInteresesGanados(), moneda,
                                ins.getSaldoFinal(), moneda));
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    private void ventanaInstrumentosCliente() {
        if (clienteActual == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente primero.");
            return;
        }

        JDialog dialog = new JDialog(this, "Instrumentos del Cliente", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        JTextArea area = new JTextArea();
        area.setEditable(false);
        dialog.add(new JScrollPane(area));

        ArrayList<Instrumento> lista = banco.listarInstrumentosCliente(clienteActual.getId());
        if (lista.isEmpty()) area.setText("El cliente no tiene instrumentos registrados.");
        else {
            StringBuilder sb = new StringBuilder();
            for (Instrumento i : lista) sb.append(i.toString()).append("\n------------------------\n");
            area.setText(sb.toString());
        }

        dialog.setVisible(true);
    }

    private void ventanaInstrumentosGlobales() {
        JDialog dialog = new JDialog(this, "Instrumentos Globales", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        JTextArea area = new JTextArea();
        area.setEditable(false);
        dialog.add(new JScrollPane(area));

        ArrayList<Instrumento> lista = banco.listarInstrumentosGlobal();
        if (lista.isEmpty()) area.setText("No hay instrumentos registrados.");
        else {
            StringBuilder sb = new StringBuilder();
            for (Instrumento i : lista) sb.append(i.toString()).append("\n------------------------\n");
            area.setText(sb.toString());
        }

        dialog.setVisible(true);
    }

    private void ventanaEliminarCliente() {
        if (clienteActual == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente primero.");
            return;
        }
    
        String[] opciones = {"Sí", "No"};
        int conf = JOptionPane.showOptionDialog(
                this,
                "¿Está seguro de eliminar al cliente actual?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[1]
        );
    
        if (conf == JOptionPane.YES_OPTION) {
            try {
                banco.eliminarCliente(clienteActual.getId());
                clienteActual = null;
                JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Operación cancelada.");
        }
    }


    private void ventanaListarClientes() {
        JDialog dialog = new JDialog(this, "Clientes registrados", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        JTextArea area = new JTextArea();
        area.setEditable(false);
        dialog.add(new JScrollPane(area));

        ArrayList<Cliente> lista = banco.listarClientesAlfabeticamente();
        if (lista.isEmpty()) area.setText("No hay clientes registrados.");
        else {
            StringBuilder sb = new StringBuilder();
            for (Cliente c : lista)
                sb.append(c.getCodigoCliente()).append(" | ")
                  .append(c.getId()).append(" | ")
                  .append(c.getNombreCompleto()).append(" | ")
                  .append(c.getCorreo()).append("\n");
            area.setText(sb.toString());
        }

        dialog.setVisible(true);
    }

    // =====================================================
    // MAIN
    // =====================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(VistaGui::new);
    }
}
