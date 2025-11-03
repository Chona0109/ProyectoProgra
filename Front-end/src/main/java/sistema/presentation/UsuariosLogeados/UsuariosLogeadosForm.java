package sistema.presentation.UsuariosLogeados;

import logic.entities.Usuario;
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


//    private JTextArea mensajesArea;
//    private JScrollPane scrollMensajes;

    public UsuariosLogeadosForm() {
        // Inicializar UI
        main = new JPanel(new BorderLayout());

        // ===== PANEL SUPERIOR: Botones =====
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        enviarMensajeButton = new JButton("Enviar Mensaje");
        recibirMensajeButton = new JButton("Recibir Mensajes");
        panelBotones.add(enviarMensajeButton);
        panelBotones.add(recibirMensajeButton);
        main.add(panelBotones, BorderLayout.NORTH);

        // ===== PANEL CENTRAL: Split entre tabla y mensajes =====
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        // Tabla de usuarios arriba
        UsuariosTable = new JTable();
        UsuariosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollTabla = new JScrollPane(UsuariosTable);
        scrollTabla.setPreferredSize(new Dimension(300, 200));
        splitPane.setTopComponent(scrollTabla);

        // ✅ Área de mensajes abajo
//        mensajesArea = new JTextArea();
//        mensajesArea.setEditable(false);
//        mensajesArea.setLineWrap(true);
//        mensajesArea.setWrapStyleWord(true);
//        mensajesArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
//        scrollMensajes = new JScrollPane(mensajesArea);
//        scrollMensajes.setPreferredSize(new Dimension(300, 150));

//        JPanel panelMensajes = new JPanel(new BorderLayout());
//        panelMensajes.setBorder(BorderFactory.createTitledBorder("Mensajes Recibidos"));
////        panelMensajes.add(scrollMensajes, BorderLayout.CENTER);
//
//        splitPane.setBottomComponent(panelMensajes);
      splitPane.setDividerLocation(200);

       main.add(splitPane, BorderLayout.CENTER);

        // Listeners
        configurarListeners();
    }

    private void configurarListeners() {
        // ✅ ENVIAR MENSAJE: Seleccionar usuario y escribir mensaje
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

                            // ✅ Agregar el mensaje enviado al área de mensajes
                            agregarMensaje("TÚ: " + usuarioSeleccionado.getNombre() + ": " + mensaje);
                        }
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
//                mensajesArea.setText("");
                agregarMensaje("=== Mensajes limpiados ===");
            }
        });


        UsuariosTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && UsuariosTable.getSelectedRow() != -1) {
                    enviarMensajeButton.doClick();
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

                break;
        }
        main.revalidate();
    }


    public void mostrarMensaje(String message) {
        SwingUtilities.invokeLater(() -> {
            agregarMensaje(" " + message);


            JOptionPane.showMessageDialog(
                    null,
                    "📩 Nuevo mensaje:\n" + message,
                    "Notificación",
                    JOptionPane.PLAIN_MESSAGE
            );
        });
    }


    private void agregarMensaje(String mensaje) {


        String mensajeFormateado =  mensaje + "\n";
//        mensajesArea.append(mensajeFormateado);

        // Auto-scroll al final
//        mensajesArea.setCaretPosition(mensajesArea.getDocument().getLength());
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