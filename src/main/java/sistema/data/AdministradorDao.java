package sistema.data;

import sistema.logic.entities.Administrador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDao {
    Database db;
    DepartamentoDao departamentoDao;
    UsuarioDao usuarioDao;

    public AdministradorDao(){
        db = Database.instance();
        departamentoDao = new DepartamentoDao();
        usuarioDao = new UsuarioDao();
    }

    public void create(Administrador a) throws Exception{
        usuarioDao.create(a);

        String sql = "INSERT INTO Administrador (id, nombre, departamento) VALUES(?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, a.getId());
        stm.setString(2, a.getNombre());
        stm.setString(3, a.getDepartamento().getCodigo());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Administrador ya existe");
        }
    }

    public Administrador read(String id) throws Exception{
        String sql = "SELECT * FROM Administrador WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            Administrador a = from(rs);
            String depCodigo = rs.getString("departamento");
            a.setDepartamento(departamentoDao.read(depCodigo));
            a.setClave(usuarioDao.read(id).getClave());
            return a;
        } else {
            throw new Exception("Administrador no Existe");
        }
    }
    
    public void update(Administrador a) throws Exception{
        usuarioDao.update(a);

        String sql = "UPDATE Administrador SET nombre=?, departamento=? WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, a.getNombre());
        stm.setString(2, a.getDepartamento().getCodigo());
        stm.setString(3, a.getId());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Administrador no existe");
        }
    }

    public void delete(Administrador a) throws Exception{
        String sql = "DELETE FROM Administrador WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, a.getId());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Administrador no existe");
        }
        usuarioDao.delete(a);
    }
    
    public List<Administrador> findAll(){
        List<Administrador> administradores = new ArrayList<Administrador>();
        try {
            String sql = "SELECT * FROM Administrador";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                Administrador a = from(rs);
                String depCodigo = rs.getString("departamento");
                a.setDepartamento(departamentoDao.read(depCodigo));
                administradores.add(a);
            }
        } catch (Exception ex) {
            System.err.println("Error en findAll Administrador: " + ex.getMessage());
        }
        return administradores;
    }
    
    private Administrador from(ResultSet rs){
        try {
            Administrador a = new Administrador();
            a.setId(rs.getString("id"));
            a.setNombre(rs.getString("nombre"));
            return a;
        } catch (SQLException ex) {
            return null;
        }
    }
}