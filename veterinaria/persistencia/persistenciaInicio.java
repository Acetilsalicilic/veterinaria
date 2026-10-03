package veterinaria.persistencia;

import java.nio.file.Path;

import veterinaria.herramientas.herramientaDueño;

public class persistenciaInicio {
    public static void inicializadorDatos() {
        persistenciaDueño.setArchivo(Path.of("datosDueño.csv"));
        persistenciaMascota.setArchivo(Path.of("datosMascotas.csv"));
        persistenciaVeterinario.setArchivo(Path.of("datosVeterinarios.csv"));
        persistenciaConsulta.setArchivo(Path.of("datosConsultas.csv"));

        herramientaDueño.setDueños(persistenciaDueño.obteniendoDueños());
    }

    public static void main(String[] args) {
        inicializadorDatos();
    }
}