package veterinaria.persistencia;

import java.nio.file.*;
import java.util.LinkedList;
import java.util.List;

import veterinaria.entidades.*;

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
        LinkedList<String> camposVeterinario = new LinkedList<String>();
        camposVeterinario.add(String.valueOf(veterinario.getId()));
        camposVeterinario.add(String.valueOf(veterinario.isActivo()));
        camposVeterinario.add(veterinario.getNombre());
        camposVeterinario.add(veterinario.getEspecialidad());
        return String.join(",", camposVeterinario);
    }
    
    public static boolean actualizarVeterinario(Veterinario veterinario) {
        List<String> lineas;

        try {
            lineas = Files.readAllLines(archivo);
        } catch (Exception e) {
            return true;
        }
        
        for(int i = 0; i < lineas.size(); i++) {
            String[] datos = lineas.get(i).split(",", -1);
            if(Integer.valueOf(datos[0]) == veterinario.getId()) {
                lineas.set(i, veterinarioObjetoAString(veterinario));

                try {
                    Files.write(archivo, lineas);
                } catch (Exception e) {
                    return true;
                }
                
                return false;
            }
        }

        return agregarVeterinario(veterinario);
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
            return new LinkedList<>();
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
