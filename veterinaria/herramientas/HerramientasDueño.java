package veterinaria.herramientas;

import java.util.LinkedList;
import veterinaria.entidades.Dueño;
import veterinaria.entidades.Mascota;
import veterinaria.persistencia.persistenciaDueño;

public class HerramientasDueño {
    private static LinkedList<Dueño> dueños;

    public static void setDueños(LinkedList<Dueño> dueños) {
        HerramientasDueño.dueños = dueños;
    }

    public static void imprimiendoDueños() {
        System.out.println("Imprimiendo dueños");
        for (Dueño dueño : dueños) {
            System.out.println(dueño.getId() + " -> " + dueño.getNombre() + "- Mascotas: ");
            for(Mascota mascota : dueño.getMascotas())
            {
                System.out.println("\t" + mascota.getId() + " - " + mascota.getNombre());
            }
        }
    }

    public static void registrarDueño(Dueño dueño) {
        dueños.add(dueño);

        try {
            persistenciaDueño.registrarDueño(dueño);
        } catch (Exception e) {
            System.err.println("No se pudo guardar al dueño");
        }
    }
}