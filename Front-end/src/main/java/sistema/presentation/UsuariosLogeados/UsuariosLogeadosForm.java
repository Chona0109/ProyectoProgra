package sistema.presentation.UsuariosLogeados;

import logic.entities.Usuario;
import sistema.presentation.ThreadListener;
import sistema.presentation.tableModels.UsuariosTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;

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
        recibirMensajeButton = new JButton("Recibir Mensaje");
        panelBotones.add(enviarMensajeButton);
        panelBotones.add(recibirMensajeButton);
        main.add(panelBotones, BorderLayout.NORTH);

        // Tabla de usuarios
        UsuariosTable = new JTable();
        UsuariosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(UsuariosTable);
        main.add(scrollPane, BorderLayout.CENTER);

        // Listeners
        configurarListeners();
    }

    private void configurarListeners() {
        enviarMensajeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = UsuariosTable.getSelectedRow();
                if (row >= 0 && model.getList() != null && row < model.getList().size()) {
                    Usuario usuarioSeleccionado = model.getList().get(row);
                    String mensaje = JOptionPane.showInputDialog(main,
                            "Ingrese mensaje para " + usuarioSeleccionado.getId() + ":");
                    if (mensaje != null && !mensaje.trim().isEmpty()) {
                        controller.enviarMensaje(usuarioSeleccionado.getId(), mensaje.trim());
                    }
                } else {
                    JOptionPane.showMessageDialog(main,
                            "Seleccione un usuario para enviar mensaje",
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        recibirMensajeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = UsuariosTable.getSelectedRow();
                if (row >= 0 && model.getList() != null && row < model.getList().size()) {
                    Usuario usuarioSeleccionado = model.getList().get(row);
                    controller.recibirMensajes();
                    JOptionPane.showMessageDialog(main,
                            "Se ha iniciado la recepción de mensajes de " + usuarioSeleccionado.getId(),
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(main,
                            "Seleccione un usuario para recibir mensaje",
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        UsuariosTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && UsuariosTable.getSelectedRow() != -1) {
                    int row = UsuariosTable.getSelectedRow();
                    Usuario usuarioSeleccionado = model.getList().get(row);
                    controller.recibirMensajes();
                    JOptionPane.showMessageDialog(main,
                            "Se ha iniciado la recepción de mensajes de " + usuarioSeleccionado.getId(),
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
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
                break;

            case UsuariosLogeadosModel.CURRENT:
                // Opcional: si quieres mostrar detalles del usuario actual
                break;
        }
        main.revalidate();
    }

    public void mostrarMensaje(String message) {
        SwingUtilities.invokeLater(() -> {
            JTextArea textArea = new JTextArea(message);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setCaretPosition(0);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 200));

            JOptionPane.showMessageDialog(main, scrollPane, "Mensaje recibido", JOptionPane.INFORMATION_MESSAGE);
        });
    }
    private void createUIComponents() {
        // Panel principal
        main = new JPanel(new BorderLayout());

        // Tabla de usuarios
        UsuariosTable = new JTable();
        UsuariosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Scroll pane para que se vea correctamente
        JScrollPane scrollPane = new JScrollPane(UsuariosTable);
        main.add(scrollPane, BorderLayout.CENTER);

        // Botones abajo
        enviarMensajeButton = new JButton("Enviar Mensaje");
        recibirMensajeButton = new JButton("Recibir Mensaje");
        JPanel botonesPanel = new JPanel();
        botonesPanel.add(enviarMensajeButton);
        botonesPanel.add(recibirMensajeButton);
        main.add(botonesPanel, BorderLayout.SOUTH);

        // Asignar un TableModel inicial vacío para que la tabla se muestre
        tableModel = new UsuariosTableModel(
                new int[]{UsuariosTableModel.ID, UsuariosTableModel.NOMBRE},
                Collections.emptyList()
        );
        UsuariosTable.setModel(tableModel);
    }
}