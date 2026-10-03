package veterinaria.persistencia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import veterinaria.entidades.*;

public class persistenciaConsulta {
    public static boolean actualizarConsulta(Consulta consulta) throws IOException {
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
        Path archivo = Path.of("datosConsulta.csv");
        List<String> lineas = Files.readAllLines(archivo);

        ArrayList<String> consultaActualizado = new ArrayList<String>();
        consultaActualizado.add(String.valueOf(consulta.getId()));
        consultaActualizado.add(String.valueOf(consulta.getFechaYHora()));
        consultaActualizado.add(consulta.getMotivo());
        consultaActualizado.add(consulta.getDiagnóstico());
        consultaActualizado.add(consulta.getTratamiento());
        consultaActualizado.add(String.valueOf(consulta.getMascota().getId()));
        
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

        agregarConsulta(consulta);
        return true;
    }
}