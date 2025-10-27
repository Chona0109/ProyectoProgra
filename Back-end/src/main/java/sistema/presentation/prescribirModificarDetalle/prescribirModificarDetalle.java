package sistema.presentation.prescribirModificarDetalle;

import sistema.logic.entities.MedicamentoDetalle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class prescribirModificarDetalle extends JDialog {

    private JButton guardarButton;
    private JButton cancelarButton;
    private JTextField indicaciones;
    private JSpinner cantidad;
    private JSpinner dias;
    private JPanel main;
    private JLabel medicamento;


    private prescribirModificarDetalleModel model;
    private prescribirModificarDetalleController controller;
    private boolean guardado = false;

    public prescribirModificarDetalle(Window parent, MedicamentoDetalle detalle) {
        super(parent, "Modificar Detalle", ModalityType.APPLICATION_MODAL);

        model = new prescribirModificarDetalleModel();
        controller = new prescribirModificarDetalleController(model);

        controller.setCurrentDetalle(detalle);

        setContentPane(main);
        setSize(550, 310);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        cantidad.setValue(model.getCantidad());
        dias.setValue(model.getDias());
        indicaciones.setText(model.getIndicaciones());

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.cambiarCantidad((Integer) cantidad.getValue());
                controller.cambiarDias((Integer) dias.getValue());
                controller.cambiarIndicaciones(indicaciones.getText());
                guardado = true;
                dispose();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public boolean isGuardado() {
        return guardado;
    }

    public MedicamentoDetalle take() {
        return model.getCurrent();
    }
}
