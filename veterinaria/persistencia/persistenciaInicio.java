package veterinaria.persistencia;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;

import veterinaria.entidades.*;
import veterinaria.herramientas.*;

public class persistenciaInicio {
    public static void inicializadorDatos() {
        persistenciaDueño.setArchivo(Path.of("datosDueños.csv"));
        persistenciaMascota.setArchivo(Path.of("datosMascotas.csv"));
        persistenciaVeterinario.setArchivo(Path.of("datosVeterinarios.csv"));
        persistenciaConsulta.setArchivo(Path.of("datosConsultas.csv"));


        ArrayList<Integer> dueñosMascota = new ArrayList<>();
        ArrayList<Integer> MascotaConsulta = new ArrayList<>();
        ArrayList<Integer> VeterinarioConsulta = new ArrayList<>();

        LinkedList<Dueño> dueños =  persistenciaDueño.obteniendoDueños();
        LinkedList<Mascota> mascotas = persistenciaMascota.obteniendoMascotas(dueñosMascota);
        LinkedList<Veterinario> veterinarios = persistenciaVeterinario.obteniendoVeterinarios();
        LinkedList<Consulta> consultas = persistenciaConsulta.obtenerConsultas(MascotaConsulta, VeterinarioConsulta);

        int i = 0;
        for (Mascota mascota : mascotas) {
            int idPadre = dueñosMascota.get(i++);
            Dueño dueño = null;

            for (Dueño indiceDueño : dueños) {
                if(indiceDueño.getId() == idPadre) {
                    dueño = indiceDueño;
                    break;
                }
            }

            if(dueño == null)
                System.err.println("No se encontro el dueño");

            dueño.agregarMascota(mascota);
            mascota.setDueño(dueño);
        }

        HerramientasDueño.setDueños(dueños);

        HerramientasDueño.imprimiendoDueños();
        System.out.println(dueños.size());
        System.out.println(dueñosMascota.size());
        System.out.println(mascotas.size());
    }

    public static void main(String[] args) {
        inicializadorDatos();
    }
}