package sistema.data;

import sistema.logic.entities.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    Database db;
    DepartamentoDao departamentoDao;

    public UsuarioDao(){
        db = Database.instance();
        departamentoDao = new DepartamentoDao();
    }

    public void create(Usuario u) throws Exception{
        String sql = "INSERT INTO Usuario (id, nombre, clave, departamento) VALUES(?,?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, u.getId());
        stm.setString(2, u.getNombre());
        stm.setString(3, u.getClave());
        stm.setString(4, u.getDepartamento().getCodigo());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Usuario ya existe");
        }
    }

    public Usuario read(String id) throws Exception{
        String sql = "SELECT * FROM Usuario u WHERE u.id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            Usuario u = from(rs);
            String depCodigo = rs.getString("departamento");
            if (depCodigo != null && !depCodigo.isEmpty()) {
                try {
                    u.setDepartamento(departamentoDao.read(depCodigo));
                } catch (Exception e) {
                    System.err.println("Error cargando departamento: " + e.getMessage());
                }
            }
            return u;
        } else {
            throw new Exception("Usuario no Existe");
        }
    }

    public void update(Usuario u) throws Exception{
        Usuario usuarioActual = read(u.getId());

        if (u.getDepartamento() == null) {
            u.setDepartamento(usuarioActual.getDepartamento());
        }

        if (u.getNombre() == null || u.getNombre().isEmpty()) {
            u.setNombre(usuarioActual.getNombre());
        }

        if (u.getClave() == null || u.getClave().isEmpty()) {
            u.setClave(usuarioActual.getClave());
        }

        String sql = "UPDATE Usuario SET nombre=?, clave=?, departamento=? WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, u.getNombre());
        stm.setString(2, u.getClave());
        stm.setString(3, u.getDepartamento().getCodigo());
        stm.setString(4, u.getId());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Usuario no existe");
        }
    }

    public void delete(Usuario u) throws Exception{
        String sql = "DELETE FROM Usuario WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, u.getId());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Usuario no existe");
        }
    }

    public List<Usuario> findAll(){
        List<Usuario> usuarios = new ArrayList<Usuario>();
        try {
            String sql = "SELECT * FROM Usuario";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                Usuario u = from(rs);
                String depCodigo = rs.getString("departamento");
                u.setDepartamento(departamentoDao.read(depCodigo));
                usuarios.add(u);
            }
        } catch (Exception ex) {
            System.err.println("Error en findAll Usuario: " + ex.getMessage());
        }
        return usuarios;
    }

    private Usuario from(ResultSet rs){
        try {
            Usuario u = new Usuario();
            u.setId(rs.getString("id"));
            u.setNombre(rs.getString("nombre"));
            u.setClave(rs.getString("clave"));
            return u;
        } catch (SQLException ex) {
            return null;
        }
    }
}