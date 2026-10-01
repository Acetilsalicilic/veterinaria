package veterinaria.interfaz.cli;

import java.util.List;
import java.util.Scanner;

import veterinaria.entidades.Dueño;

public class CLI {
	
	private static final String prompt = "[%s]> ";
	private static final String waiting = "ENTER...";
	
	private static final String MENU_PRINCIPAL = """
			--- MENU PRINCIPAL ---
			1. Lista de dueños
			2. Registrar dueño
			
			0. Salir
			""";
	
	private static final String MENU_BUSCAR_DUEÑO = """
			--- Lista de dueños ---
			0. Volver
			""";
	
	private static Scanner sc;
	
	public static void run() {
		CLI.sc = new Scanner(System.in);
		for (;;) {
			boolean exit = false;
			System.out.println(MENU_PRINCIPAL);
			System.out.printf(prompt, "principal");
			
			int opt = sc.nextInt();
			sc.nextLine();
			
			// Menu
			switch (opt) {
			case 0 -> exit = true;
			case 1 -> menuBuscarDueño();
			}
			
			if (exit) break;
		}
		sc.close();
	}
	
	private static void menuBuscarDueño() {
		for (;;) {
			TablaDueños.imprimirTabla(List.of(new Dueño("Xhuk", "992")));
			
			System.out.print(MENU_BUSCAR_DUEÑO);
			System.out.printf(prompt, "listaDueños");
			
			int opt = sc.nextInt();
			sc.nextLine();
			
			boolean salir = false;
			switch (opt) {
			case 0 -> salir = true;
			}
			
			if (salir) return;
			
			System.out.println(waiting);
			sc.nextLine();
		}
		
	}
}
