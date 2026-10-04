package veterinaria.persistencia;

import java.nio.file.*;
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

    private static Veterinario veterinarioStringAObjecto(String strVeterinario) {
        String[] datos = strVeterinario.split(",", -1);

        if(datos.length < 4) {
            System.err.println("Formato invalido");
            return null;
        }

        return new Veterinario(Integer.valueOf(datos[0]), Boolean.valueOf(datos[1]), datos[2], datos[3]);
    }

    private static String veterinarioObjetoAString(Veterinario veterinario) {
        LinkedList<String> camposDueño = new LinkedList<String>();
        camposDueño.add(String.valueOf(veterinario.getId()));
        camposDueño.add(String.valueOf(veterinario.getActivo()));
        camposDueño.add(veterinario.getNombre());
        camposDueño.add(veterinario.getEspecialidad());

        return String.join(",", camposDueño);
    }
    
    // Retorna verdadero si no encuentra un veterinario con el ID, lo cuál nunca debería pasar
    public static boolean actualizarVeterinario(Veterinario veterinario) {
        List<String> lineas;

        try {
            lineas = Files.readAllLines(archivo);
        } catch (Exception e) {
            return true;
        }

        String strVeterinarioActualizado = veterinarioObjetoAString(veterinario);
        
        for(int i = 0; i < lineas.size(); i++) {
            String[] datos = lineas.get(i).split(",");
            if(Integer.valueOf(datos[0]) == veterinario.getId()) {
                lineas.set(i, strVeterinarioActualizado);

                try {
                    Files.write(archivo, lineas);
                } catch (Exception e) {
                    return true;
                }
                
                return false;
            }
        }

        agregarVeterinario(veterinario);
        return true;
    }

    public static boolean agregarVeterinario(Veterinario veterinario) {
        List<String> lineas;

        try {
            lineas = Files.readAllLines(archivo);
        } catch (Exception e) {
            return true;
        }
        
        lineas.add(veterinarioObjetoAString(veterinario));

        try {
            Files.write(archivo, lineas);
        } catch (Exception e) {
            return true;
        }
        
        return false;
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
            Veterinario nuevoVeterinario = veterinarioStringAObjecto(linea);
            if(nuevoVeterinario != null)
                veterinarios.add(nuevoVeterinario);
            
        }

        return veterinarios;
    }
}
