package veterinaria.persistencia;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import veterinaria.entidades.*;

class persistenciaDueño {
    // Retorna verdadero si no encuentra un veterinario con el ID, lo cuál nunca debería pasar
    public static boolean actualizarDueño(Dueño dueño) throws IOException {
        Path archivo = Path.of("datosDueño.csv");
        List<String> lineas = Files.readAllLines(archivo);

        ArrayList<String> dueñoActualizado = new ArrayList<String>();
        dueñoActualizado.add(String.valueOf(dueño.getId()));
        dueñoActualizado.add(String.valueOf(dueño.getActivo()));
        dueñoActualizado.add(dueño.getNombre());
        dueñoActualizado.add(dueño.getTeléfono());
        dueñoActualizado.add(dueño.getDirección());
        dueñoActualizado.add(dueño.getTelefonoDeEmergencia());
        dueñoActualizado.add(dueño.getNombreDeEmergencia());
        
        for(int i = 0; i < lineas.size(); i++) {
            String[] datos = lineas.get(i).split(",");
            if(Integer.valueOf(datos[0]) == dueño.getId()) {
                lineas.set(i, String.join(",", dueñoActualizado));
                Files.write(archivo, lineas);
                System.out.println("Se actualiza elemento");
                return false;
            }
        }

        agregarDueño(dueño);
        return true;
    }

    public static void agregarDueño(Dueño dueño) throws IOException {
        Path archivo = Path.of("datosDueño.csv");
        List<String> lineas = Files.readAllLines(archivo);

        ArrayList<String> dueñoNuevo = new ArrayList<String>();
        dueñoNuevo.add(String.valueOf(dueño.getId()));
        dueñoNuevo.add(String.valueOf(dueño.getActivo()));
        dueñoNuevo.add(dueño.getNombre());
        dueñoNuevo.add(dueño.getTeléfono());
        dueñoNuevo.add(dueño.getDirección());
        dueñoNuevo.add(dueño.getTelefonoDeEmergencia());
        dueñoNuevo.add(dueño.getNombreDeEmergencia());
        
        lineas.add(String.join(",", dueñoNuevo));

        Files.write(archivo, lineas);
        System.out.println("Se agrega elemento");
    }
}