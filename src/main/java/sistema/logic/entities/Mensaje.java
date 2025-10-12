package sistema.logic.entities;

import java.time.LocalDateTime;

public class Mensaje {
    private int id;
    private String remitenteId;
    private String destinatarioId;
    private String mensaje;
    private LocalDateTime fecha;
    private boolean leido;

    public Mensaje() {
        this.fecha = LocalDateTime.now();
        this.leido = false;
    }

    public Mensaje(String remitenteId, String destinatarioId, String mensaje) {
        this();
        this.remitenteId = remitenteId;
        this.destinatarioId = destinatarioId;
        this.mensaje = mensaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemitenteId() {
        return remitenteId;
    }

    public void setRemitenteId(String remitenteId) {
        this.remitenteId = remitenteId;
    }

    public String getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(String destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }
}