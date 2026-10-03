package veterinaria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import veterinaria.entidades.Dueño;
import veterinaria.entidades.Mascota;
import veterinaria.entidades.Veterinario;
import veterinaria.interfaz.cli.CLI;

public class Principal {
	// TODO cambiar esto por HerramientasDueños
	public static List<Dueño> dueños = new ArrayList<>();
	public static List<Veterinario> veterinarios = new ArrayList<>();

	public static void main(String[] args) {
		dueños.add(new Dueño("Xhuk", "992"));
		dueños.getLast().getMascotas().add(new Mascota("Jack",
				"Perro",
				"IDK",
				LocalDate.now(),
				dueños.getLast()));
		veterinarios.add(new Veterinario("Carlos", "Perros"));

		CLI.run();

	}

}
