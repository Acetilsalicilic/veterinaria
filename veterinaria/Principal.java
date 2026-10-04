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

public class Principal {
	static void main(String[] args) {
		HerramientasDueño.internoCrearDueño(new Dueño("Xhuk", "992", "Quintana Raw", "5533", "equisde"));
		HerramientasDueño.internoGetDueños().getLast().getMascotas().add(new Mascota("Jack",
				"Perro",
				"IDK",
				LocalDate.now(),
				HerramientasDueño.internoGetDueños().getLast()));

		CLI.run();

	}

}
