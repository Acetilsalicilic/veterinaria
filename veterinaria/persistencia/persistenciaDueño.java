package veterinaria.persistencia;

import java.nio.file.*;
import java.util.LinkedList;
import java.util.List;

import veterinaria.entidades.*;

public class persistenciaDueño {
    private static Path archivo;

    public static void setArchivo(Path archivo) {
        persistenciaDueño.archivo = archivo;
    }

    private static Dueño dueñoStringAObjecto(String strDueño) {
        String[] datos = strDueño.split("\\|", -1);

        if(datos.length < 7) {
            System.err.println("Formato invalido");
            return null;
        }

        return new Dueño(Integer.valueOf(datos[0]), Boolean.valueOf(datos[1]), datos[2], datos[3], datos[4], datos[5], datos[6]);
    }

    private static String dueñoObjetoAString(Dueño dueño) {
        LinkedList<String> camposDueño = new LinkedList<String>();
        camposDueño.add(String.valueOf(dueño.getId()));
        camposDueño.add(String.valueOf(dueño.isActivo()));
        camposDueño.add(dueño.getNombre());
        camposDueño.add(dueño.getTeléfono());
        camposDueño.add(dueño.getDirección());
        camposDueño.add(dueño.getTelefonoDeEmergencia());
        camposDueño.add(dueño.getNombreDeEmergencia());

        return String.join("|", camposDueño);
    }

    public static boolean actualizarDueño(Dueño dueño) {
        List<String> lineas;
        try {
            lineas = Files.readAllLines(archivo);
        } catch (Exception e) {
            return true;
        }
        
        for(int i = 0; i < lineas.size(); i++) {
            String[] datos = lineas.get(i).split("\\|", -1);
            if(Integer.valueOf(datos[0]) == dueño.getId()) {
                lineas.set(i, dueñoObjetoAString(dueño));
                
                try {
                    Files.write(archivo, lineas);
                } catch (Exception e) {
                    return true;
                }

                return false;
            }
        }

        return agregarDueño(dueño);
    }

    public static boolean agregarDueño(Dueño dueño) {
        List<String> lineas;

        try {
            lineas = Files.readAllLines(archivo);
        } catch (Exception e) {
            return true;
        }
        
        lineas.add(dueñoObjetoAString(dueño));

        try {
            Files.write(archivo, lineas);
        } catch (Exception e) {
            return true;
        }

        return false;
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
            Dueño dueño = dueñoStringAObjecto(linea);
            if (dueño != null)
                dueños.add(dueño);
        }

        return dueños;
    }
}