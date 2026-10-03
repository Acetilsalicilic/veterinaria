package veterinaria.persistencia;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import veterinaria.entidades.*;

public class persistenciaVeterinario {
    // Retorna verdadero si no encuentra un veterinario con el ID, lo cuál nunca debería pasar
    public static boolean actualizarVeterinario(Veterinario veterinario) throws IOException {
        Path archivo = Path.of("datosVeterinario.csv");
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
        Path archivo = Path.of("datosVeterinario.csv");
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
}
