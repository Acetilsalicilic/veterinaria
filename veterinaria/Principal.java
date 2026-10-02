package veterinaria;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import veterinaria.entidades.Dueño;
import veterinaria.entidades.Mascota;
import veterinaria.interfaz.cli.CLI;

public class Principal {
	// TODO cambiar esto por HerramientasDueños
	public static List<Dueño> dueños = new ArrayList<>();

	public static void main(String[] args) {
		dueños.add(new Dueño("Xhuk", "992"));
		dueños.getLast().getMascotas().add(new Mascota("Jack",
				"Perro",
				"IDK",
				LocalDateTime.now(),
				dueños.getLast()));

		CLI.run();

	}

}
