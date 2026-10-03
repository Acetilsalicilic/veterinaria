package veterinaria.persistencia;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import veterinaria.entidades.*;

public class persistenciaDueño {
    private static Path archivo;

    public static void setArchivo(Path archivo) {
        persistenciaDueño.archivo = archivo;
    }

    // Retorna verdadero si no encuentra un veterinario con el ID, lo cuál nunca debería pasar
    public static boolean actualizarDueño(Dueño dueño) throws IOException {
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

        registrarDueño(dueño);
        return true;
    }

    public static void registrarDueño(Dueño dueño) throws IOException {
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

    public static LinkedList<Dueño> obteniendoDueños() {
        List<String> lineas;

        try {
            lineas = Files.readAllLines(archivo);
        } catch (Exception e) {
            return new LinkedList<Dueño>();
        }
        
        LinkedList<Dueño> dueños = new LinkedList<Dueño>();

        for(String linea : lineas) {
            Dueño dueño = crearDueño(linea);
            if (dueño != null) {
                dueños.add(dueño);
            }
        }

        return dueños;
    }

    private static Dueño crearDueño(String strDueño) {
        String[] datos = strDueño.split(",", -1);

        if(datos.length < 7)
        {
            System.err.println("Formato invalido");
            return null;
        }

        return new Dueño(Integer.valueOf(datos[0]), Boolean.valueOf(datos[1]), datos[2], datos[3], datos[4], datos[5], datos[6]);
    }
}