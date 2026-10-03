package veterinaria.persistencia;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import veterinaria.entidades.*;

/*
    Formato en el .csv
    ID,Activo,Nombre,Especialidad
*/

public class persistenciaVeterinario {
    private static Path archivo;
    
    public static void setArchivo(Path archivo) {
        persistenciaVeterinario.archivo = archivo;
    }
    
    // Retorna verdadero si no encuentra un veterinario con el ID, lo cuál nunca debería pasar
    public static boolean actualizarVeterinario(Veterinario veterinario) throws IOException {
        List<String> lineas = Files.readAllLines(archivo);

        ArrayList<String> veterinarioActualizado = new ArrayList<String>();
        veterinarioActualizado.add(String.valueOf(veterinario.getId()));
        veterinarioActualizado.add(String.valueOf(veterinario.getActivo()));
        veterinarioActualizado.add(veterinario.getNombre());
        veterinarioActualizado.add(veterinario.getEspecialidad());
        
        for(int i = 0; i < lineas.size(); i++) {
            String[] datos = lineas.get(i).split(",");
            if(Integer.valueOf(datos[0]) == veterinario.getId()) {
                lineas.set(i, String.join(",", veterinarioActualizado));
                Files.write(archivo, lineas);
                System.out.println("Se actualiza elemento");
                return false;
            }
        }

        agregarVeterinario(veterinario);
        return true;
    }

    public static void agregarVeterinario(Veterinario veterinario) throws IOException {
        List<String> lineas = Files.readAllLines(archivo);

        ArrayList<String> veterinarioNuevo = new ArrayList<String>();
        veterinarioNuevo.add(String.valueOf(veterinario.getId()));
        veterinarioNuevo.add(String.valueOf(veterinario.getActivo()));
        veterinarioNuevo.add(veterinario.getNombre());
        veterinarioNuevo.add(veterinario.getEspecialidad());
        
        lineas.add(String.join(",", veterinarioNuevo));

        Files.write(archivo, lineas);
        System.out.println("Se agrega elemento");
    }

    public static LinkedList<Veterinario> obteniendoVeterinarios() {
        List<String> lineas;

        try {
            lineas = Files.readAllLines(archivo);
        } catch (Exception e) {
            return new LinkedList<Veterinario>();
        }

        LinkedList<Veterinario> veterinarios = new LinkedList<Veterinario>();
        
        for (String linea : lineas) {
            Veterinario nuevoVeterinario = crearVeterinario(linea);
            if(nuevoVeterinario != null)
            {
                veterinarios.add(nuevoVeterinario);
            }
        }

        return veterinarios;
    }

    private static Veterinario crearVeterinario(String strVeterinario) {
        String[] datos = strVeterinario.split(",", -1);

        if(datos.length < 4)
        {
            System.err.println("Formato invalido");
            return null;
        }

        return new Veterinario(Integer.valueOf(datos[0]), Boolean.valueOf(datos[1]), datos[2], datos[3]);
    }
}
