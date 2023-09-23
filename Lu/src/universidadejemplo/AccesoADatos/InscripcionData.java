package universidadejemplo.AccesoADatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import universidadejemplo.Entidades.Alumno;
import universidadejemplo.Entidades.Inscripcion;
import universidadejemplo.Entidades.Materia;

public class InscripcionData {
    
    private Connection con = null;
    private MateriaData md =new MateriaData();
    private AlumnoData ad = new AlumnoData();
    public InscripcionData() {
        con = Conexion.getConexion();
    }

    public void guardarInscripcion(Inscripcion incs) {

        String sql = "INSERT INTO inscripcion (nota, id_alumno, id_materia ) VALUES (?, ?, ?)";

        try {

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, incs.getNota());
            ps.setInt(2, incs.getAlumno().getId_alumno());
            ps.setInt(3, incs.getMateria().getId_materia());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                incs.setId_inscripto(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "inscripcion registrada");

            }

            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error al acceder a la tabla inscripcion" + ex);
        }

    }
    
    public void actualizarNota (int nota, int id_alumno, int id_materia){ //actualizar las notas a DOUBLE en la db tamb
        String sql = "UPDATE inscripcion SET nota= ? WHERE id_alumno = ? AND id_materia= ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nota);
            ps.setInt(2, id_alumno);
            ps.setInt(3, id_materia);
            
            
            int filas = ps.executeUpdate();
            if(filas>0){
                JOptionPane.showMessageDialog(null, "Nota Actualizada");
                ps.close();
                
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la tabla Inscripcion"+ex.getMessage());
        }
        
    }
    
    public void borrarInscripcionMateriaAlumno (int id_alumno, int id_materia){ //actualizar las notas a DOUBLE en la db tamb
        String sql = "DELETE FROM inscripcion WHERE id_alumno = ? AND id_materia= ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_materia);
            ps.setInt(2, id_alumno);
            
            
            int filas = ps.executeUpdate();
            if(filas>0){
                JOptionPane.showMessageDialog(null, "Inscripcion Borrada");
                ps.close();
                
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la tabla Inscripcion"+ex.getMessage());
        }
        
    }

    public List <Inscripcion> obtenerInscripciones(){
        List <Inscripcion> cursadas = new ArrayList<>();
    
        try{
            String sql = "SELECT * FROM inscripcion";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
            
            Inscripcion insc= new Inscripcion();
            insc.setId_inscripto(rs.getInt("id_inscripto"));
            Alumno alu =ad.buscarAlumno(rs.getInt("id_alumno"));
            Materia mat = md.buscarMateriaPorID(rs.getInt("id_materia"));
            
            insc.setAlumno(alu);
            insc.setMateria(mat);
            insc.setNota(rs.getInt("nota"));
            cursadas.add(insc);
            }
            ps.close();
            rs.close();
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Inscripcion"+ex.getMessage());
        
        }
    return cursadas;
    }
    
    public List<Materia> obtenerMateriasCursadas(int id) {
        List<Materia> materias = new ArrayList<>();

        try {
            String sql = "SELECT m.id_materia, m.nombre, m.anio\n"
                    + "FROM inscripcion AS i\n"
                    + "JOIN materia AS m\n"
                    + "ON i.id_materia = m.id_materia\n"
                    + "JOIN alumno a\n"
                    + "ON i.id_alumno = a.id_alumno\n"
                    + "WHERE nota > 0"; //nota is null pal prox metodo

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Materia materia = new Materia();

                materia.setId_materia(rs.getInt("id_materia"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAnio(rs.getInt("anio"));

                materias.add(materia);
            }

            ps.close();
            rs.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Inscripcion" + ex.getMessage());

        }
        return materias;
        
    }
    
    public List <Inscripcion> obtenerInscripcionesPorAlumno(int id){
        ArrayList <Inscripcion> inscripciones = new ArrayList<>();
    
            String sql = "SELECT * FROM inscripcion WHERE id_alumno = ?";

            try{    
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    
                    while(rs.next()){
                        Inscripcion insc = new Inscripcion();
                        insc.setId_inscripto(rs.getInt("id_inscripto"));

                        Alumno alu =ad.buscarAlumno(rs.getInt("id_alumno"));
                        Materia mat = md.buscarMateriaPorID(rs.getInt("id_materia"));

                        insc.setAlumno(alu);
                        insc.setMateria(mat);

                        insc.setNota(rs.getInt("nota"));
                        inscripciones.add(insc);
                
                    }
                ps.close();
                rs.close();
                
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Inscripcion"+ex.getMessage());
            
            }
        return inscripciones;
    }
    
    public List <Materia> obtenerMateriasNOCursadas(int id_alumno){
       ArrayList <Materia> materias = new ArrayList<>();
    //Esta consulta nos devuelve los datos de todas las materias en el que el alumno no esta inscripto

        String sql = "SELECT * FROM materia WHERE estado = 1 AND id_materia not in "
                        +"(SELECT id_materia FROM inscripcion WHERE id_alumno = ?)";
                    /*(SELECT) obtenemos los datos de las materias (WHERE) que estan activas (AND) y ademas el id de
                    esa materia (NOT IN ) no este en el conjunto de abajo que esta entre parentesis.
                    SUBCONSULTA*/
                
          try{    
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setInt(1, id_alumno);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {

                        Materia materia = new Materia();

                        materia.setId_materia(rs.getInt("id_materia"));
                        materia.setNombre(rs.getString("nombre"));
                        materia.setAnio(rs.getInt("anio"));
                        materias.add(materia);
                    }
                ps.close();
                rs.close();

          }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Inscripcion"+ex.getMessage());                    
           }

    return materias;
    }
    
    public List <Alumno> obtenerAlumnosPorMateria(int id_materia){
       ArrayList <Alumno> alumnosMateria = new ArrayList<>();
        //devolver los datos de los alumnos inscriptos en una determinada materia
        //unimos la tabla inscripcion con la tabla alumno
        String sql = "SELECT a.id_alumno, dni, nombre, apellido, fecha_nacimiento, estado  "
                         +"FROM inscripcion i, alumno a WHERE i.id_alumno = a.id_alumno AND id_materia = ? AND a.estado = 1";
        
               try{    
                    PreparedStatement ps = con.prepareStatement(sql);        
                    ps.setInt(1, id_materia);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                              Alumno alumno = new Alumno();
                              alumno.setId_alumno(rs.getInt("id_alumno"));
                              alumno.setDni(rs.getInt("dni"));
                              alumno.setApellido(rs.getString("apellido"));
                              alumno.setNombre(rs.getString("nombre"));
                              alumno.setFecha_nacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                              alumno.setEstado(rs.getBoolean("estado"));
                              alumnosMateria.add(alumno);
                    }
                    rs.close();
                    ps.close();
                    
          }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Inscripcion"+ex.getMessage());                    
          } 
          return alumnosMateria;
    }
    
    
    
}
