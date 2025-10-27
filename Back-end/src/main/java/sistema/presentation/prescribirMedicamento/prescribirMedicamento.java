package sistema.presentation.prescribirMedicamento;

import sistema.logic.entities.Medicamento;
import sistema.logic.entities.MedicamentoDetalle;
import sistema.presentation.prescribirModificarDetalle.prescribirModificarDetalle;
import sistema.presentation.prescribirReceta.prescribirReceta;
import sistema.presentation.tableModels.MedicamentosTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;

public class prescribirMedicamento extends JDialog implements PropertyChangeListener {

    private JPanel main;
    private JComboBox<String> buscarComboBox;
    private JTextField buscarFld;
    private JTable miTabla;
    private JButton okButton;
    private JButton cancelarButton;
    private JScrollPane scrollPane1;

    private prescribirBuscarMedicamentoController controller;
    private prescribirBuscarMedicamentoModel model;
    private MedicamentosTableModel tableModel;

    public prescribirMedicamento(Window parent) {
        super(parent, "Buscar Medicamento", ModalityType.APPLICATION_MODAL);
        setContentPane(main);
        setSize(650, 450);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = miTabla.getSelectedRow();
                if (row >= 0 && controller != null) {
                    Medicamento seleccionado = model.getList().get(row);

                    MedicamentoDetalle detalle = new MedicamentoDetalle();
                    detalle.setMedicamento(seleccionado);

                    prescribirModificarDetalle detalleDialog = new prescribirModificarDetalle(prescribirMedicamento.this, detalle);
                    detalleDialog.setVisible(true);

                    if (detalleDialog.isGuardado()) {
                        if (getParent() instanceof prescribirReceta) {
                            prescribirReceta recetaDialog = (prescribirReceta) getParent();
                            recetaDialog.agregarDetalle(detalleDialog.take());
                        }
                    }

                    dispose();
                }
            }
        });


        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buscarFld.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filtrar(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filtrar(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filtrar(); }

            private void filtrar() {
                if (controller != null) {
                    String criterio = (String) buscarComboBox.getSelectedItem();
                    String valor = buscarFld.getText().trim();
                    controller.search(criterio, valor);
                }
            }
        });

        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void setController(prescribirBuscarMedicamentoController controller) {
        this.controller = controller;
    }

    public void setModel(prescribirBuscarMedicamentoModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);

        tableModel = new MedicamentosTableModel(
                new int[]{MedicamentosTableModel.CODIGO, MedicamentosTableModel.NOMBRE, MedicamentosTableModel.PRESENTACION},
                model.getList() != null ? model.getList() : Collections.emptyList()
        );
        miTabla.setModel(tableModel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case prescribirBuscarMedicamentoModel.LIST:
                int[] cols = {
                        MedicamentosTableModel.CODIGO,
                        MedicamentosTableModel.NOMBRE,
                        MedicamentosTableModel.PRESENTACION
                };
                miTabla.setModel(new MedicamentosTableModel(cols, model.getList()));
                break;

            case prescribirBuscarMedicamentoModel.CURRENT:
                break;
        }

        main.revalidate();
    }


    private void createUIComponents() {
        miTabla = new JTable();
        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}