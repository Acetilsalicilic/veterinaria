package veterinaria;

import java.util.ArrayList;
import java.util.List;

import veterinaria.entidades.Dueño;
import veterinaria.interfaz.cli.CLI;

public class Principal {
	public static List<Dueño> dueños = new ArrayList<>(List.of(new Dueño("Xhuk", "992")));

	public static void main(String[] args) {

		CLI.run();

	}

}
