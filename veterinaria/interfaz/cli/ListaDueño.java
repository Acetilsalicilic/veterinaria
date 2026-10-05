package veterinaria.interfaz.cli;

import java.util.*;

import veterinaria.entidades.Dueño;
import veterinaria.herramientas.HerramientasDueño;
import veterinaria.utiles.Campo;

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
			1. Ver información
			2. Modificar dueño
			3. Eliminar dueño
			
			4. Ver mascotas
			
			0. Volver
			""";
	
	public ListaDueño(Scanner sc, String formatoPrompt, String waiting, String opcionInvalida) {
		super(sc, formatoPrompt, waiting, opcionInvalida);
	}
	
	@Override
	public void iniciar() {
		for (;;) {
			System.out.print(ENCABEZADO_MENU);
			
			imprimirTabla(HerramientasDueño.internoGetDueños());
			
			System.out.print(MENU_BUSCAR_DUEÑO);
			System.out.printf(formatoPrompt, "lista de dueños");
			
			int opt = sc.nextInt();
			sc.nextLine();
			
			boolean salir = false;
			switch (opt) {
				case 0 -> salir = true;
				case 1 -> acciónBuscarDueño(HerramientasDueño.internoGetDueños());
				case 2 -> HerramientasDueño.crearDueño(sc);
                default -> System.out.println(mensajeOpcionInválida);
			}
			
			if (salir) return;
		}
	}
	
	
	private void acciónBuscarDueño(List<Dueño> dueños) {
		for (;;) {
			System.out.println("Ingrese el término de búsqueda (-1 para continuar):");
			System.out.printf(formatoPrompt, "buscar dueño");
			String busqueda = sc.nextLine();
			
			if (busqueda.equals("-1"))
				break;
			
			var resultado = HerramientasDueño.filtrarPorNombre(busqueda);
			
			imprimirTabla(resultado);
		}
		
		
		System.out.println("Ingrese el ID del dueño (-1 para cancelar):");
		System.out.printf(formatoPrompt, "id dueño");
		int id = sc.nextInt();
		sc.nextLine();
		
		if (id == -1)
			return;

		Dueño dueño = null;
		for (var d : dueños)
			if (d.getId() == id)
				dueño = d;

		if (dueño == null) {
			System.out.println("❌ El ID ingresado no existe.");
			return;
		}

		System.out.println("Elija una acción:");
		System.out.print(MENU_ACCIONES_DUEÑO);
		System.out.printf(formatoPrompt, "id dueño = "+id);
		
		int opt = sc.nextInt();
		sc.nextLine();
		
		switch (opt) {
			case 0 -> {}
			case 1 -> acciónMostrarDetalleDueño(dueño);
			case 2 -> HerramientasDueño.modificarDueño(sc, dueño);
			case 3 -> HerramientasDueño.bajaDueño(dueño);
			case 4 -> {
				new ListaMascota(sc, formatoPrompt, waiting, mensajeOpcionInválida, dueño).iniciar();
			}
            default -> System.out.println(mensajeOpcionInválida);
		}
	}
	
	private void imprimirTabla(List<Dueño> dueños) {
		String[] cabeceras = {"ID", "NOMBRE", "TELÉFONO", "NO. MASCOTAS"};
		
		if (dueños.isEmpty()) {
			System.out.println("\t# No se encontraron registros para mostrar.");
			return;
		}

		int max_id = cabeceras[0].length();
		for (var d : dueños)
			max_id = Integer.max(max_id, Integer.toString(d.getId()).length());

		int max_nombre = cabeceras[1].length();
		for (var d : dueños)
			max_nombre = Integer.max(max_nombre, d.getNombre().length());

		int max_telefono = cabeceras[2].length();
		for (var d : dueños)
			max_telefono = Integer.max(max_telefono, d.getTeléfono().length());

		int max_no_mascotas = cabeceras[3].length();
		for (var d : dueños)
			max_no_mascotas = Integer.max(max_no_mascotas, Integer.toString(d.getMascotas().size()).length());

		var formato = "|%" +
                max_id + "s|%" +
                max_nombre + "s|%" +
                max_telefono + "s|%" +
				max_no_mascotas + "s|\n";
		
		System.out.printf(formato, (Object[]) cabeceras);

		for (var d : dueños) {
			System.out.printf(formato,
                    d.getId(),
					d.getNombre(),
					d.getTeléfono(),
					d.getMascotas().size());
		}

		System.out.println("\t# Se encontraron "+dueños.size()+" registros.");
	}

	private void acciónMostrarDetalleDueño(Dueño dueño) {
		List<Campo> info = new LinkedList<>();

		info.add(new Campo("ID", dueño.getId()));
		info.add(new Campo("Nombre", dueño.getNombre()));
		info.add(new Campo("Teléfono", dueño.getTeléfono()));
		info.add(new Campo("Dirección", dueño.getDirección()));
		info.add(new Campo("Teléfono de emergencia", dueño.getTelefonoDeEmergencia()));
		info.add(new Campo("Nombre de emergencia", dueño.getNombreDeEmergencia()));
		info.add(new Campo("Mascotas", dueño.getMascotas().size()));

		int maxAncho = 0;
		for (Campo campo : info)
			maxAncho = Integer.max(maxAncho, campo.etiqueta.length());

		String formatoCampo = "%"+maxAncho+"s: %s\n";

		System.out.println("--- Detalle Dueño ----");
		for (var c : info)
			System.out.printf(formatoCampo, c.etiqueta, c.valor);

		System.out.println();

		System.out.println(waiting);
		sc.nextLine();
	}
}
