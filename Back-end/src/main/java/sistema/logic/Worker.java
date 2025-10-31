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
    Service service;
    ObjectOutputStream os;
    ObjectInputStream is;

    public Worker(Server srv, Socket s, Service service) {
        try{
            this.srv=srv;
            this.s=s;
            os = new ObjectOutputStream(s.getOutputStream());
            is = new ObjectInputStream(s.getInputStream());
            this.service=service;
        } catch (IOException ex) { System.out.println(ex); }
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
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTICO_DELETE:
                        try {
                            service.delete((Farmaceutico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
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

                        //FARMACEUTICO SEARCH???

                    // ===================== MEDICAMENTO =====================
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
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_DELETE:
                        try {
                            service.delete((Medicamento) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_SEARCH_BY_CODIGO:
                        try {
                            String codigo = is.readUTF();                           // recibimos código
                            List<Medicamento> lista = service.searchMedicamentoByCodigo(codigo); // servicio
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lista);                                  // enviamos lista al proxy
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en MEDICAMENTO_SEARCH_BY_CODIGO: " + ex.getMessage());
                        }
                        break;
                    //Medicamento SEARCH???

                    // ===================== MEDICAMENTO DETALLE =====================



                    //Falta todo??

                    // ===================== MEDICO =====================
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
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_DELETE:
                        try {
                            service.delete((Medico) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
//                    case Protocol.MEDICO_SEARCH:
//                        try {
//                            List<Medico> lme = service.search((Medico) is.readObject());
//                            os.writeInt(Protocol.ERROR_NO_ERROR);
//                            os.writeObject(lme);
//                        } catch (Exception ex) {
//                            os.writeInt(Protocol.ERROR_ERROR);
//                        }
//                        break;
                    case Protocol.MEDICAMENTO_SEARCH_BY_NAME:
                        try {
                            String nombre = is.readUTF();
                            List<Medicamento> lista = service.searchMedicamentoByName(nombre); // servicio
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lista);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en MEDICAMENTO_SEARCH_BY_NAME: " + ex.getMessage());
                        }
                        break;


                    // ===================== PACIENTE =====================
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
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_DELETE:
                        try {
                            service.delete((Paciente) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
//                    case Protocol.PACIENTE_SEARCH:
//                        try {
//                            List<Paciente> lpac = service.search((Paciente) is.readObject());
//                            os.writeInt(Protocol.ERROR_NO_ERROR);
//                            os.writeObject(lpac);
//                        } catch (Exception ex) {
//                            os.writeInt(Protocol.ERROR_ERROR);
//                        }
//                        break;
                    case Protocol.PACIENTE_SEARCH:
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
                            List<Paciente> lista = service.searchPacienteById(id); // ahora devuelve lista
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(lista);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en PACIENTE_SEARCH_BY_ID: " + ex.getMessage());
                        }
                        break;


                    // ===================== RECETA =====================
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
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
//                    case Protocol.RECETA_DELETE:
//                        try {
//                            service.delete((Receta) is.readObject());
//                            os.writeInt(Protocol.ERROR_NO_ERROR);
//                        } catch (Exception ex) {
//                            os.writeInt(Protocol.ERROR_ERROR);
//                        }
//                        break;
//                    case Protocol.RECETA_SEARCH:
//                        try {
//                            List<Receta> lr = service.search((Receta) is.readObject());
//                            os.writeInt(Protocol.ERROR_NO_ERROR);
//                            os.writeObject(lr);
//                        } catch (Exception ex) {
//                            os.writeInt(Protocol.ERROR_ERROR);
//                        }
//                        break;
                    case Protocol.RECETA_SEARCH_BY_PACIENTE:
                        try {
                            String idPaciente = is.readUTF(); // recibimos el ID del paciente
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
                            service.avanzarEstado(r); // método en el servicio
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en RECETA_AVANZAR_ESTADO: " + ex.getMessage());
                        }
                        break;
                    case Protocol.RECETA_GENERAR_DETALLES:
                        try {
                            Receta r = (Receta) is.readObject();
                            String detalles = service.generarDetallesReceta(r); // método en el servicio
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeUTF(detalles);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en RECETA_GENERAR_DETALLES: " + ex.getMessage());
                        }
                        break;
                    case Protocol.RECETA_SEARCH_BY_ID:
                        try {
                            String id = is.readUTF();                      // recibe ID del proxy
                            List<Receta> recetas = service.searchRecetaById(id); // devuelve lista
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(recetas);                       // envía lista al proxy
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en RECETA_SEARCH_BY_ID: " + ex.getMessage());
                        }
                        break;
                    case Protocol.RECETA_CREATE:
                        try {
                            Receta r = (Receta) is.readObject();
                            Receta creada = service.createReceta(r); // llama al servicio
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(creada);                  // devuelve al proxy
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en RECETA_CREATE: " + ex.getMessage());
                        }
                        break;
                    case Protocol.RECETA_REMOVE_MEDICAMENTO:
                        try {
                            int recetaId = is.readInt();  // recibimos ID como int
                            int index = is.readInt();     // recibimos índice
                            service.removeMedicamentoFromReceta(recetaId, index);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
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
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                        }
                        break;
//                    case Protocol.USUARIO_DELETE:
//                        try {
//                            service.delete((Usuario) is.readObject());
//                            os.writeInt(Protocol.ERROR_NO_ERROR);
//                        } catch (Exception ex) {
//                            os.writeInt(Protocol.ERROR_ERROR);
//                        }
//                        break;
//                    case Protocol.USUARIO_SEARCH:
//                        try {
//                            List<Usuario> lu = service.search((Usuario) is.readObject());
//                            os.writeInt(Protocol.ERROR_NO_ERROR);
//                            os.writeObject(lu);
//                        } catch (Exception ex) {
//                            os.writeInt(Protocol.ERROR_ERROR);
//                        }
//                        break;
                    case Protocol.USUARIO_FIND_BY_ID:
                        try {
                            String id = is.readUTF(); // Recibimos el ID como String
                            Usuario user = service.findUserById(id); // Buscamos en el servicio
                            if (user != null) {
                                os.writeInt(Protocol.ERROR_NO_ERROR);
                                os.writeObject(user); // Enviamos el usuario al frontend
                            } else {
                                os.writeInt(Protocol.ERROR_ERROR);
                            }
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en USUARIO_FIND_BY_ID: " + ex.getMessage());
                        }
                        break;
                    case Protocol.USUARIO_LOGIN:
                        try {
                            Usuario u = (Usuario) is.readObject();
                            Usuario logged = service.login(u);
                            if (logged != null) {
                                os.writeInt(Protocol.ERROR_NO_ERROR);
                                os.writeObject(logged);
                            } else {
                                os.writeInt(Protocol.ERROR_ERROR);
                            }
                        } catch (Exception ex) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.err.println("Error en USUARIO_LOGIN: " + ex.getMessage());
                        }
                        break;


                    // ===================== DESCONECTAR =====================
                    case Protocol.DISCONNECT:
                        stop();
                        srv.remove(this);
                        break;
                }

                os.flush();

            } catch (IOException e) {
                stop();

            }
        }
    }

}
