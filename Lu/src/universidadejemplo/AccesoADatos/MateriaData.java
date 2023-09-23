
package universidadejemplo.AccesoADatos;
    import universidadejemplo.Entidades.Materia;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.util.ArrayList;
    import java.util.List;
    import javax.swing.JOptionPane;



public class MateriaData {
    
    private Connection con = null; // genera conexion y pone null para que no se inicie.
    
    public MateriaData() { //un metodo para conectar a la base de datos
        con = Conexion.getConexion();
    }
    
    public void guardarMateria(Materia materia){
        String sql = "INSERT INTO materia (nombre, anio, estado) VALUES(?,?,?)";
        try {
        PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString( 1, materia.getNombre());
            ps.setInt( 2, materia.getAnio() );
            ps.setBoolean(3, true); //modificar por isEstado
            ps.executeUpdate(); //manda datos cargados a la base de datos
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next() ){
                materia.setId_materia(rs.getInt("id_materia"));
                JOptionPane.showMessageDialog(null, "alumno añadido con exito");
            }
                ps.close();
            
        
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null,"error al acceder a la tabla materia" + ex.getMessage());
        }
         
}
    
    public Materia buscarMateriaPorID(int id){
        
        Materia materia = null;
        
        String sql = "SELECT * FROM materia WHERE id_materia = ?";
        PreparedStatement ps = null;  
        
        try{
        ps = con.prepareStatement(sql);
        ps.setInt(1,id);  
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            materia = new Materia();
            materia.setId_materia(rs.getInt("id_materia"));
            materia.setNombre (rs.getString("nombre"));
            materia.setAnio(rs.getInt("anio"));
            materia.setEstado(rs.getBoolean("estado"));
        }else{
            JOptionPane.showMessageDialog(null, "No existe la Materia ingresada. ");
        }
        
            rs.close();
            ps.close();
        
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Materia " + ex.getMessage());

        }
        return materia; 
    }
    
    public void modificarMateria(Materia materia){
        
        String sql = "UPDATE materia SET nombre = ?, anio = ? WHERE id_materia = ?";
        PreparedStatement ps= null;
        
        try {
            ps= con.prepareStatement(sql);
            ps.setString(1,materia.getNombre());
            ps.setInt(2,materia.getAnio());
            ps.setInt(3,materia.getId_materia());
            
            int exito = ps.executeUpdate();
            if (exito == 1) {
                JOptionPane.showMessageDialog(null, " Materia modificado exitosamente. " );
            }else{
                JOptionPane.showMessageDialog(null, " Materia no existe. " );
            }
    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, " Error al acceder a la tabla Materia. "+ex.getMessage() );   
        } 
    } 
    
    public void eliminarMateria(int id){

        try{
        
            String sql  = "UPDATE materia SET estado = 0 WHERE id_materia= ?"; //esto va a mandar a la base de datos
                                                                                                                                     //en set estado 0 es donde se manda la orden para borrar.
                                                                                                                                     
            PreparedStatement ps = con.prepareStatement(sql); //con = connection - esta asignando. 
            ps.setInt(1, id); 
            int fila = ps.executeUpdate(); 
            if(fila ==1){
                JOptionPane.showMessageDialog(null, "se elimino la materia");
                        
            }
            ps.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "error al acceder a la tabla materia");
            
        }
    }
    
    public List<Materia> listarMaterias(){
        
        List <Materia> materias = new ArrayList <> ();
        try{
            String sql = "SELECT * FROM materia ";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
                while (rs.next()) {
                  Materia materia = new Materia(); 
                   materia.setId_materia(rs.getInt("id_materia"));
                   materia.setAnio(rs.getInt("anio"));
                   materia.setNombre(rs.getString("nombre"));
                   materia.setEstado(rs.getBoolean("estado")); 
                   
                   materias.add(materia);
                  
                   }
                   ps.close();
                   rs.close();
        }
        catch(SQLException ex){
                  JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Materia. "+ex.getMessage());
        }
        return materias;
    }

}
