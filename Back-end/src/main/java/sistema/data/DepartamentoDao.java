package sistema.data;

import sistema.logic.entities.Departamento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDao {
    Database db;

    public DepartamentoDao(){
        db = Database.instance();
    }

    public Departamento read(String codigo) throws Exception{
        String sql = "SELECT * FROM Departamento WHERE codigo=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, codigo);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            return from(rs);
        } else {
            throw new Exception("Departamento no Existe");
        }
    }

    public List<Departamento> findAll(){
        List<Departamento> ds = new ArrayList<Departamento>();
        try {
            String sql = "SELECT * FROM Departamento";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                ds.add(from(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error en findAll Departamento: " + ex.getMessage());
        }
        return ds;
    }
    
    public Departamento from(ResultSet rs){
        try {
            Departamento d = new Departamento();
            d.setCodigo(rs.getString("codigo"));
            d.setNombre(rs.getString("nombre"));
            return d;
        } catch (SQLException ex) {
            return null;
        }
    }
}