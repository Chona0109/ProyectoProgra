package sistema.presentation.UsuariosLogeados;

import logic.entities.Usuario;
import sistema.presentation.tableModels.UsuariosTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UsuariosLogeadosForm extends JPanel implements PropertyChangeListener {

    private JPanel main;
    private JButton recibirMensajeButton;
    private JButton enviarMensajeButton;
    private JTable UsuariosTable;

    private UsuariosLogeadosController controller;
    private UsuariosLogeadosModel model;
    private UsuariosTableModel tableModel;

    public UsuariosLogeadosForm() {
        // Inicializar UI
        main = new JPanel(new BorderLayout());

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        enviarMensajeButton = new JButton("Enviar Mensaje");
        recibirMensajeButton = new JButton("Recibir Mensajes");
        panelBotones.add(enviarMensajeButton);
        panelBotones.add(recibirMensajeButton);
        main.add(panelBotones, BorderLayout.NORTH);

        // Tabla de usuarios
        UsuariosTable = new JTable();
        UsuariosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        UsuariosTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(UsuariosTable);
        main.add(scrollPane, BorderLayout.CENTER);

        // Listeners
        configurarListeners();
    }

    private void configurarListeners() {
        // ✅ ENVIAR MENSAJE
        enviarMensajeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = UsuariosTable.getSelectedRow();
                if (row >= 0 && model.getList() != null && row < model.getList().size()) {
                    Usuario usuarioSeleccionado = model.getList().get(row);

                    // Diálogo para escribir el mensaje
                    JTextArea textArea = new JTextArea(5, 30);
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    JScrollPane scrollPane = new JScrollPane(textArea);

                    int result = JOptionPane.showConfirmDialog(
                            main,
                            scrollPane,
                            "Enviar mensaje a " + usuarioSeleccionado.getNombre(),
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE
                    );

                    if (result == JOptionPane.OK_OPTION) {
                        String mensaje = textArea.getText().trim();
                        if (!mensaje.isEmpty()) {
                            controller.enviarMensaje(usuarioSeleccionado.getId(), mensaje);
                            JOptionPane.showMessageDialog(main,
                                    "✓ Mensaje enviado a " + usuarioSeleccionado.getNombre(),
                                    "Enviado",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(main,
                            "Seleccione un usuario para enviar mensaje",
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // ✅ RECIBIR MENSAJES - NUEVO COMPORTAMIENTO
        recibirMensajeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = UsuariosTable.getSelectedRow();

                if (row < 0 || model.getList() == null || row >= model.getList().size()) {
                    JOptionPane.showMessageDialog(main,
                            "Seleccione un usuario para ver sus mensajes",
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Usuario usuarioSeleccionado = model.getList().get(row);
                String usuarioId = usuarioSeleccionado.getId();

                // ✅ Obtener mensajes pendientes de ese usuario
                List<String> mensajes = controller.obtenerMensajesDe(usuarioId);

                if (mensajes.isEmpty()) {
                    JOptionPane.showMessageDialog(main,
                            "No hay mensajes nuevos de " + usuarioSeleccionado.getNombre(),
                            "Sin mensajes",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // ✅ Mostrar mensajes en ventana emergente
                mostrarVentanaEmergenteMensajes(usuarioSeleccionado, mensajes);

                // ✅ Actualizar indicadores
                actualizarIndicadores();
            }
        });

        // Doble clic para enviar mensaje
        UsuariosTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && UsuariosTable.getSelectedRow() != -1) {
                    enviarMensajeButton.doClick();
                }
            }
        });
    }

    // ✅ NUEVO: Ventana emergente para mostrar mensajes
    private void mostrarVentanaEmergenteMensajes(Usuario emisor, List<String> mensajes) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(main),
                "Mensajes de " + emisor.getNombre(),
                true);
        dialog.setLayout(new BorderLayout(10, 10));

        // Panel superior con info del emisor
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel headerLabel = new JLabel("📨 " + mensajes.size() + " mensaje(s) de " + emisor.getNombre());
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        headerPanel.add(headerLabel);
        dialog.add(headerPanel, BorderLayout.NORTH);

        // Área de texto con los mensajes
        JTextArea mensajesArea = new JTextArea();
        mensajesArea.setEditable(false);
        mensajesArea.setLineWrap(true);
        mensajesArea.setWrapStyleWord(true);
        mensajesArea.setFont(new Font("Arial", Font.PLAIN, 13));
        mensajesArea.setMargin(new Insets(10, 10, 10, 10));

        // ✅ Formatear mensajes con timestamps
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mensajes.size(); i++) {
            sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            sb.append("Mensaje ").append(i + 1).append(" de ").append(mensajes.size()).append("\n");
            sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
            sb.append(mensajes.get(i));
            sb.append("\n\n");
        }
        mensajesArea.setText(sb.toString());
        mensajesArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(mensajesArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Botón cerrar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton cerrarButton = new JButton("Cerrar");
        cerrarButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(cerrarButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(main);
        dialog.setVisible(true);
    }

    public JPanel getPanel() {
        return main;
    }

    public void setController(UsuariosLogeadosController controller) {
        this.controller = controller;
    }

    public void setModel(UsuariosLogeadosModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);

        tableModel = new UsuariosTableModel(
                new int[]{UsuariosTableModel.ID, UsuariosTableModel.NOMBRE},
                model.getList() != null ? model.getList() : Collections.emptyList()
        );
        UsuariosTable.setModel(tableModel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case UsuariosLogeadosModel.LIST:
                UsuariosTable.setModel(new UsuariosTableModel(
                        new int[]{UsuariosTableModel.ID, UsuariosTableModel.NOMBRE},
                        model.getList()
                ));

                // ✅ Configurar renderer para mostrar indicadores
                configurarRendererIndicadores();
                break;

            case UsuariosLogeadosModel.CURRENT:
                break;
        }
        main.revalidate();
    }

    // ✅ NUEVO: Configurar renderer para mostrar indicadores de mensajes
    private void configurarRendererIndicadores() {
        UsuariosTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);

                // Obtener usuario de esta fila
                if (model.getList() != null && row < model.getList().size()) {
                    Usuario usuario = model.getList().get(row);
                    int mensajesPendientes = controller.contarMensajesDe(usuario.getId());

                    if (mensajesPendientes > 0) {
                        // ✅ Resaltar usuarios con mensajes pendientes
                        c.setBackground(new Color(255, 250, 205)); // Amarillo claro
                        c.setFont(c.getFont().deriveFont(Font.BOLD));

                        // Agregar indicador en la columna del nombre
                        if (column == 1 && value != null) {
                            String texto = value.toString() + " (" + mensajesPendientes + " 📬)";
                            ((JLabel) c).setText(texto);
                        }
                    } else {
                        if (!isSelected) {
                            c.setBackground(Color.WHITE);
                        }
                        c.setFont(c.getFont().deriveFont(Font.PLAIN));
                    }
                }

                return c;
            }
        });
    }

    // ✅ MÉTODO PÚBLICO: Actualizar indicadores visuales
    public void actualizarIndicadores() {
        if (UsuariosTable != null) {
            SwingUtilities.invokeLater(() -> {
                UsuariosTable.repaint();
            });
        }
    }

    // ✅ MÉTODO PÚBLICO: Mostrar mensaje en consola (compatibilidad)
    public void mostrarMensaje(String message) {
        // Este método ya no se usa con el nuevo sistema de popup
        // Pero lo mantenemos para evitar errores si se llama desde otro lugar
        System.out.println("📨 " + message);
    }

    private void createUIComponents() {
        main = new JPanel(new BorderLayout());
        UsuariosTable = new JTable();
        UsuariosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(UsuariosTable);
        main.add(scrollPane, BorderLayout.CENTER);

        enviarMensajeButton = new JButton("Enviar Mensaje");
        recibirMensajeButton = new JButton("Recibir Mensajes");
        JPanel botonesPanel = new JPanel();
        botonesPanel.add(enviarMensajeButton);
        botonesPanel.add(recibirMensajeButton);
        main.add(botonesPanel, BorderLayout.SOUTH);

        tableModel = new UsuariosTableModel(
                new int[]{UsuariosTableModel.ID, UsuariosTableModel.NOMBRE},
                Collections.emptyList()
        );
        UsuariosTable.setModel(tableModel);
    }
}