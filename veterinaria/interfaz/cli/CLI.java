package veterinaria.interfaz.cli;

import java.util.Scanner;

public class CLI {
	
	private static final String prompt = "[%s]> ";
	private static final String waiting = "ENTER...";
	
	private static final String MENU_PRINCIPAL = """
			--- MENU PRINCIPAL ---
			1. Lista de dueños
			
			0. Salir
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
			case 1 -> {
				new ListaDueño(sc, prompt, waiting).iniciar();
			}
			}
			
			if (exit) break;
		}
		sc.close();
	}	
}
