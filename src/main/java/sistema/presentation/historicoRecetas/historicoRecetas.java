package sistema.presentation.historicoRecetas;

import sistema.logic.entities.Receta;
import sistema.presentation.tableModels.HistoricoRecetasTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class historicoRecetas extends JDialog implements PropertyChangeListener {

    private JPanel main;
    private JTextField idFld;
    private JButton buscarButton;
    private JButton verTodasButton;
    private JTable miTabla;
    private JButton verDetallesButton;

    private historicoRecetasModel model;
    private historicoRecetasController controller;

    public historicoRecetas(JFrame parent, historicoRecetasModel model, historicoRecetasController controller) {
        super(parent, "Histórico de Recetas", true);
        this.model = model;
        this.controller = controller;

        setContentPane(main);
        setSize(1000, 600);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        verTodasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idFld.setText("");
                try {
                    controller.refresh();
                    miTabla.clearSelection();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(historicoRecetas.this,
                            "Error al cargar todas las recetas: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        idFld.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                String id = idFld.getText().trim();
                controller.buscarPorId(id);
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idFld.getText().trim();
                controller.buscarPorId(id);
            }
        });

        verDetallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = miTabla.getSelectedRow();
                List<Receta> recetas = model.getCurrentList();

                if (row >= 0 && row < recetas.size()) {
                    Receta receta = recetas.get(row);
                    showRecetaDetails(receta);
                } else {
                    JOptionPane.showMessageDialog(historicoRecetas.this,
                            "Seleccione una receta para ver detalles",
                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        miTabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = miTabla.getSelectedRow();
                if (row >= 0 && model.getCurrentList() != null && row < model.getCurrentList().size()) {
                    Receta recetaSeleccionada = model.getCurrentList().get(row);
                    model.setCurrent(recetaSeleccionada);
                }
            }
        });


        model.addPropertyChangeListener(this);

        try {
            controller.refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar recetas: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case historicoRecetasModel.LIST:
                actualizarTabla();
                break;
            case historicoRecetasModel.CURRENT:
                break;
        }
        this.main.revalidate();
    }

    private void actualizarTabla() {
        int[] cols = {
                HistoricoRecetasTableModel.ID,
                HistoricoRecetasTableModel.ESTADO,
                HistoricoRecetasTableModel.FECHA,
                HistoricoRecetasTableModel.PACIENTE,
                HistoricoRecetasTableModel.ID_PACIENTE,
                HistoricoRecetasTableModel.MEDICO
        };

        List<Receta> recetas = model.getCurrentList() != null ? model.getCurrentList() : new ArrayList<>();
        HistoricoRecetasTableModel tableModel = new HistoricoRecetasTableModel(cols, recetas);
        miTabla.setModel(tableModel);
        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void showRecetaDetails(Receta receta) {
        if (receta == null) {
            JOptionPane.showMessageDialog(this, "Receta no disponible", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String detalles = controller.generarDetallesDe(receta);

        JTextArea textArea = new JTextArea(detalles);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Detalles de Receta", JOptionPane.INFORMATION_MESSAGE);
    }

    private void createUIComponents() {
        miTabla = new JTable();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            historicoRecetasModel model = new historicoRecetasModel();
            historicoRecetasController controller = new historicoRecetasController(model);
            historicoRecetas dialog = new historicoRecetas(null, model, controller);
            dialog.setVisible(true);
        });
    }

    public Component getPanel() {
        return main;
    }
}
