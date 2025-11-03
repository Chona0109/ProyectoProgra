package sistema.logic;

import logic.Protocol;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import logic.entities.*;

public class Worker {
    Server srv;
    Socket s;
    ObjectOutputStream os;
    ObjectInputStream is;
    Service service;

    String sid; // Session Id
    Socket as; // Asynchronous Socket
    ObjectOutputStream aos;
    ObjectInputStream ais;
    private Usuario usuarioConectado;

    public Worker(Server srv, Socket s, ObjectOutputStream os, ObjectInputStream is, String sid, Service service) {
        this.srv = srv;
        this.s = s;
        this.os = os;
        this.is = is;
        this.service = service;
        this.sid = sid;
    }

    public void setAs(Socket as, ObjectOutputStream aos, ObjectInputStream ais) {
        this.as = as;
        this.aos = aos;
        this.ais = ais;
    }

    private String usuarioId;
    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    boolean continuar;
    public void start(){
        try {
            System.out.println("Worker atendiendo peticiones...");
            Thread t = new Thread(new Runnable(){
                public void run(){
                    listen();
                }
            });
            continuar = true;
            t.start();
        } catch (Exception ex) { }
    }

    public void stop(){
        continuar=false;
        System.out.println("Conexion cerrada...");
    }

    public void listen() {
        int method;
        while (continuar) {
            try {
                method = is.readInt();
                System.out.println("Operacion: " + method);

                switch (method) {

                    // ===================== DEPARTAMENTO =====================
                    case Protocol.DEPARTAMENTO_SEARCH:
                        try {
                            List<Departamento> ld = service.search((Departamento) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(ld);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;

                    // ===================== FARMACEUTICO =====================
                    case Protocol.FARMACEUTICO_CREATE:
                        try {
                            service.create((Farmaceutico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver_message(this, "Farmacéutico creado");
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTICO_READ:
                        try {
                            Farmaceutico f = service.read((Farmaceutico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(f);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTICO_UPDATE:
                        try {
                            service.updateFarmaceutico((Farmaceutico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver_message(this, "Farmacéutico actualizado");
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTICO_DELETE:
                        try {
                            service.delete((Farmaceutico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver_message(this, "Farmacéutico eliminado");
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTICO_SEARCH_BY_NAME:
                        try {
                            String nombre = is.readUTF();
                            List<Farmaceutico> lista = service.searchFarmaceuticoByName(nombre);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lista);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en FARMACEUTICO_SEARCH_BY_NAME: " + ex.getMessage());
                        }
                        break;
                    case Protocol.FARMACEUTICO_SEARCH:
                        try {
                            Farmaceutico filtro = (Farmaceutico) is.readObject();
                            List<Farmaceutico> lista = service.searchFarmaceutico(filtro);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lista);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en FARMACEUTICO_SEARCH: " + ex.getMessage());
                        }
                        break;

                    // ===================== MEDICAMENTO =====================
                    case Protocol.MEDICAMENTO_CREATE:
                        try {
                            service.create((Medicamento) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver_message(this, "Medicamento creado");
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_READ:
                        try {
                            Medicamento m = service.read((Medicamento) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(m);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_UPDATE:
                        try {
                            service.updateMedicamento((Medicamento) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver_message(this, "Medicamento actualizado");
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_DELETE:
                        try {
                            service.delete((Medicamento) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver_message(this, "Medicamento eliminado");
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_SEARCH_BY_CODIGO:
                        try {
                            String codigo = is.readUTF();
                            List<Medicamento> lista = service.searchMedicamentoByCodigo(codigo);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lista);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en MEDICAMENTO_SEARCH_BY_CODIGO: " + ex.getMessage());
                        }
                        break;
                    case Protocol.MEDICAMENTO_SEARCH:
                        try {
                            Medicamento filtro = (Medicamento) is.readObject();
                            List<Medicamento> lista = service.searchMedicamento(filtro);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lista);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en MEDICAMENTO_SEARCH: " + ex.getMessage());
                        }
                        break;
                    case Protocol.MEDICAMENTO_SEARCH_BY_NAME:
                        try {
                            String nombre = is.readUTF();
                            List<Medicamento> lista = service.searchMedicamentoByName(nombre);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lista);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en MEDICAMENTO_SEARCH_BY_NAME: " + ex.getMessage());
                        }
                        break;

                    // ===================== MEDICO =====================
                    case Protocol.MEDICO_CREATE:
                        try {
                            service.create((Medico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver_message(this, "Médico creado");
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_READ:
                        try {
                            Medico me = service.read((Medico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(me);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_UPDATE:
                        try {
                            service.updateMedico((Medico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver_message(this, "Médico actualizado");
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_DELETE:
                        try {
                            service.delete((Medico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver_message(this, "Médico eliminado");
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_SEARCH:
                        try {
                            List<Medico> lme = service.search((Medico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lme);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;

                    // ===================== PACIENTE =====================
                    case Protocol.PACIENTE_CREATE:
                        try {
                            service.create((Paciente) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver_message(this, "Paciente creado");
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_READ:
                        try {
                            Paciente pa = service.read((Paciente) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(pa);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_UPDATE:
                        try {
                            service.updatePaciente((Paciente) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver_message(this, "Paciente actualizado");
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_DELETE:
                        try {
                            service.delete((Paciente) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver_message(this, "Paciente eliminado");
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_SEARCH:
                        try {
                            List<Paciente> lpac = service.searchPaciente((Paciente) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lpac);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_SEARCH_BY_NAME:
                        try {
                            String nombre = is.readUTF();
                            List<Paciente> lista = service.searchPacienteByName(nombre);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lista);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en PACIENTE_SEARCH: " + ex.getMessage());
                        }
                        break;
                    case Protocol.PACIENTE_SEARCH_BY_ID:
                        try {
                            String id = is.readUTF();
                            List<Paciente> lista = service.searchPacienteById(id);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lista);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en PACIENTE_SEARCH_BY_ID: " + ex.getMessage());
                        }
                        break;

                    // ===================== RECETA =====================
                    case Protocol.RECETA_CREATE:
                        try {
                            Receta r = (Receta) is.readObject();
                            Receta creada = service.createReceta(r);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(creada);
                            srv.deliver_message(this, "Receta creada");
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en RECETA_CREATE: " + ex.getMessage());
                        }
                        break;
                    case Protocol.RECETA_READ:
                        try {
                            Receta r = service.readReceta((Receta) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(r);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.RECETA_UPDATE:
                        try {
                            service.updateReceta((Receta) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver_message(this, "Receta actualizada");
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.RECETA_SEARCH:
                        try {
                            List<Receta> lr = service.searchReceta((Receta) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lr);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.RECETA_SEARCH_BY_PACIENTE:
                        try {
                            String idPaciente = is.readUTF();
                            List<Receta> recetas = service.searchRecetaByIdPaciente(idPaciente);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(recetas);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en RECETA_SEARCH_BY_PACIENTE: " + ex.getMessage());
                        }
                        break;
                    case Protocol.RECETA_AVANZAR_ESTADO:
                        try {
                            Receta r = (Receta) is.readObject();
                            service.avanzarEstado(r);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver_message(this, "Estado de receta avanzado");
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en RECETA_AVANZAR_ESTADO: " + ex.getMessage());
                        }
                        break;
                    case Protocol.RECETA_GENERAR_DETALLES:
                        try {
                            Receta r = (Receta) is.readObject();
                            String detalles = service.generarDetallesReceta(r);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeUTF(detalles);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en RECETA_GENERAR_DETALLES: " + ex.getMessage());
                        }
                        break;
                    case Protocol.RECETA_SEARCH_BY_ID:
                        try {
                            String id = is.readUTF();
                            List<Receta> recetas = service.searchRecetaById(id);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(recetas);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en RECETA_SEARCH_BY_ID: " + ex.getMessage());
                        }
                        break;
                    case Protocol.RECETA_REMOVE_MEDICAMENTO:
                        try {
                            int recetaId = is.readInt();
                            int index = is.readInt();
                            service.removeMedicamentoFromReceta(recetaId, index);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver_message(this, "Medicamento eliminado de receta");
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en RECETA_REMOVE_MEDICAMENTO: " + ex.getMessage());
                        }
                        break;

                    // ===================== USUARIO =====================
                    case Protocol.USUARIO_READ:
                        try {
                            Usuario u = service.read((Usuario) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(u);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.USUARIO_UPDATE:
                        try {
                            service.updateUsuario((Usuario) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            srv.deliver_message(this, "Usuario actualizado");
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.USUARIO_FIND_BY_ID:
                        try {
                            String id = (String) is.readObject();
                            Usuario u = service.findUserById(id);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(u);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.USUARIO_LOGIN:
                        try {
                            Usuario u = (Usuario) is.readObject();
                            Usuario logged = service.login(u);
                            if (logged != null) {
                                os.writeInt(Protocol.ERROR_NO_ERROR);
                                os.writeObject(logged);

                                setUsuarioId(logged.getId());         // asigna ID al Worker
                                srv.notifyUserOnline(logged.getId()); // notifica a todos que está online

                            } else {
                                os.writeInt(Protocol.ERROR_ERROR);
                            }
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en USUARIO_LOGIN: " + ex.getMessage());
                        }
                        break;

                    case Protocol.USUARIO_ENVIAR_MENSAJE:
                        try {
                            String destinatario = is.readUTF();
                            String mensaje = is.readUTF();

                            if (usuarioId == null) {
                                os.writeInt(Protocol.ERROR_ERROR);
                                break;
                            }

                            // Obtener Worker del destinatario
                            Worker destinatarioWorker = srv.getWorkerByUsuarioId(destinatario);
                            if (destinatarioWorker != null) {
                                // Enviar mensaje usando deliver_message
                                destinatarioWorker.deliver_message("De " + usuarioId + ": " + mensaje);
                            }

                            os.writeInt(Protocol.ERROR_NO_ERROR); // confirma al emisor
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error enviando mensaje: " + ex.getMessage());
                        }
                        break;
                    // En Worker.java - Dentro del método listen(), reemplaza el caso USUARIOS_ACTIVOS:

                    case Protocol.USUARIOS_ACTIVOS:
                        try {
                            // Obtener usuarios activos del SERVICE, no del servidor
                            List<Usuario> activos = service.getUsuariosActivos();

                            System.out.println("→ USUARIOS_ACTIVOS solicitado por worker");
                            System.out.println("  Total usuarios activos: " + activos.size());

                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(activos);
                            os.flush();

                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error enviando usuarios activos: " + e.getMessage());
                            e.printStackTrace();
                        }
                        break;


                    // ===================== DESCONECTAR =====================
                    case Protocol.DISCONNECT:
                        try {
                            System.out.println("→ DISCONNECT recibido de worker");

                            // Remover usuario del service
                            if (usuarioId != null && !usuarioId.isEmpty()) {
                                Usuario u = new Usuario();
                                u.setId(usuarioId);
                                service.logout(u);

                                srv.notifyUserOffline(usuarioId);

                                System.out.println("✓ Usuario deslogueado: " + usuarioId);
                            }

                            stop();
                            srv.remove(this);

                        } catch (Exception e) {
                            System.err.println("Error en DISCONNECT: " + e.getMessage());
                            stop();
                            srv.remove(this);
                        }
                        break;
                }

                os.flush();

            } catch (IOException e) {
                stop();
            }
        }
    }

    public synchronized void deliver_message(String message) {
        if (as != null) {
            try {
                aos.writeInt(Protocol.DELIVER_MESSAGE);
                aos.writeObject(message);
                aos.flush();
            } catch (Exception e) {

                System.err.println("Error enviando mensaje: " + e.getMessage());
            }
        }
    }
}