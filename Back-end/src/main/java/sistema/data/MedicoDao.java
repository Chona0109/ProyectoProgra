package sistema.data;

import logic.entities.Medico;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDao {
    Database db;
    DepartamentoDao departamentoDao;
    UsuarioDao usuarioDao;

    public MedicoDao(){
        db = Database.instance();
        departamentoDao = new DepartamentoDao();
        usuarioDao = new UsuarioDao();
    }

    public void create(Medico m) throws Exception{
        usuarioDao.create(m);

        String sql = "INSERT INTO Medico (id, nombre, especialidad, departamento) VALUES(?,?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, m.getId());
        stm.setString(2, m.getNombre());
        stm.setString(3, m.getEspecialidad());
        stm.setString(4, m.getDepartamento().getCodigo());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Médico ya existe");
        }
    }

    public Medico read(String id) throws Exception{
        String sql = "SELECT * FROM Medico WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            Medico m = from(rs);
            String depCodigo = rs.getString("departamento");
            m.setDepartamento(departamentoDao.read(depCodigo));
            m.setClave(usuarioDao.read(id).getClave());
            return m;
        } else {
            throw new Exception("Médico no Existe");
        }
    }

    public void update(Medico m) throws Exception{
        usuarioDao.update(m);

        String sql = "UPDATE Medico SET nombre=?, especialidad=?, departamento=? WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, m.getNombre());
        stm.setString(2, m.getEspecialidad());
        stm.setString(3, m.getDepartamento().getCodigo());
        stm.setString(4, m.getId());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Médico no existe");
        }
    }

    public void delete(Medico m) throws Exception{
        try {
            // Primero eliminar registros relacionados
            eliminarRelacionesMedico(m.getId());

            // Luego eliminar médico
            String sql = "DELETE FROM Medico WHERE id=?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, m.getId());
            int count = db.executeUpdate(stm);

            if (count == 0){
                throw new Exception("Médico no existe");
            }

            usuarioDao.delete(m);

        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23")) { // Código de violación de FK
                throw new Exception("No se puede eliminar médico con citas o registros relacionados");
            }
            throw e;
        }
    }

    private void eliminarRelacionesMedico(String medicoId) throws Exception {
        // Eliminar citas del médico
        String deleteCitas = "DELETE FROM Cita WHERE medico_id=?";
        PreparedStatement stm = db.prepareStatement(deleteCitas);
        stm.setString(1, medicoId);
        db.executeUpdate(stm);

        // Eliminar otras relaciones según tu modelo...
    }

    public List<Medico> findAll(){
        List<Medico> medicos = new ArrayList<Medico>();
        try {
            String sql = "SELECT * FROM Medico";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                Medico m = from(rs);
                String depCodigo = rs.getString("departamento");
                m.setDepartamento(departamentoDao.read(depCodigo));
                medicos.add(m);
            }
        } catch (Exception ex) {
            System.err.println("Error en findAll Medico: " + ex.getMessage());
        }
        return medicos;
    }

    public List<Medico> searchByName(String nombre){
        List<Medico> medicos = new ArrayList<Medico>();
        try {
            String sql = "SELECT * FROM Medico WHERE nombre LIKE ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%" + nombre + "%");
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                Medico m = from(rs);
                String depCodigo = rs.getString("departamento");
                m.setDepartamento(departamentoDao.read(depCodigo));
                medicos.add(m);
            }
        } catch (Exception ex) {
            System.err.println("Error en searchByName Medico: " + ex.getMessage());
        }
        return medicos;
    }

    private Medico from(ResultSet rs){
        try {
            Medico m = new Medico();
            m.setId(rs.getString("id"));
            m.setNombre(rs.getString("nombre"));
            m.setEspecialidad(rs.getString("especialidad"));
            return m;
        } catch (SQLException ex) {
            return null;
        }
    }
}