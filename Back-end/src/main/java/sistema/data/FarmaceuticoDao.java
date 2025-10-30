package sistema.data;

import logic.entities.Farmaceutico;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FarmaceuticoDao {
    Database db;
    DepartamentoDao departamentoDao;
    UsuarioDao usuarioDao;

    public FarmaceuticoDao(){
        db = Database.instance();
        departamentoDao = new DepartamentoDao();
        usuarioDao = new UsuarioDao();
    }

    public void create(Farmaceutico f) throws Exception{
        usuarioDao.create(f);

        String sql = "INSERT INTO Farmaceutico (id, nombre, departamento) VALUES(?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, f.getId());
        stm.setString(2, f.getNombre());
        stm.setString(3, f.getDepartamento().getCodigo());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Farmacéutico ya existe");
        }
    }

    public Farmaceutico read(String id) throws Exception{
        String sql = "SELECT * FROM Farmaceutico WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            Farmaceutico f = from(rs);
            String depCodigo = rs.getString("departamento");
            f.setDepartamento(departamentoDao.read(depCodigo));
            f.setClave(usuarioDao.read(id).getClave());
            return f;
        } else {
            throw new Exception("Farmacéutico no Existe");
        }
    }

    public void update(Farmaceutico f) throws Exception{
        usuarioDao.update(f);

        String sql = "UPDATE Farmaceutico SET nombre=?, departamento=? WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, f.getNombre());
        stm.setString(2, f.getDepartamento().getCodigo());
        stm.setString(3, f.getId());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Farmacéutico no existe");
        }
    }

    public void delete(Farmaceutico f) throws Exception{
        String sql = "DELETE FROM Farmaceutico WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, f.getId());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Farmacéutico no existe");
        }
        usuarioDao.delete(f);
    }

    public List<Farmaceutico> findAll(){
        List<Farmaceutico> farmaceuticos = new ArrayList<Farmaceutico>();
        try {
            String sql = "SELECT * FROM Farmaceutico";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                Farmaceutico f = from(rs);
                String depCodigo = rs.getString("departamento");
                f.setDepartamento(departamentoDao.read(depCodigo));
                farmaceuticos.add(f);
            }
        } catch (Exception ex) {
            System.err.println("Error en findAll Farmaceutico: " + ex.getMessage());
        }
        return farmaceuticos;
    }

    public List<Farmaceutico> searchByName(String nombre){
        List<Farmaceutico> farmaceuticos = new ArrayList<Farmaceutico>();
        try {
            String sql = "SELECT * FROM Farmaceutico WHERE nombre LIKE ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%" + nombre + "%");
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                Farmaceutico f = from(rs);
                String depCodigo = rs.getString("departamento");
                f.setDepartamento(departamentoDao.read(depCodigo));
                farmaceuticos.add(f);
            }
        } catch (Exception ex) {
            System.err.println("Error en searchByName Farmaceutico: " + ex.getMessage());
        }
        return farmaceuticos;
    }

    private Farmaceutico from(ResultSet rs){
        try {
            Farmaceutico f = new Farmaceutico();
            f.setId(rs.getString("id"));
            f.setNombre(rs.getString("nombre"));
            return f;
        } catch (SQLException ex) {
            return null;
        }
    }
}