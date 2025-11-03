package sistema.logic;

import logic.Protocol;
import logic.entities.*;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Proxy {
    private static Proxy theInstance;

    public static Proxy instance() {
        if (theInstance == null) theInstance = new Proxy();
        return theInstance;
    }

    private ObjectInputStream is;
    private ObjectOutputStream os;
    private Socket socket;
    private String sid; // Session ID

    public Proxy() {
        try {
            socket = new Socket(Protocol.SERVER, Protocol.PORT);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());

            // Indicar que es una conexión normal (SYNC)
            os.writeInt(Protocol.SYNC);
            os.flush();

            // Recibir el Session ID del servidor
            sid = (String) is.readObject();
            System.out.println("Conectado al servidor con SID: " + sid);

        } catch (Exception e) {
            System.err.println("Error conectando al servidor: " + e.getMessage());
            System.exit(-1);
        }
    }

    public String getSid() {
        return sid;
    }

    // ==========================================================
    // 🔹 ADMINISTRADOR
    // ==========================================================
    public synchronized void create(Administrador e) throws Exception {
        os.writeInt(Protocol.ADMINISTRADOR_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("ADMINISTRADOR DUPLICADO");
    }

    public synchronized Administrador read(Administrador e) throws Exception {
        os.writeInt(Protocol.ADMINISTRADOR_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Administrador) is.readObject();
        else throw new Exception("ADMINISTRADOR NO EXISTE");
    }

    public synchronized void update(Administrador e) throws Exception {
        os.writeInt(Protocol.ADMINISTRADOR_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("ADMINISTRADOR NO EXISTE");
    }

    public synchronized void delete(Administrador e) throws Exception {
        os.writeInt(Protocol.ADMINISTRADOR_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("ADMINISTRADOR NO EXISTE");
    }

    public synchronized List<Administrador> search(Administrador e) {
        try {
            os.writeInt(Protocol.ADMINISTRADOR_SEARCH);
            os.writeObject(e);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR)
                return (List<Administrador>) is.readObject();
            else return List.of();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    // ==========================================================
    // 🔹 DEPARTAMENTO
    // ==========================================================
    public synchronized void create(Departamento e) throws Exception {
        os.writeInt(Protocol.DEPARTAMENTO_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("DEPARTAMENTO DUPLICADO");
    }

    public synchronized Departamento read(Departamento e) throws Exception {
        os.writeInt(Protocol.DEPARTAMENTO_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Departamento) is.readObject();
        else throw new Exception("DEPARTAMENTO NO EXISTE");
    }

    public synchronized void update(Departamento e) throws Exception {
        os.writeInt(Protocol.DEPARTAMENTO_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("DEPARTAMENTO NO EXISTE");
    }

    public synchronized void delete(Departamento e) throws Exception {
        os.writeInt(Protocol.DEPARTAMENTO_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("DEPARTAMENTO NO EXISTE");
    }

    public synchronized List<Departamento> search(Departamento e) {
        try {
            os.writeInt(Protocol.DEPARTAMENTO_SEARCH);
            os.writeObject(e);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR)
                return (List<Departamento>) is.readObject();
            else return List.of();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    // ==========================================================
    // 🔹 FARMACEUTICO
    // ==========================================================
    public synchronized void create(Farmaceutico e) throws Exception {
        os.writeInt(Protocol.FARMACEUTICO_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("FARMACEUTICO DUPLICADO");
    }

    public synchronized Farmaceutico read(Farmaceutico e) throws Exception {
        os.writeInt(Protocol.FARMACEUTICO_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Farmaceutico) is.readObject();
        else throw new Exception("FARMACEUTICO NO EXISTE");
    }

    public synchronized void update(Farmaceutico e) throws Exception {
        os.writeInt(Protocol.FARMACEUTICO_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("FARMACEUTICO NO EXISTE");
    }

    public synchronized void delete(Farmaceutico e) throws Exception {
        os.writeInt(Protocol.FARMACEUTICO_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("FARMACEUTICO NO EXISTE");
    }

    public synchronized List<Farmaceutico> search(Farmaceutico e) {
        try {
            os.writeInt(Protocol.FARMACEUTICO_SEARCH);
            os.writeObject(e);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR)
                return (List<Farmaceutico>) is.readObject();
            else return List.of();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public synchronized void updateFarmaceutico(Farmaceutico farmaceutico) throws Exception {
        try {
            os.writeInt(Protocol.FARMACEUTICO_UPDATE);
            os.writeObject(farmaceutico);
            os.flush();

            int response = is.readInt();
            if (response != Protocol.ERROR_NO_ERROR) {
                throw new Exception("No se pudo actualizar el farmacéutico.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al actualizar el farmacéutico: " + e.getMessage());
        }
    }

    public synchronized List<Farmaceutico> searchFarmaceuticoByName(String nombre) {
        try {
            os.writeInt(Protocol.FARMACEUTICO_SEARCH_BY_NAME);
            os.writeUTF(nombre);
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (List<Farmaceutico>) is.readObject();
            } else {
                return List.of();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error buscando farmacéuticos: " + e.getMessage());
        }
    }

    // ==========================================================
    // 🔹 MEDICAMENTO
    // ==========================================================
    public synchronized void create(Medicamento e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("MEDICAMENTO DUPLICADO");
    }

    public synchronized Medicamento read(Medicamento e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Medicamento) is.readObject();
        else throw new Exception("MEDICAMENTO NO EXISTE");
    }

    public synchronized void update(Medicamento e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("MEDICAMENTO NO EXISTE");
    }

    public synchronized void delete(Medicamento e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("MEDICAMENTO NO EXISTE");
    }

    public synchronized List<Medicamento> search(Medicamento e) {
        try {
            os.writeInt(Protocol.MEDICAMENTO_SEARCH);
            os.writeObject(e);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR)
                return (List<Medicamento>) is.readObject();
            else return List.of();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public synchronized void updateMedicamento(Medicamento medicamento) throws Exception {
        try {
            os.writeInt(Protocol.MEDICAMENTO_UPDATE);
            os.writeObject(medicamento);
            os.flush();

            int response = is.readInt();
            if (response != Protocol.ERROR_NO_ERROR) {
                throw new Exception("No se pudo actualizar el medicamento.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al actualizar el medicamento: " + e.getMessage());
        }
    }

    public synchronized List<Medicamento> searchMedicamentoByCodigo(String codigo) {
        try {
            os.writeInt(Protocol.MEDICAMENTO_SEARCH_BY_CODIGO);
            os.writeUTF(codigo);
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (List<Medicamento>) is.readObject();
            } else {
                return List.of();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error buscando medicamentos: " + e.getMessage());
        }
    }

    public synchronized List<Medicamento> searchMedicamentoByName(String nombre) {
        try {
            os.writeInt(Protocol.MEDICAMENTO_SEARCH_BY_NAME);
            os.writeUTF(nombre);
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (List<Medicamento>) is.readObject();
            } else {
                return List.of();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error buscando medicamentos: " + e.getMessage());
        }
    }

    // ==========================================================
    // 🔹 MEDICAMENTO DETALLE
    // ==========================================================
    public synchronized void create(MedicamentoDetalle e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_DETALLE_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("DETALLE DUPLICADO");
    }

    public synchronized MedicamentoDetalle read(MedicamentoDetalle e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_DETALLE_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (MedicamentoDetalle) is.readObject();
        else throw new Exception("DETALLE NO EXISTE");
    }

    public synchronized void update(MedicamentoDetalle e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_DETALLE_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("DETALLE NO EXISTE");
    }

    public synchronized void delete(MedicamentoDetalle e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_DETALLE_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("DETALLE NO EXISTE");
    }

    public synchronized List<MedicamentoDetalle> search(MedicamentoDetalle e) {
        try {
            os.writeInt(Protocol.MEDICAMENTO_DETALLE_SEARCH);
            os.writeObject(e);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR)
                return (List<MedicamentoDetalle>) is.readObject();
            else return List.of();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    // ==========================================================
    // 🔹 MÉDICO
    // ==========================================================
    public synchronized void create(Medico e) throws Exception {
        os.writeInt(Protocol.MEDICO_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("MEDICO DUPLICADO");
    }

    public synchronized Medico read(Medico e) throws Exception {
        os.writeInt(Protocol.MEDICO_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Medico) is.readObject();
        else throw new Exception("MEDICO NO EXISTE");
    }

    public synchronized void update(Medico e) throws Exception {
        os.writeInt(Protocol.MEDICO_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("MEDICO NO EXISTE");
    }

    public synchronized void delete(Medico e) throws Exception {
        os.writeInt(Protocol.MEDICO_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("MEDICO NO EXISTE");
    }

    public synchronized List<Medico> search(Medico e) {
        try {
            os.writeInt(Protocol.MEDICO_SEARCH);
            os.writeObject(e);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Medico>) is.readObject();
            } else {
                return List.of();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public synchronized void updateMedico(Medico medico) throws Exception {
        try {
            os.writeInt(Protocol.MEDICO_UPDATE);
            os.writeObject(medico);
            os.flush();

            int response = is.readInt();
            if (response != Protocol.ERROR_NO_ERROR) {
                throw new Exception("No se pudo actualizar el médico.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al actualizar el médico: " + e.getMessage());
        }
    }

    // ==========================================================
    // 🔹 MENSAJE
    // ==========================================================
    public synchronized void create(Mensaje e) throws Exception {
        os.writeInt(Protocol.MENSAJE_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("MENSAJE DUPLICADO");
    }

    public synchronized Mensaje read(Mensaje e) throws Exception {
        os.writeInt(Protocol.MENSAJE_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Mensaje) is.readObject();
        else throw new Exception("MENSAJE NO EXISTE");
    }

    public synchronized void update(Mensaje e) throws Exception {
        os.writeInt(Protocol.MENSAJE_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("MENSAJE NO EXISTE");
    }

    public synchronized void delete(Mensaje e) throws Exception {
        os.writeInt(Protocol.MENSAJE_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("MENSAJE NO EXISTE");
    }

    public synchronized List<Mensaje> search(Mensaje e) {
        try {
            os.writeInt(Protocol.MENSAJE_SEARCH);
            os.writeObject(e);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR)
                return (List<Mensaje>) is.readObject();
            else return List.of();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    // ==========================================================
    // 🔹 PACIENTE
    // ==========================================================
    public synchronized void create(Paciente e) throws Exception {
        os.writeInt(Protocol.PACIENTE_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("PACIENTE DUPLICADO");
    }

    public synchronized Paciente read(Paciente e) throws Exception {
        os.writeInt(Protocol.PACIENTE_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Paciente) is.readObject();
        else throw new Exception("PACIENTE NO EXISTE");
    }

    public synchronized void update(Paciente e) throws Exception {
        os.writeInt(Protocol.PACIENTE_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("PACIENTE NO EXISTE");
    }

    public synchronized void delete(Paciente e) throws Exception {
        os.writeInt(Protocol.PACIENTE_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("PACIENTE NO EXISTE");
    }

    public synchronized List<Paciente> search(Paciente e) {
        try {
            os.writeInt(Protocol.PACIENTE_SEARCH);
            os.writeObject(e);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR)
                return (List<Paciente>) is.readObject();
            else return List.of();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public synchronized void updatePaciente(Paciente paciente) throws Exception {
        try {
            os.writeInt(Protocol.PACIENTE_UPDATE);
            os.writeObject(paciente);
            os.flush();

            int response = is.readInt();
            if (response != Protocol.ERROR_NO_ERROR) {
                throw new Exception("No se pudo actualizar el paciente.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al actualizar el paciente: " + e.getMessage());
        }
    }

    public synchronized List<Paciente> searchPacienteByName(String nombre) {
        try {
            os.writeInt(Protocol.PACIENTE_SEARCH);
            os.writeUTF(nombre);
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (List<Paciente>) is.readObject();
            } else {
                return List.of();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error buscando pacientes: " + e.getMessage());
        }
    }

    public synchronized List<Paciente> searchPacienteById(String id) {
        try {
            os.writeInt(Protocol.PACIENTE_SEARCH_BY_ID);
            os.writeUTF(id);
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (List<Paciente>) is.readObject();
            } else {
                return List.of();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error buscando paciente por ID: " + e.getMessage());
        }
    }

    public synchronized List<Paciente> searchPaciente(Paciente filtro) {
        try {
            os.writeInt(Protocol.PACIENTE_SEARCH);
            os.writeObject(filtro);
            os.flush();

            if (is.readInt() == Protocol.ERROR_NO_ERROR) {
                return (List<Paciente>) is.readObject();
            } else {
                return List.of();
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error buscando pacientes: " + ex.getMessage(), ex);
        }
    }

    // ==========================================================
    // 🔹 RECETA
    // ==========================================================
    public synchronized void create(Receta e) throws Exception {
        os.writeInt(Protocol.RECETA_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("RECETA DUPLICADA");
    }

    public synchronized Receta read(Receta e) throws Exception {
        os.writeInt(Protocol.RECETA_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Receta) is.readObject();
        else throw new Exception("RECETA NO EXISTE");
    }

    public synchronized void update(Receta e) throws Exception {
        os.writeInt(Protocol.RECETA_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("RECETA NO EXISTE");
    }

    public synchronized void delete(Receta e) throws Exception {
        os.writeInt(Protocol.RECETA_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("RECETA NO EXISTE");
    }

    public synchronized List<Receta> search(Receta e) {
        try {
            os.writeInt(Protocol.RECETA_SEARCH);
            os.writeObject(e);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR)
                return (List<Receta>) is.readObject();
            else return List.of();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public synchronized List<Receta> searchRecetaByIdPaciente(String idPaciente) {
        try {
            os.writeInt(Protocol.RECETA_SEARCH_BY_PACIENTE);
            os.writeUTF(idPaciente);
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (List<Receta>) is.readObject();
            } else {
                return List.of();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error buscando recetas: " + e.getMessage());
        }
    }

    public synchronized void avanzarEstado(Receta receta) throws Exception {
        try {
            os.writeInt(Protocol.RECETA_AVANZAR_ESTADO);
            os.writeObject(receta);
            os.flush();

            int response = is.readInt();
            if (response != Protocol.ERROR_NO_ERROR) {
                throw new Exception("No se pudo avanzar el estado de la receta.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al avanzar el estado de la receta: " + e.getMessage());
        }
    }

    public synchronized Receta createReceta(Receta receta) throws Exception {
        try {
            os.writeInt(Protocol.RECETA_CREATE);
            os.writeObject(receta);
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (Receta) is.readObject();
            } else {
                throw new Exception("No se pudo crear la receta.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al crear receta: " + e.getMessage());
        }
    }

    public synchronized Receta readReceta(Receta receta) throws Exception {
        try {
            os.writeInt(Protocol.RECETA_READ);
            os.writeObject(receta);
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (Receta) is.readObject();
            } else {
                throw new Exception("La receta no existe.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al leer la receta: " + e.getMessage());
        }
    }

    public synchronized void updateReceta(Receta receta) throws Exception {
        try {
            os.writeInt(Protocol.RECETA_UPDATE);
            os.writeObject(receta);
            os.flush();

            int response = is.readInt();
            if (response != Protocol.ERROR_NO_ERROR) {
                throw new Exception("No se pudo actualizar la receta.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al actualizar la receta: " + e.getMessage());
        }
    }

    public synchronized void removeMedicamentoFromReceta(int recetaId, int index) throws Exception {
        try {
            os.writeInt(Protocol.RECETA_REMOVE_MEDICAMENTO);
            os.writeInt(recetaId);
            os.writeInt(index);
            os.flush();

            int response = is.readInt();
            if (response != Protocol.ERROR_NO_ERROR) {
                throw new Exception("No se pudo eliminar el medicamento de la receta.");
            }
        } catch (IOException e) {
            throw new Exception("Error al eliminar medicamento de la receta: " + e.getMessage());
        }
    }

    public synchronized String generarDetallesReceta(Receta receta) {
        try {
            os.writeInt(Protocol.RECETA_GENERAR_DETALLES);
            os.writeObject(receta);
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return is.readUTF();
            } else {
                return "No se pudieron generar los detalles de la receta.";
            }
        } catch (IOException e) {
            throw new RuntimeException("Error generando detalles de la receta: " + e.getMessage());
        }
    }

    public synchronized List<Receta> searchRecetaListById(String id) {
        try {
            os.writeInt(Protocol.RECETA_SEARCH_LIST_BY_ID);
            os.writeUTF(id);
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (List<Receta>) is.readObject();
            } else {
                return List.of();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error buscando recetas: " + e.getMessage());
        }
    }

    public synchronized Receta modificarDetalleMedicamento(Window parent, Receta receta, int row) throws Exception {
        if (receta == null) {
            throw new Exception("Receta inválida");
        }

        if (receta.getMedicamentos() == null || receta.getMedicamentos().isEmpty()) {
            throw new Exception("La receta no tiene medicamentos");
        }

        if (row < 0 || row >= receta.getMedicamentos().size()) {
            throw new Exception("Índice inválido para modificar detalle");
        }

        MedicamentoDetalle detalle = receta.getMedicamentos().get(row);

        sistema.presentation.prescribirModificarDetalle.prescribirModificarDetalle dialog =
                new sistema.presentation.prescribirModificarDetalle.prescribirModificarDetalle(parent, detalle);
        dialog.setVisible(true);

        if (dialog.isGuardado()) {
            receta.getMedicamentos().set(row, dialog.take());
        }

        return receta;
    }

    // ==========================================================
    // 🔹 USUARIO
    // ==========================================================
    public synchronized void create(Usuario e) throws Exception {
        os.writeInt(Protocol.USUARIO_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("USUARIO DUPLICADO");
    }

    public synchronized Usuario read(Usuario e) throws Exception {
        os.writeInt(Protocol.USUARIO_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Usuario) is.readObject();
        else throw new Exception("USUARIO NO EXISTE");
    }

    public synchronized void update(Usuario e) throws Exception {
        os.writeInt(Protocol.USUARIO_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("USUARIO NO EXISTE");
    }

    public synchronized void delete(Usuario e) throws Exception {
        os.writeInt(Protocol.USUARIO_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("USUARIO NO EXISTE");
    }

    public synchronized List<Usuario> search(Usuario e) {
        try {
            os.writeInt(Protocol.USUARIO_SEARCH);
            os.writeObject(e);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR)
                return (List<Usuario>) is.readObject();
            else
                return List.of();
        } catch (Exception ex) {
            throw new RuntimeException("Error buscando usuarios: " + ex.getMessage(), ex);
        }
    }


    public synchronized Usuario findUserById(String id) throws Exception {
        try {
            os.writeInt(Protocol.USUARIO_FIND_BY_ID);
            os.writeObject(id);
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (Usuario) is.readObject();
            } else {
                throw new Exception("USUARIO NO EXISTE");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error buscando usuario por ID: " + e.getMessage());
        }
    }

    public synchronized Usuario login(Usuario usuario) throws Exception {
        try {
            os.writeInt(Protocol.USUARIO_LOGIN); // enviamos operación
            os.writeObject(usuario);             // enviamos usuario con ID y contraseña
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (Usuario) is.readObject(); // usuario encontrado y válido
            } else {
                throw new Exception("Usuario o contraseña incorrectos.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error en login: " + e.getMessage());
        }
    }

    // ==========================================================
    // 🔹 DESCONECTAR
    // ==========================================================
    public synchronized void disconnect() {
        try {
            os.writeInt(Protocol.DISCONNECT);
            os.flush();
            is.close();
            os.close();
            socket.close();
            theInstance = null;
        } catch (IOException e) {
            System.err.println("Error al desconectar: " + e.getMessage());
        }
    }
    public synchronized void sendUserMessage(String destinatario, String mensaje) throws Exception {
        os.writeInt(Protocol.USUARIO_ENVIAR_MENSAJE);
        os.writeUTF(destinatario);
        os.writeUTF(mensaje);
        os.flush();

        int response = is.readInt();
        if (response != Protocol.ERROR_NO_ERROR) {
            throw new Exception("No se pudo enviar el mensaje a " + destinatario);
        }
    }
    public void stop() {
        try {
            disconnect();
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    public List<Usuario> getUsuariosActivos() {
        try {
            os.writeInt(Protocol.USUARIOS_ACTIVOS);
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (List<Usuario>) is.readObject();
            } else {
                return List.of();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo usuarios activos: " + e.getMessage());
        }
    }

};
