package sistema.data;

import sistema.logic.entities.Paciente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PacienteDao {
    Database db;

    public PacienteDao(){
        db = Database.instance();
    }

    public void create(Paciente p) throws Exception{
        String sql = "INSERT INTO Paciente (id, nombre, fechaNacimiento, telefono) VALUES(?,?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, p.getId());
        stm.setString(2, p.getNombre());
        stm.setDate(3, p.getFechaNacimiento() != null ? Date.valueOf(p.getFechaNacimiento()) : null);
        stm.setString(4, p.getTelefono());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Paciente ya existe");
        }
    }

    public Paciente read(String id) throws Exception{
        String sql = "SELECT * FROM Paciente WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            return from(rs);
        } else {
            throw new Exception("Paciente no Existe");
        }
    }
    
    public void update(Paciente p) throws Exception{
        String sql = "UPDATE Paciente SET nombre=?, fechaNacimiento=?, telefono=? WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, p.getNombre());
        stm.setDate(2, p.getFechaNacimiento() != null ? Date.valueOf(p.getFechaNacimiento()) : null);
        stm.setString(3, p.getTelefono());
        stm.setString(4, p.getId());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Paciente no existe");
        }
    }

    public void delete(Paciente p) throws Exception{
        String sql = "DELETE FROM Paciente WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, p.getId());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Paciente no existe");
        }
    }
    
    public List<Paciente> findAll(){
        List<Paciente> pacientes = new ArrayList<Paciente>();
        try {
            String sql = "SELECT * FROM Paciente";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                pacientes.add(from(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error en findAll Paciente: " + ex.getMessage());
        }
        return pacientes;
    }

    public List<Paciente> searchByName(String nombre){
        List<Paciente> pacientes = new ArrayList<Paciente>();
        try {
            String sql = "SELECT * FROM Paciente WHERE nombre LIKE ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%" + nombre + "%");
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                pacientes.add(from(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error en searchByName Paciente: " + ex.getMessage());
        }
        return pacientes;
    }

    public List<Paciente> searchById(String id){
        List<Paciente> pacientes = new ArrayList<Paciente>();
        try {
            String sql = "SELECT * FROM Paciente WHERE id LIKE ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%" + id + "%");
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                pacientes.add(from(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error en searchById Paciente: " + ex.getMessage());
        }
        return pacientes;
    }
    
    private Paciente from(ResultSet rs){
        try {
            Paciente p = new Paciente();
            p.setId(rs.getString("id"));
            p.setNombre(rs.getString("nombre"));
            Date fecha = rs.getDate("fechaNacimiento");
            if (fecha != null) {
                p.setFechaNacimiento(fecha.toLocalDate());
            }
            p.setTelefono(rs.getString("telefono"));
            return p;
        } catch (SQLException ex) {
            return null;
        }
    }
}