package sistema.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class RecetaDao {
    Database db;
    MedicoDao medicoDao;
    PacienteDao pacienteDao;
    MedicamentoDao medicamentoDao;

    public RecetaDao(){
        db = Database.instance();
        medicoDao = new MedicoDao();
        pacienteDao = new PacienteDao();
        medicamentoDao = new MedicamentoDao();
    }

    public void create(Receta r) throws Exception {
        String sql = "INSERT INTO Receta (medicoId, pacienteId, fechaConfeccion, fechaRetiro, estado) VALUES(?,?,?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);

        stm.setString(1, r.getMedico().getId());
        stm.setString(2, r.getPaciente().getId());
        stm.setDate(3, r.getFechaConfeccion() != null ? Date.valueOf(r.getFechaConfeccion()) : null);
        stm.setDate(4, r.getFechaRetiro() != null ? Date.valueOf(r.getFechaRetiro()) : null);
        stm.setString(5, r.getEstado());

        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Error al crear receta");
        }

        String sqlLastId = "SELECT LAST_INSERT_ID()";
        PreparedStatement stmLast = db.prepareStatement(sqlLastId);
        ResultSet rs = db.executeQuery(stmLast);
        if (rs.next()) {
            r.setId(rs.getInt(1));
        } else {
            throw new Exception("Error: no se pudo obtener el ID generado");
        }

        for (MedicamentoDetalle detalle : r.getMedicamentos()) {
            String sqlDetalle = "INSERT INTO MedicamentoDetalle (recetaId, medicamentoCodigo, cantidad, indicaciones, dias) VALUES(?,?,?,?,?)";
            PreparedStatement stmDetalle = db.prepareStatement(sqlDetalle);
            stmDetalle.setInt(1, r.getId());
            stmDetalle.setString(2, detalle.getMedicamento().getCodigo());
            stmDetalle.setInt(3, detalle.getCantidad());
            stmDetalle.setString(4, detalle.getIndicaciones());
            stmDetalle.setInt(5, detalle.getDias());
            db.executeUpdate(stmDetalle);
        }

    }


    public Receta read(int id) throws Exception{
        String sql = "SELECT * FROM Receta WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            Receta r = from(rs);

            String medicoId = rs.getString("medicoId");
            r.setMedico(medicoDao.read(medicoId));

            String pacienteId = rs.getString("pacienteId");
            r.setPaciente(pacienteDao.read(pacienteId));

            r.setMedicamentos(getMedicamentosDeReceta(id));

            return r;
        } else {
            throw new Exception("Receta no Existe");
        }
    }

    public void update(Receta r) throws Exception{
        String sql = "UPDATE Receta SET medicoId=?, pacienteId=?, fechaConfeccion=?, fechaRetiro=?, estado=? WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, r.getMedico().getId());
        stm.setString(2, r.getPaciente().getId());
        stm.setDate(3, r.getFechaConfeccion() != null ? Date.valueOf(r.getFechaConfeccion()) : null);
        stm.setDate(4, r.getFechaRetiro() != null ? Date.valueOf(r.getFechaRetiro()) : null);
        stm.setString(5, r.getEstado());
        stm.setInt(6, r.getId());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Receta no existe");
        }

        String sqlDeleteDetalle = "DELETE FROM MedicamentoDetalle WHERE recetaId=?";
        PreparedStatement stmDelete = db.prepareStatement(sqlDeleteDetalle);
        stmDelete.setInt(1, r.getId());
        db.executeUpdate(stmDelete);

        for (MedicamentoDetalle detalle : r.getMedicamentos()) {
            String sqlDetalle = "INSERT INTO MedicamentoDetalle (recetaId, medicamentoCodigo, cantidad, indicaciones, dias) VALUES(?,?,?,?,?)";
            PreparedStatement stmDetalle = db.prepareStatement(sqlDetalle);
            stmDetalle.setInt(1, r.getId());
            stmDetalle.setString(2, detalle.getMedicamento().getCodigo());
            stmDetalle.setInt(3, detalle.getCantidad());
            stmDetalle.setString(4, detalle.getIndicaciones());
            stmDetalle.setInt(5, detalle.getDias());
            db.executeUpdate(stmDetalle);
        }
    }

    public void delete(Receta r) throws Exception{
        String sql = "DELETE FROM Receta WHERE id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, r.getId());
        int count = db.executeUpdate(stm);
        if (count == 0){
            throw new Exception("Receta no existe");
        }
    }

    public List<Receta> findAll(){
        List<Receta> recetas = new ArrayList<Receta>();
        try {
            String sql = "SELECT * FROM Receta";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                Receta r = from(rs);

                String medicoId = rs.getString("medicoId");
                r.setMedico(medicoDao.read(medicoId));

                String pacienteId = rs.getString("pacienteId");
                r.setPaciente(pacienteDao.read(pacienteId));

                r.setMedicamentos(getMedicamentosDeReceta(r.getId()));

                recetas.add(r);
            }
        } catch (Exception ex) {
            System.err.println("Error en findAll Receta: " + ex.getMessage());
        }
        return recetas;
    }

    public List<Receta> searchById(String id){
        List<Receta> recetas = new ArrayList<Receta>();
        try {
            String sql = "SELECT * FROM Receta WHERE id LIKE ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%" + id + "%");
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                Receta r = from(rs);

                String medicoId = rs.getString("medicoId");
                r.setMedico(medicoDao.read(medicoId));

                String pacienteId = rs.getString("pacienteId");
                r.setPaciente(pacienteDao.read(pacienteId));

                r.setMedicamentos(getMedicamentosDeReceta(r.getId()));

                recetas.add(r);
            }
        } catch (Exception ex) {
            System.err.println("Error en searchById Receta: " + ex.getMessage());
        }
        return recetas;
    }

    public List<Receta> searchByPacienteId(String pacienteId){
        List<Receta> recetas = new ArrayList<Receta>();
        try {
            String sql = "SELECT * FROM Receta WHERE pacienteId LIKE ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%" + pacienteId + "%");
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                Receta r = from(rs);

                String medicoId = rs.getString("medicoId");
                r.setMedico(medicoDao.read(medicoId));

                String pid = rs.getString("pacienteId");
                r.setPaciente(pacienteDao.read(pid));

                r.setMedicamentos(getMedicamentosDeReceta(r.getId()));

                recetas.add(r);
            }
        } catch (Exception ex) {
            System.err.println("Error en searchByPacienteId Receta: " + ex.getMessage());
        }
        return recetas;
    }

    private List<MedicamentoDetalle> getMedicamentosDeReceta(int recetaId) throws Exception {
        List<MedicamentoDetalle> detalles = new ArrayList<MedicamentoDetalle>();
        String sql = "SELECT * FROM MedicamentoDetalle WHERE recetaId=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, recetaId);
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            MedicamentoDetalle detalle = new MedicamentoDetalle();
            String medicamentoCodigo = rs.getString("medicamentoCodigo");
            detalle.setMedicamento(medicamentoDao.read(medicamentoCodigo));
            detalle.setCantidad(rs.getInt("cantidad"));
            detalle.setIndicaciones(rs.getString("indicaciones"));
            detalle.setDias(rs.getInt("dias"));
            detalles.add(detalle);
        }
        return detalles;
    }

    private Receta from(ResultSet rs){
        try {
            Receta r = new Receta();
            r.setId(rs.getInt("id"));
            Date fechaConf = rs.getDate("fechaConfeccion");
            if (fechaConf != null) {
                r.setFechaConfeccion(fechaConf.toLocalDate());
            }
            Date fechaRet = rs.getDate("fechaRetiro");
            if (fechaRet != null) {
                r.setFechaRetiro(fechaRet.toLocalDate());
            }
            r.setEstado(rs.getString("estado"));
            return r;
        } catch (SQLException ex) {
            return null;
        }
    }
}