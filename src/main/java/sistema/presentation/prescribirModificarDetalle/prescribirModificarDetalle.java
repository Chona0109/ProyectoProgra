package sistema.presentation.prescribirModificarDetalle;

import sistema.logic.entities.Medicamento;
import sistema.logic.entities.MedicamentoDetalle;

import javax.swing.*;
import java.awt.*;

public class prescribirModificarDetalle extends JDialog {

    private JButton guardarButton;
    private JButton cancelarButton;
    private JTextField indicaciones;
    private JSpinner cantidad;
    private JSpinner dias;
    private JPanel main;
    private JLabel medicamento;

    private Medicamento medicamentoSeleccionado;
    private boolean guardado = false;

    public prescribirModificarDetalle(Window parent, Medicamento medicamento) {
        super(parent, "Modificar Detalle", ModalityType.APPLICATION_MODAL);
        this.medicamentoSeleccionado = medicamento;

        setContentPane(main);
        setSize(550, 310);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        if (medicamento != null) {
            this.medicamento.setText(medicamento.getNombre());
        }


        guardarButton.addActionListener(e -> {
            guardado = true;
            dispose();
        });


        cancelarButton.addActionListener(e -> dispose());
    }

    public boolean isGuardado() {
        return guardado;
    }

    public MedicamentoDetalle take() {
        return new MedicamentoDetalle(
                medicamentoSeleccionado,
                (Integer) cantidad.getValue(),
                indicaciones.getText(),
                (Integer) dias.getValue()
        );
    }


    public int getCantidad() {
        return (Integer) cantidad.getValue();
    }

    public int getDias() {
        return (Integer) dias.getValue();
    }

    public String getIndicaciones() {
        return indicaciones.getText();
    }


    public void setCantidad(int cant) {
        cantidad.setValue(cant);
    }

    public void setDias(int d) {
        dias.setValue(d);
    }

    public void setIndicaciones(String ind) {
        indicaciones.setText(ind);
    }
}
