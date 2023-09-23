package universidadejemplo.AccesoADatos;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import universidadejemplo.Entidades.Alumno;
import universidadejemplo.Entidades.Inscripcion;
import universidadejemplo.Entidades.Materia;

import universidadejemplo.Vistas.disenioULPApp;

public class Main {

    public static void main(String[] args) {
        //se agrego la habilidad de ejecutar la aplicacion desde el main
        
        disenioULPApp miapp = new disenioULPApp();
        miapp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        miapp.setLocationRelativeTo(null);
        miapp.setVisible(true);
        
        //Se Guardar alumno. 

        //        Alumno juan = new Alumno(12312312, "Luna", "Juan ", LocalDate.of(1996, 6, 13),true);
        //        AlumnoData alu = new AlumnoData();
        //        alu.guardarAlumno(juan);

        //Se modifico alumno. 
        //         Alumno juan = new Alumno(1,12312312, "Luna", "Rigoberto ", LocalDate.of(1996, 6, 13),true);
        //        AlumnoData alu = new AlumnoData();
        //        alu.guardarAlumno(juan);  
        //        alu.modificarAlumno(juan);

        //Se Elimino alumno. 
        //         alu.eliminarAlumno(1); 

 //buscaralumno por id
        AlumnoData alu = new AlumnoData();
        Alumno alumnoEncontrado = alu.buscarAlumno(1);

        if (alumnoEncontrado != null) {
            System.out.println("Alumno encontrado:");
            System.out.println("ID: " + alumnoEncontrado.getId_alumno());
            System.out.println("DNI: " + alumnoEncontrado.getDni());
            System.out.println("Apellido: " + alumnoEncontrado.getApellido());
            System.out.println("Nombre: " + alumnoEncontrado.getNombre());
            System.out.println("Fecha de Nacimiento: " + alumnoEncontrado.getFecha_nacimiento());
            System.out.println("Estado: " + alumnoEncontrado.isEstado());
        } else {
            System.out.println("No se encontró el alumno.");
        } 

        
        //Se prueba guardar inscripcion 
        //          AlumnoData ad = new AlumnoData();
        //          MateriaData md = new MateriaData();
        //          InscripcionData id = new InscripcionData();

        //   Alumno rigoberto = ad.buscarAlumno(1);
        //   int rigo = rigoberto.getId_alumno();
        //   
        //   Materia mate = md.buscarMateriaPorID(1);
        //   int idn = mate.getId_materia();
        //   Inscripcion isc = new Inscripcion(10,rigo, idn);
        //   
        //   
        //            id.guardarInscripcion(isc);
        //            for (Inscripcion inscripcion : id.obtenerInscripciones()) {
        //            
        //            System.out.println(" Id "+inscripcion.getId_inscripto());
        //            System.out.println(" Nombre "+inscripcion.getAlumno().getApellido());
        //            System.out.println(" Materia "+inscripcion.getMateria().getNombre());   
        //        }
                //id.actualizarNota(8, 1, 1);

        //    id.borrarInscripcionMateriaAlumno(1, 1);
        //          for(Materia materia:id.obtenerMateriasNOCursadas(22)){
        //              
        //                    System.out.println("nombre"+ materia.getNombre());        
        //          }



        ////Probamos obtenerMateriasNOCursadas
        //
        //          for(Materia materia:id.obtenerMateriasNOCursadas(1)){
        //                    System.out.println(" Nombre "+ materia.getNombre());
        //          }

        ////Probamos obtenerAlumnosPorMateria
        //          
        //          for(Alumno alum : id.obtenerAlumnosPorMateria(1)  ){
        //                    System.out.println(" Id Alumno "+ alum.getId_alumno());
        //                    System.out.println("Dni "+alum.getDni());
        //                    System.out.println("Nombre "+alum.getNombre());
        //                    System.out.println("Apellido "+alum.getApellido());
        //                    System.out.println("Fecha de Nacimiento "+alum.getFecha_nacimiento());
        //                    System.out.println("Estado "+alum.isEstado());
        //                    
        //                    
        //                    
        //          }

        ////Probamos obtenerInscripcionesPorAlumno.
        //
        //           for (Inscripcion inscripcion : id.obtenerInscripcionesPorAlumno(2)) {
        //
        //                     System.out.println("Nombre del alumno: " + inscripcion.getAlumno().getNombre());
        //
        //         }
 
    }
    }

