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
    public static Proxy instance(){
        if (theInstance == null) theInstance = new Proxy();
        return theInstance;
    }
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private Socket socket;
    public Proxy() {
        try {
            socket = new Socket(Protocol.SERVER, Protocol.PORT);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) { System.exit(-1);}
    }
    // ==========================================================
    // 🔹 ADMINISTRADOR
    // ==========================================================
    public void create(Administrador e) throws Exception {
        os.writeInt(Protocol.ADMINISTRADOR_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("ADMINISTRADOR DUPLICADO");
    }

    public Administrador read(Administrador e) throws Exception {
        os.writeInt(Protocol.ADMINISTRADOR_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Administrador) is.readObject();
        else throw new Exception("ADMINISTRADOR NO EXISTE");
    }

    public void update(Administrador e) throws Exception {
        os.writeInt(Protocol.ADMINISTRADOR_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("ADMINISTRADOR NO EXISTE");
    }

    public void delete(Administrador e) throws Exception {
        os.writeInt(Protocol.ADMINISTRADOR_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("ADMINISTRADOR NO EXISTE");
    }

    public List<Administrador> search(Administrador e) {
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
    public void create(Departamento e) throws Exception {
        os.writeInt(Protocol.DEPARTAMENTO_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("DEPARTAMENTO DUPLICADO");
    }

    public Departamento read(Departamento e) throws Exception {
        os.writeInt(Protocol.DEPARTAMENTO_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Departamento) is.readObject();
        else throw new Exception("DEPARTAMENTO NO EXISTE");
    }

    public void update(Departamento e) throws Exception {
        os.writeInt(Protocol.DEPARTAMENTO_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("DEPARTAMENTO NO EXISTE");
    }

    public void delete(Departamento e) throws Exception {
        os.writeInt(Protocol.DEPARTAMENTO_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("DEPARTAMENTO NO EXISTE");
    }

    public List<Departamento> search(Departamento e) {
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
    public void create(Farmaceutico e) throws Exception {
        os.writeInt(Protocol.FARMACEUTICO_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("FARMACEUTICO DUPLICADO");
    }

    public Farmaceutico read(Farmaceutico e) throws Exception {
        os.writeInt(Protocol.FARMACEUTICO_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Farmaceutico) is.readObject();
        else throw new Exception("FARMACEUTICO NO EXISTE");
    }

    public void update(Farmaceutico e) throws Exception {
        os.writeInt(Protocol.FARMACEUTICO_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("FARMACEUTICO NO EXISTE");
    }

    public void delete(Farmaceutico e) throws Exception {
        os.writeInt(Protocol.FARMACEUTICO_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("FARMACEUTICO NO EXISTE");
    }

    public List<Farmaceutico> search(Farmaceutico e) {
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

    // ==========================================================
    // 🔹 MEDICAMENTO
    // ==========================================================
    public void create(Medicamento e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("MEDICAMENTO DUPLICADO");
    }

    public Medicamento read(Medicamento e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Medicamento) is.readObject();
        else throw new Exception("MEDICAMENTO NO EXISTE");
    }

    public void update(Medicamento e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("MEDICAMENTO NO EXISTE");
    }

    public void delete(Medicamento e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("MEDICAMENTO NO EXISTE");
    }

    public List<Medicamento> search(Medicamento e) {
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

    // ==========================================================
    // 🔹 MEDICAMENTO DETALLE
    // ==========================================================
    public void create(MedicamentoDetalle e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_DETALLE_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("DETALLE DUPLICADO");
    }

    public MedicamentoDetalle read(MedicamentoDetalle e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_DETALLE_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (MedicamentoDetalle) is.readObject();
        else throw new Exception("DETALLE NO EXISTE");
    }

    public void update(MedicamentoDetalle e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_DETALLE_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("DETALLE NO EXISTE");
    }

    public void delete(MedicamentoDetalle e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_DETALLE_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("DETALLE NO EXISTE");
    }

    public List<MedicamentoDetalle> search(MedicamentoDetalle e) {
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
    public void create(Medico e) throws Exception {
        os.writeInt(Protocol.MEDICO_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("MEDICO DUPLICADO");
    }

    public Medico read(Medico e) throws Exception {
        os.writeInt(Protocol.MEDICO_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Medico) is.readObject();
        else throw new Exception("MEDICO NO EXISTE");
    }

    public void update(Medico e) throws Exception {
        os.writeInt(Protocol.MEDICO_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("MEDICO NO EXISTE");
    }

    public void delete(Medico e) throws Exception {
        os.writeInt(Protocol.MEDICO_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("MEDICO NO EXISTE");
    }

    public List<Medico> search(Medico e) {
        try {
            System.out.println("Pidiendo Lista");
            os.writeInt(Protocol.MEDICO_SEARCH);
            os.writeObject(e);
            os.flush();
            System.out.println("Pidiendo Lista");
            if (is.readInt() == Protocol.ERROR_NO_ERROR) {

                return (List<Medico>) is.readObject();
            }else{
                System.out.println("Encontrada");
                return List.of();}
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    // ==========================================================
    // 🔹 MENSAJE
    // ==========================================================
    public void create(Mensaje e) throws Exception {
        os.writeInt(Protocol.MENSAJE_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("MENSAJE DUPLICADO");
    }

    public Mensaje read(Mensaje e) throws Exception {
        os.writeInt(Protocol.MENSAJE_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Mensaje) is.readObject();
        else throw new Exception("MENSAJE NO EXISTE");
    }

    public void update(Mensaje e) throws Exception {
        os.writeInt(Protocol.MENSAJE_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("MENSAJE NO EXISTE");
    }

    public void delete(Mensaje e) throws Exception {
        os.writeInt(Protocol.MENSAJE_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("MENSAJE NO EXISTE");
    }

    public List<Mensaje> search(Mensaje e) {
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
    public void create(Paciente e) throws Exception {
        os.writeInt(Protocol.PACIENTE_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("PACIENTE DUPLICADO");
    }

    public Paciente read(Paciente e) throws Exception {
        os.writeInt(Protocol.PACIENTE_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Paciente) is.readObject();
        else throw new Exception("PACIENTE NO EXISTE");
    }

    public void update(Paciente e) throws Exception {
        os.writeInt(Protocol.PACIENTE_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("PACIENTE NO EXISTE");
    }

    public void delete(Paciente e) throws Exception {
        os.writeInt(Protocol.PACIENTE_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("PACIENTE NO EXISTE");
    }

    public List<Paciente> search(Paciente e) {
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

    // ==========================================================
    // 🔹 RECETA
    // ==========================================================
    public void create(Receta e) throws Exception {
        os.writeInt(Protocol.RECETA_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("RECETA DUPLICADA");
    }

    public Receta read(Receta e) throws Exception {
        os.writeInt(Protocol.RECETA_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Receta) is.readObject();
        else throw new Exception("RECETA NO EXISTE");
    }

    public void update(Receta e) throws Exception {
        os.writeInt(Protocol.RECETA_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("RECETA NO EXISTE");
    }

    public void delete(Receta e) throws Exception {
        os.writeInt(Protocol.RECETA_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("RECETA NO EXISTE");
    }

    public List<Receta> search(Receta e) {
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
    public List<Receta> searchRecetaByIdPaciente(String idPaciente) {
        try {
            os.writeInt(Protocol.RECETA_SEARCH_BY_PACIENTE); // nuevo código en Protocol
            os.writeUTF(idPaciente);                         // enviamos el ID del paciente
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (List<Receta>) is.readObject();
            } else {
                return List.of(); // lista vacía si no hay recetas
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error buscando recetas: " + e.getMessage());
        }
    }
    public void avanzarEstado(Receta receta) throws Exception {
        try {
            os.writeInt(Protocol.RECETA_AVANZAR_ESTADO); // enviamos operación
            os.writeObject(receta);                      // enviamos la receta
            os.flush();

            int response = is.readInt();
            if (response != Protocol.ERROR_NO_ERROR) {
                throw new Exception("No se pudo avanzar el estado de la receta.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al avanzar el estado de la receta: " + e.getMessage());
        }
    }

    // ==========================================================
    // 🔹 USUARIO
    // ==========================================================
    public void create(Usuario e) throws Exception {
        os.writeInt(Protocol.USUARIO_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("USUARIO DUPLICADO");
    }

    public Usuario read(Usuario e) throws Exception {
        os.writeInt(Protocol.USUARIO_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Usuario) is.readObject();
        else throw new Exception("USUARIO NO EXISTE");
    }

    public void update(Usuario e) throws Exception {
        os.writeInt(Protocol.USUARIO_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("USUARIO NO EXISTE");
    }

    public void delete(Usuario e) throws Exception {
        os.writeInt(Protocol.USUARIO_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.ERROR_NO_ERROR)
            throw new Exception("USUARIO NO EXISTE");
    }

    public List<Usuario> search(Usuario e) {
        try {
            os.writeInt(Protocol.USUARIO_SEARCH);
            os.writeObject(e);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR)
                return (List<Usuario>) is.readObject();
            else return List.of();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    public Usuario findUserById(String userId) throws Exception {
        try {
            os.writeInt(Protocol.USUARIO_FIND_BY_ID); // Código de operación
            os.writeObject(userId);                   // Enviamos el ID como objeto
            os.flush();

            int response = is.readInt();              // Leemos respuesta del backend
            if (response == Protocol.ERROR_NO_ERROR) {
                return (Usuario) is.readObject();     // Usuario encontrado
            } else {
                return null;                          // No existe o error
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al buscar usuario: " + e.getMessage());
        }
    }
    public Usuario login(Usuario usuario) throws Exception {
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



    private void disconnect() throws Exception {
        os.writeInt(Protocol.DISCONNECT);
        os.flush();
        socket.shutdownOutput();
        socket.close();
    }

    public void stop() {
        try {
            disconnect();
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    public void updateUsuario(Usuario user) throws Exception {
        try {
            os.writeInt(Protocol.USUARIO_UPDATE); // Código de operación
            os.writeObject(user);                 // Enviamos el objeto Usuario
            os.flush();

            int response = is.readInt();          // Leemos la respuesta del worker
            if (response != Protocol.ERROR_NO_ERROR) {
                throw new Exception("No se pudo actualizar el usuario.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    public String generarDetallesReceta(Receta receta) {
        try {
            os.writeInt(Protocol.RECETA_GENERAR_DETALLES); // enviamos operación
            os.writeObject(receta);                        // enviamos la receta
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return is.readUTF(); // recibimos el detalle como String
            } else {
                return "No se pudieron generar los detalles de la receta.";
            }
        } catch (IOException e) {
            throw new RuntimeException("Error generando detalles de la receta: " + e.getMessage());
        }
    }

    public void updateFarmaceutico(Farmaceutico farmaceutico) throws Exception {
        try {
            os.writeInt(Protocol.FARMACEUTICO_UPDATE); // operación
            os.writeObject(farmaceutico);             // enviamos objeto
            os.flush();

            int response = is.readInt();
            if (response != Protocol.ERROR_NO_ERROR) {
                throw new Exception("No se pudo actualizar el farmacéutico.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al actualizar el farmacéutico: " + e.getMessage());
        }
    }

    public List<Farmaceutico> searchFarmaceuticoByName(String nombre) {
        try {
            os.writeInt(Protocol.FARMACEUTICO_SEARCH_BY_NAME); // enviamos operación
            os.writeUTF(nombre);                               // enviamos nombre
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (List<Farmaceutico>) is.readObject();
            } else {
                return List.of(); // lista vacía si no hay resultados
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error buscando farmacéuticos: " + e.getMessage());
        }
    }

    public List<Receta> searchRecetaListById(String id) {
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
    public void updateMedicamento(Medicamento medicamento) throws Exception {
        try {
            os.writeInt(Protocol.MEDICAMENTO_UPDATE); // enviamos operación
            os.writeObject(medicamento);             // enviamos objeto
            os.flush();

            int response = is.readInt();
            if (response != Protocol.ERROR_NO_ERROR) {
                throw new Exception("No se pudo actualizar el medicamento.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al actualizar el medicamento: " + e.getMessage());
        }
    }
    public List<Medicamento> searchMedicamentoByCodigo(String codigo) {
        try {
            os.writeInt(Protocol.MEDICAMENTO_SEARCH_BY_CODIGO); // enviamos operación
            os.writeUTF(codigo);                                // enviamos código
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (List<Medicamento>) is.readObject();    // recibimos lista
            } else {
                return List.of();                               // lista vacía si no hay resultados
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error buscando medicamentos: " + e.getMessage());
        }
    }

    public void updateMedico(Medico medico) throws Exception {
        try {
            os.writeInt(Protocol.MEDICO_UPDATE); // enviamos operación
            os.writeObject(medico);              // enviamos objeto
            os.flush();

            int response = is.readInt();
            if (response != Protocol.ERROR_NO_ERROR) {
                throw new Exception("No se pudo actualizar el médico.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al actualizar el médico: " + e.getMessage());
        }
    }

    public void updatePaciente(Paciente paciente) throws Exception {
        try {
            os.writeInt(Protocol.PACIENTE_UPDATE); // enviamos operación
            os.writeObject(paciente);             // enviamos objeto
            os.flush();

            int response = is.readInt();
            if (response != Protocol.ERROR_NO_ERROR) {
                throw new Exception("No se pudo actualizar el paciente.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al actualizar el paciente: " + e.getMessage());
        }
    }

    public List<Paciente> searchPacienteByName(String nombre) {
        try {
            os.writeInt(Protocol.PACIENTE_SEARCH); // enviamos operación
            os.writeUTF(nombre);                    // enviamos nombre
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (List<Paciente>) is.readObject(); // recibimos lista
            } else {
                return List.of();                        // lista vacía si no hay resultados
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error buscando pacientes: " + e.getMessage());
        }
    }

    public List<Paciente> searchPacienteById(String id) {
        try {
            os.writeInt(Protocol.PACIENTE_SEARCH_BY_ID); // enviamos operación
            os.writeUTF(id);                             // enviamos ID
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (List<Paciente>) is.readObject(); // recibimos lista
            } else {
                return List.of();                        // lista vacía si no existe
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error buscando paciente por ID: " + e.getMessage());
        }
    }

    public List<Medicamento> searchMedicamentoByName(String nombre) {
        try {
            os.writeInt(Protocol.MEDICAMENTO_SEARCH_BY_NAME); // operación
            os.writeUTF(nombre);                              // enviamos nombre
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (List<Medicamento>) is.readObject();  // lista de resultados
            } else {
                return List.of();                             // lista vacía si no hay resultados
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error buscando medicamentos: " + e.getMessage());
        }
    }
    public Receta createReceta(Receta receta) throws Exception {
        try {
            os.writeInt(Protocol.RECETA_CREATE); // enviamos operación
            os.writeObject(receta);              // enviamos la receta
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (Receta) is.readObject(); // recibimos la receta creada
            } else {
                throw new Exception("No se pudo crear la receta.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al crear receta: " + e.getMessage());
        }
    }
    public Receta readReceta(Receta receta) throws Exception {
        try {
            os.writeInt(Protocol.RECETA_READ); // enviamos operación
            os.writeObject(receta);            // enviamos la receta con ID
            os.flush();

            int response = is.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                return (Receta) is.readObject(); // recibimos la receta completa
            } else {
                throw new Exception("La receta no existe.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al leer la receta: " + e.getMessage());
        }

    }
    public void updateReceta(Receta receta) throws Exception {
        try {
            os.writeInt(Protocol.RECETA_UPDATE); // enviamos operación
            os.writeObject(receta);              // enviamos la receta actualizada
            os.flush();

            int response = is.readInt();
            if (response != Protocol.ERROR_NO_ERROR) {
                throw new Exception("No se pudo actualizar la receta.");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("Error al actualizar la receta: " + e.getMessage());
        }
    }
    public void removeMedicamentoFromReceta(int recetaId, int index) throws Exception {
        try {
            os.writeInt(Protocol.RECETA_REMOVE_MEDICAMENTO); // operación
            os.writeInt(recetaId);                          // enviamos ID como int
            os.writeInt(index);                             // índice del medicamento
            os.flush();

            int response = is.readInt();
            if (response != Protocol.ERROR_NO_ERROR) {
                throw new Exception("No se pudo eliminar el medicamento de la receta.");
            }
        } catch (IOException e) {
            throw new Exception("Error al eliminar medicamento de la receta: " + e.getMessage());
        }
    }
    public Receta modificarDetalleMedicamento(Window parent, Receta receta, int row) throws Exception {
        // Validar que la receta tenga medicamentos
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

    public List<Paciente> searchPaciente(Paciente filtro) {
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
}
