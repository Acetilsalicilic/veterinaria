package veterinaria.persistencia;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.time.LocalDateTime;
import java.util.List;

import veterinaria.entidades.*;

public class persistenciaMascota {
    private static Path archivo;

    public static void setArchivo(Path archivo) {
        persistenciaMascota.archivo = archivo;
    }

    // Retorna verdadero si no encuentra un veterinario con el ID, lo cuál nunca debería pasar
    public static boolean actualizarMascota(Mascota mascota) throws IOException{
        List<String> lineas = Files.readAllLines(archivo);

        ArrayList<String> mascotaActualizado = new ArrayList<String>();
        mascotaActualizado.add(String.valueOf(mascota.getId()));
        mascotaActualizado.add(String.valueOf(mascota.getActivo()));
        mascotaActualizado.add(mascota.getNombre());
        mascotaActualizado.add(mascota.getEspecie());
        mascotaActualizado.add(mascota.getRaza());
        mascotaActualizado.add(String.valueOf(mascota.getFechaNacimiento()));
        mascotaActualizado.add(String.valueOf(mascota.getDueño().getId()));
        
        for(int i = 0; i < lineas.size(); i++) {
            String[] datos = lineas.get(i).split(",");
            if(Integer.valueOf(datos[0]) == mascota.getId()) {
                lineas.set(i, String.join(",", mascotaActualizado));
                Files.write(archivo, lineas);
                System.out.println("Se actualiza elemento");
                return false;
            }
        }

        agregarMascota(mascota);
        return true;
    }

    public static void agregarMascota(Mascota mascota) throws IOException {
        List<String> lineas = Files.readAllLines(archivo);

        ArrayList<String> mascotaNuevo = new ArrayList<String>();
        mascotaNuevo.add(String.valueOf(mascota.getId()));
        mascotaNuevo.add(String.valueOf(mascota.getActivo()));
        mascotaNuevo.add(mascota.getNombre());
        mascotaNuevo.add(mascota.getEspecie());
        mascotaNuevo.add(mascota.getRaza());
        mascotaNuevo.add(String.valueOf(mascota.getFechaNacimiento()));
        mascotaNuevo.add(String.valueOf(mascota.getDueño().getId()));
        
        lineas.add(String.join(",", mascotaNuevo));

        Files.write(archivo, lineas);
        System.out.println("Se agrega elemento");
    }

    public static LinkedList<Mascota> obteniendoMascotas(ArrayList<Integer> dueñoId) {
        List<String> lineas;

        try {
            lineas = Files.readAllLines(archivo);
        } catch (Exception e) {
            return new LinkedList<Mascota>();
        }

        LinkedList<Mascota> mascotas = new LinkedList<Mascota>();
        
        for (String linea : lineas) {
            Mascota nuevaMascota = crearMascota(linea);
            if(nuevaMascota != null)
            {
                mascotas.add(nuevaMascota);
                String[] datos = linea.split(",", -1);
                dueñoId.add(Integer.valueOf(datos[6]));
            }
        }

        return mascotas;
    }

    private static Mascota crearMascota(String strMascota) {
        String[] datos = strMascota.split(",", -1);

        if(datos.length < 7)
        {
            System.err.println("Formato invalido");
            return null;
        }

        return new Mascota(Integer.valueOf(datos[0]), Boolean.valueOf(datos[1]), datos[2], datos[3], datos[4], LocalDateTime.parse(datos[5]), null);
    }
}