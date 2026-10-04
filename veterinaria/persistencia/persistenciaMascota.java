package veterinaria.persistencia;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.time.LocalDateTime;
import java.util.List;

import veterinaria.entidades.*;

/*
    Formato en el .csv
    ID,activo,nombre,especie,raza,fechanacimiento,IDdueño
*/

public class persistenciaMascota {
    private static Path archivo;

    public static void setArchivo(Path archivo) {
        persistenciaMascota.archivo = archivo;
    }

    public static Mascota mascotaStringAObjeto(String strMascota) {
        String[] datos = strMascota.split(",", -1);

        if(datos.length < 7)
        {
            System.err.println("Formato invalido");
            return null;
        }

        return new Mascota(Integer.valueOf(datos[0]), Boolean.valueOf(datos[1]), datos[2], datos[3], datos[4], LocalDateTime.parse(datos[5]), null);
    }

    public static String mascotaObjetoAString(Mascota mascota) {
        LinkedList<String> mascotaActualizado = new LinkedList<String>();
        mascotaActualizado.add(String.valueOf(mascota.getId()));
        mascotaActualizado.add(String.valueOf(mascota.getActivo()));
        mascotaActualizado.add(mascota.getNombre());
        mascotaActualizado.add(mascota.getEspecie());
        mascotaActualizado.add(mascota.getRaza());
        mascotaActualizado.add(String.valueOf(mascota.getFechaNacimiento()));
        mascotaActualizado.add(String.valueOf(mascota.getDueño().getId()));

        return String.join(",", mascotaActualizado);
    }

    // Retorna verdadero si no pudo actualizar a la mascota
    public static boolean actualizarMascota(Mascota mascota){
        List<String> lineas;
        try {
            lineas = Files.readAllLines(archivo);
        } catch (Exception e) {
            return true;
        }
        
        for(int i = 0; i < lineas.size(); i++) {
            String[] datos = lineas.get(i).split(",");
            if(Integer.valueOf(datos[0]) == mascota.getId()) {
                lineas.set(i, mascotaObjetoAString(mascota));
                
                try {
                    Files.write(archivo, lineas);
                } catch (Exception e) {
                    return true;
                }

                System.out.println("Se actualiza elemento");
                return false;
            }
        }

        agregarMascota(mascota);
        return true;
    }

    // Retorna verdadero si no pudo agregar a la mascota
    public static boolean agregarMascota(Mascota mascota) {
        List<String> lineas;
        try {
            lineas = Files.readAllLines(archivo);
        } catch (Exception e) {
            return true;
        }

        lineas.add(mascotaObjetoAString(mascota));

        try {
            Files.write(archivo, lineas);
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    // Retorna una lista vacia si no pudo leer el archivo de persistencia;
    public static LinkedList<Mascota> obteniendoMascotas(ArrayList<Integer> dueñosIDs) {
        List<String> lineas;
        try {
            lineas = Files.readAllLines(archivo);
        } catch (Exception e) {
            return new LinkedList<Mascota>();
        }
        
        LinkedList<Mascota> mascotas = new LinkedList<Mascota>();
        
        for (String linea : lineas) {
            Mascota nuevaMascota = mascotaStringAObjeto(linea);
            if(nuevaMascota != null)
            {
                mascotas.add(nuevaMascota);
                String[] datos = linea.split(",", -1);
                dueñosIDs.add(Integer.valueOf(datos[6]));
            }
        }

        return mascotas;
    }
}