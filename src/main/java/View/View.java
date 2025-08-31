package View;

import personas.Controller;
import personas.Persona;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
  // Falta un atributo Para implementar todos los FORM
    Controller controller;
    Persona model;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {


    }
// Por ahora Implemente este FORM para probrar, hay que implpementarlo con todos.
    public JPanel getPanel() {
        changeForm c = new changeForm(null);
        return c.getPanel();
    }

    public void setModel(Persona model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }
    public void setController(Controller controller) {
        this.controller = controller;
    }

}

// TODAS LAS CLASES DE FORM DEBEN DERIVAR DE ESTA CLASE