package veterinaria.persistencia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.time.LocalDateTime;

import veterinaria.entidades.*;

/*
    Formato en el .csv
    ID,FechaHora,Motivo,Diagnostico,Tratamiento,IDMascota,IDConsulta
*/

public class persistenciaConsulta {
    private static Path archivo;

    public static void setArchivo(Path archivo) {
        persistenciaConsulta.archivo = archivo;
    }

    // Retorna verdadero si no encuentra un Consulta con el ID, lo cuál nunca debería pasar
    public static boolean actualizarConsulta(Consulta consulta) throws IOException {
        List<String> lineas = Files.readAllLines(archivo);

        ArrayList<String> consultaActualizado = new ArrayList<String>();
        consultaActualizado.add(String.valueOf(consulta.getId()));
        consultaActualizado.add(String.valueOf(consulta.getFechaYHora()));
        consultaActualizado.add(consulta.getMotivo());
        consultaActualizado.add(consulta.getDiagnóstico());
        consultaActualizado.add(consulta.getTratamiento());
        consultaActualizado.add(String.valueOf(consulta.getMascota().getId()));
        consultaActualizado.add(String.valueOf(consulta.getConsulta().getId()));
        
        for(int i = 0; i < lineas.size(); i++) {
            String[] datos = lineas.get(i).split(",");
            if(Integer.valueOf(datos[0]) == consulta.getId()) {
                lineas.set(i, String.join(",", consultaActualizado));
                Files.write(archivo, lineas);
                System.out.println("Se actualiza elemento");
                return false;
            }
        }

        agregarConsulta(consulta);
        return true;
    }

    public static void agregarConsulta(Consulta consulta) throws IOException {
        List<String> lineas = Files.readAllLines(archivo);

        ArrayList<String> consultaActualizado = new ArrayList<String>();
        consultaActualizado.add(String.valueOf(consulta.getId()));
        consultaActualizado.add(String.valueOf(consulta.getFechaYHora()));
        consultaActualizado.add(consulta.getMotivo());
        consultaActualizado.add(consulta.getDiagnóstico());
        consultaActualizado.add(consulta.getTratamiento());
        consultaActualizado.add(String.valueOf(consulta.getMascota().getId()));
        consultaActualizado.add(String.valueOf(consulta.getConsulta().getId()));
        
        lineas.add(String.join(",", consultaActualizado));

        Files.write(archivo, lineas);
        System.out.println("Se agrega elemento");
    }

    public static boolean eliminarConsulta(Consulta consulta) throws IOException {
        Path archivo = Path.of("datosConsulta.csv");
        List<String> lineas = Files.readAllLines(archivo);

        ArrayList<String> consultaActualizado = new ArrayList<String>();
        consultaActualizado.add(String.valueOf(consulta.getId()));
        consultaActualizado.add(String.valueOf(consulta.getFechaYHora()));
        consultaActualizado.add(consulta.getMotivo());
        consultaActualizado.add(consulta.getDiagnóstico());
        consultaActualizado.add(consulta.getTratamiento());
        consultaActualizado.add(String.valueOf(consulta.getMascota().getId()));
        
        for(int i = 0; i < lineas.size(); i++) {
            String[] datos = lineas.get(i).split(",");
            if(Integer.valueOf(datos[0]) == consulta.getId()) {
                lineas.remove(i);
                Files.write(archivo, lineas);
                System.out.println("Se elimino elemento");
                return false;
            }
        }
        return true;
    }

    public static LinkedList<Consulta> obtenerConsultas(ArrayList<Integer> mascotasID, ArrayList<Integer> veterinariosID) {
        List<String> lineas;

        try {
            lineas = Files.readAllLines(archivo);
        } catch (Exception e) {
            return new LinkedList<Consulta>();
        }

        LinkedList<Consulta> consultas = new LinkedList<Consulta>();
        
        for (String linea : lineas) {
            Consulta nuevaConsulta = crearConsulta(linea);
            if(nuevaConsulta != null)
            {
                consultas.add(nuevaConsulta);
                String[] datos = linea.split(",", -1);
                mascotasID.add(Integer.valueOf(datos[5]));
                veterinariosID.add(Integer.valueOf(datos[6]));
            }
        }

        return consultas;
    }

    private static Consulta crearConsulta(String strConsulta) {
        String[] datos = strConsulta.split(",", -1);

        if(datos.length < 7)
        {
            System.err.println("Formato invalido");
            return null;
        }

        return new Consulta(Integer.valueOf(datos[0]), LocalDateTime.parse(datos[1]), datos[2], datos[3], datos[4], null);
    }
}