package veterinaria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import veterinaria.entidades.Dueño;
import veterinaria.entidades.Mascota;
import veterinaria.entidades.Veterinario;
import veterinaria.herramientas.HerramientasDueño;
import veterinaria.herramientas.HerramientasVeterinario;
import veterinaria.interfaz.cli.CLI;
import veterinaria.persistencia.persistenciaInicio;

public class Principal {
	static void main(String[] args) {

		if (persistenciaInicio.inicializadorDatos())
			System.exit(1);

		CLI.run();

	}

}
