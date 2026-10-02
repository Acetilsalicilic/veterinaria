package veterinaria.interfaz.cli;

import static veterinaria.utiles.Utiles.maximaLongitud;

import java.util.List;
import java.util.Scanner;

import veterinaria.Principal;
import veterinaria.entidades.Dueño;

public class ListaDueño extends SubMenu {
	private static final String ENCABEZADO_MENU = """
			
			--- Lista de dueños ---
			
			""";
	private static final String MENU_BUSCAR_DUEÑO = """
			
			--- *************** ---
			1. Buscar dueño
			2. Registrar dueño
			
			0. Volver
			""";
	private static final String MENU_ACCIONES_DUEÑO = """
			1. Ver detalle
			2. Modificar dueño
			3. Eliminar dueño
			
			0. Volver
			""";
	
	
	public ListaDueño(Scanner sc, String formatoPrompt, String waiting) {
		super(sc, formatoPrompt, waiting);
	}
	
	@Override
	public void iniciar() {
		for (;;) {
			System.out.print(ENCABEZADO_MENU);
			
			imprimirTabla(Principal.dueños);
			
			System.out.print(MENU_BUSCAR_DUEÑO);
			System.out.printf(formatoPrompt, "lista de dueños");
			
			int opt = sc.nextInt();
			sc.nextLine();
			
			boolean salir = false;
			switch (opt) {
			case 0 -> salir = true;
			case 1 -> acciónBuscarDueño(Principal.dueños);
			}
			
			if (salir) return;
			
			//System.out.println(waiting);
			//sc.nextLine();
		}
	}
	
	
	private void acciónBuscarDueño(List<Dueño> dueños) {
		for (;;) {
			System.out.println("Ingrese el término de búsqueda (-1 para continuar):");
			System.out.printf(formatoPrompt, "buscar dueño");
			String busqueda = sc.nextLine();
			
			if (busqueda.equals("-1"))
				break;
			
			// TODO hacer que HerramientasDueño haga este filtrado
			var resultado = dueños.stream()
					.filter(d -> d.getNombre().toLowerCase().contains(busqueda.toLowerCase()))
					.toList();
			
			imprimirTabla(resultado);
		}
		
		
		System.out.println("Ingrese el ID del dueño (-1 para cancelar):");
		System.out.printf(formatoPrompt, "id dueño");
		int id = sc.nextInt();
		sc.nextLine();
		
		if (id == -1)
			return;
		
		var posibleDueño = dueños.stream()
				.filter(d -> d.getId() == id)
				.findFirst();
		
		if (posibleDueño.isEmpty()) {
			System.out.println("El ID ingresado no existe.");
			return;
		}
		
		var dueño = posibleDueño.get();
		
		System.out.println("Elija una acción:");
		System.out.print(MENU_ACCIONES_DUEÑO);
		System.out.printf(formatoPrompt, "id dueño = "+id);
		
		int opt = sc.nextInt();
		sc.nextLine();
		
		boolean salir = false;
		switch (opt) {
		case 0 -> salir = true;
		case 3 -> {
			dueños.remove(dueño);
			System.out.println("Eliminado correctamente");
		}
		}
		
		if (salir)
			return;
	}
	
	
	private void imprimirTabla(List<Dueño> dueños) {
		String[] cabeceras = {"ID", "NOMBRE", "TELÉFONO"};
		
		if (dueños.size() == 0) {
			System.out.println("\t# No se encontraron registros para mostrar.");
			return;
		}
		
		int max_id = maximaLongitud(dueños, d -> Integer.toString(d.getId()), cabeceras[0].length());
		int max_nombre = maximaLongitud(dueños, Dueño::getNombre, cabeceras[1].length());
		int max_telefono = maximaLongitud(dueños, Dueño::getTeléfono, cabeceras[2].length());
		
		var formato = new StringBuilder()
				.append("|%")
				.append(max_id)
				.append("s|%")
				.append(max_nombre)
				.append("s|%")
				.append(max_telefono)
				.append("s|\n")
				.toString();
		
		System.out.printf(formato, (Object[]) cabeceras);
		
		// Imprimir las entidades
		
		
		for (var d : dueños) {
			System.out.printf(formato, 
					Integer.toString(d.getId()),
					d.getNombre(),
					d.getTeléfono());
		}
		
		// Imprimir el número de entidades
		System.out.println("\t# Se encontraron "+dueños.size()+" registros.");
	}

}
