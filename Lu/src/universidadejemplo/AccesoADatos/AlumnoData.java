package universidadejemplo.AccesoADatos;

import universidadejemplo.Entidades.Alumno;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AlumnoData {

    private Connection con = null; // genera conexion y pone null para que no se inicie.

    public AlumnoData() { //un metodo para conectar a la base de datos
        con = Conexion.getConexion();
    }

    public void guardarAlumno(Alumno alumno) { //este metodo genera un objeto alumno llamado alumno

        String sql = "INSERT INTO alumno (dni, apellido, nombre, fecha_nacimiento, estado) VALUES (?, ?, ?, ?, ?)"; //se crea declaracion de consulta a la bd
        try {
                      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //esto genera una clave de entrada/ingreso de informacion y la instancia
                      ps.setInt(1, alumno.getDni()); // carga en el el primer value de la consulta a la db
                      ps.setString(2, alumno.getApellido()); // carga en el el 2do value de la consulta a la db
                      ps.setString(3, alumno.getNombre()); // carga en el el 3er value de la consulta a la db
                      ps.setDate(4, Date.valueOf(alumno.getFecha_nacimiento()));//localDate a Date // carga en el el 4to value de la consulta a la db
                      ps.setBoolean(5, alumno.isEstado()); // if reducido // carga en el el 5to value de la consulta a la db
                      ps.executeUpdate(); //manda el statement a la bd
                      ResultSet rs = ps.getGeneratedKeys(); //aca guarda la clave de ingreso y lo mete en una variable
                      
           if (rs.next()) { //iteracion de datos
                      alumno.setId_alumno(rs.getInt(1)); //cambia el id del alumno por el numero de la clave de ingreso 
                      JOptionPane.showMessageDialog(null, "Alumno añadido con exito."); //mensaje de salida en un optionpane
            }           
           ps.close(); // cierra el statement, para que no quede ninguna conexion abierta y luego genere errores.           
           rs.close();// cierra el rs.
           
        } catch (SQLException ex) { //catch de la exepcion 
           JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno" + ex.getMessage()); //exepcion en si

        }

    }
    
    public void modificarAlumno(Alumno alumno) {

        String sql = "UPDATE alumno SET dni = ? , apellido = ?, nombre = ?, fecha_nacimiento = ? WHERE id_alumno = ?";
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFecha_nacimiento()));
            ps.setInt(5, alumno.getId_alumno());
            int exito = ps.executeUpdate();

            if (exito == 1) {
                JOptionPane.showMessageDialog(null, "Modificado Exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "El alumno no existe");
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno " + ex.getMessage());
        }

    }
    
    public void eliminarAlumno(int id) {

        try {
            String sql = "UPDATE alumno SET estado = 0 WHERE id_alumno = ? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int fila = ps.executeUpdate();

            if (fila == 1) {
                JOptionPane.showMessageDialog(null, " Se eliminó el alumno.");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al acceder a la tabla Alumno");
        }
    }
    
    
    public Alumno buscarAlumno(int id) { 
            Alumno alumno = null;
            String sql = "SELECT dni, apellido, nombre, fecha_nacimiento FROM alumno WHERE id_alumno = ? AND estado = 1";
            PreparedStatement ps = null;
            try {
                ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        alumno = new Alumno();
                        alumno.setId_alumno(id);
                        alumno.setDni(rs.getInt("dni"));
                        alumno.setApellido(rs.getString("apellido"));
                        alumno.setNombre(rs.getString("nombre"));
                        alumno.setFecha_nacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                        alumno.setEstado(true);

            } else {
                JOptionPane.showMessageDialog(null, "No existe el alumno");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno " + ex.getMessage());

        }

        return alumno;
    }

    public Alumno buscarAlumnoPorDni(int dni) {
        Alumno alumno = null;
        String sql = "SELECT id_alumno, dni, apellido, nombre, fecha_nacimiento FROM alumno WHERE dni=? AND estado = 1";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                alumno = new Alumno();
                alumno.setId_alumno(rs.getInt("id_alumno"));
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFecha_nacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                alumno.setEstado(true);

            } else {
                JOptionPane.showMessageDialog(null, "No existe el alumno");

            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno " + ex.getMessage());
        }

        return alumno;
    }

    public List<Alumno> listarAlumnos() {

        List<Alumno> alumnos = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM alumno ";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Alumno alumno = new Alumno();

                alumno.setId_alumno(rs.getInt("id_alumno"));
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFecha_nacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                alumno.setEstado(rs.getBoolean("estado"));
                alumnos.add(alumno);
            }
            ps.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, " Error al acceder a la tabla Alumno " + ex.getMessage());
        }
        return alumnos;
    }

}
