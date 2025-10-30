package sistema.data;

import logic.entities.Medicamento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDao {
    Database db;

    public MedicamentoDao(){
        db = Database.instance();
    }

    public void create(Medicamento m) throws Exception{
        String sql = "INSERT INTO Medicamento (codigo, nombre, presentacion) VALUES(?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, m.getCodigo());
        stm.setString(2, m.getNombre());
        stm.setString(3, m.getPresentacion());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Medicamento ya existe");
        }
    }

    public Medicamento read(String codigo) throws Exception{
        String sql = "SELECT * FROM Medicamento WHERE codigo=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, codigo);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            return from(rs);
        } else {
            throw new Exception("Medicamento no Existe");
        }
    }
    
    public void update(Medicamento m) throws Exception{
        String sql = "UPDATE Medicamento SET nombre=?, presentacion=? WHERE codigo=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, m.getNombre());
        stm.setString(2, m.getPresentacion());
        stm.setString(3, m.getCodigo());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Medicamento no existe");
        }
    }

    public void delete(Medicamento m) throws Exception{
        String sql = "DELETE FROM Medicamento WHERE codigo=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, m.getCodigo());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Medicamento no existe");
        }
    }
    
    public List<Medicamento> findAll(){
        List<Medicamento> medicamentos = new ArrayList<Medicamento>();
        try {
            String sql = "SELECT * FROM Medicamento";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                medicamentos.add(from(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error en findAll Medicamento: " + ex.getMessage());
        }
        return medicamentos;
    }

    public List<Medicamento> searchByName(String nombre){
        List<Medicamento> medicamentos = new ArrayList<Medicamento>();
        try {
            String sql = "SELECT * FROM Medicamento WHERE nombre LIKE ? ORDER BY nombre";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%" + nombre + "%");
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                medicamentos.add(from(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error en searchByName Medicamento: " + ex.getMessage());
        }
        return medicamentos;
    }

    public List<Medicamento> searchByCodigo(String codigo){
        List<Medicamento> medicamentos = new ArrayList<Medicamento>();
        try {
            String sql = "SELECT * FROM Medicamento WHERE codigo LIKE ? ORDER BY codigo";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%" + codigo + "%");
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                medicamentos.add(from(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error en searchByCodigo Medicamento: " + ex.getMessage());
        }
        return medicamentos;
    }
    
    private Medicamento from(ResultSet rs){
        try {
            Medicamento m = new Medicamento();
            m.setCodigo(rs.getString("codigo"));
            m.setNombre(rs.getString("nombre"));
            m.setPresentacion(rs.getString("presentacion"));
            return m;
        } catch (SQLException ex) {
            return null;
        }
    }
}