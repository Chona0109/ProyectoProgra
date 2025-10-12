package sistema.data;

import sistema.logic.entities.Mensaje;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MensajeDao {
    Database db;
    UsuarioDao usuarioDao;

    public MensajeDao(){
        db = Database.instance();
        usuarioDao = new UsuarioDao();
    }

    public void create(Mensaje m) throws Exception{
        String sql = "INSERT INTO Mensaje (remitenteId, destinatarioId, mensaje, leido) VALUES(?,?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, m.getRemitenteId());
        stm.setString(2, m.getDestinatarioId());
        stm.setString(3, m.getMensaje());
        stm.setBoolean(4, m.isLeido());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Error al crear mensaje");
        }
    }

    public Mensaje read(int id) throws Exception{
        String sql = "SELECT * FROM Mensaje WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            return from(rs);
        } else {
            throw new Exception("Mensaje no Existe");
        }
    }
    
    public void marcarComoLeido(int id) throws Exception{
        String sql = "UPDATE Mensaje SET leido=true WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, id);
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Mensaje no existe");
        }
    }

    public void delete(int id) throws Exception{
        String sql = "DELETE FROM Mensaje WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, id);
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Mensaje no existe");
        }
    }
    
    public List<Mensaje> findByDestinatario(String destinatarioId){
        List<Mensaje> mensajes = new ArrayList<Mensaje>();
        try {
            String sql = "SELECT * FROM Mensaje WHERE destinatarioId=? ORDER BY fecha DESC";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, destinatarioId);
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                mensajes.add(from(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error en findByDestinatario Mensaje: " + ex.getMessage());
        }
        return mensajes;
    }

    public List<Mensaje> findNoLeidos(String destinatarioId){
        List<Mensaje> mensajes = new ArrayList<Mensaje>();
        try {
            String sql = "SELECT * FROM Mensaje WHERE destinatarioId=? AND leido=false ORDER BY fecha DESC";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, destinatarioId);
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                mensajes.add(from(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error en findNoLeidos Mensaje: " + ex.getMessage());
        }
        return mensajes;
    }

    public int countNoLeidos(String destinatarioId){
        try {
            String sql = "SELECT COUNT(*) as total FROM Mensaje WHERE destinatarioId=? AND leido=false";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, destinatarioId);
            ResultSet rs = db.executeQuery(stm);
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException ex) {
            System.err.println("Error en countNoLeidos Mensaje: " + ex.getMessage());
        }
        return 0;
    }
    
    private Mensaje from(ResultSet rs){
        try {
            Mensaje m = new Mensaje();
            m.setId(rs.getInt("id"));
            m.setRemitenteId(rs.getString("remitenteId"));
            m.setDestinatarioId(rs.getString("destinatarioId"));
            m.setMensaje(rs.getString("mensaje"));
            Timestamp fecha = rs.getTimestamp("fecha");
            if (fecha != null) {
                m.setFecha(fecha.toLocalDateTime());
            }
            m.setLeido(rs.getBoolean("leido"));
            return m;
        } catch (SQLException ex) {
            return null;
        }
    }
}