package veterinaria.interfaz.cli;

import static veterinaria.utiles.Utiles.maximaLongitudDueños;

import java.util.*;

import veterinaria.Principal;
import veterinaria.entidades.Dueño;
import veterinaria.utiles.Campo;
import veterinaria.utiles.Utiles;

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
			
			imprimirTabla(Principal.dueños);
			
			System.out.print(MENU_BUSCAR_DUEÑO);
			System.out.printf(formatoPrompt, "lista de dueños");
			
			int opt = sc.nextInt();
			sc.nextLine();
			
			boolean salir = false;
			switch (opt) {
				case 0 -> salir = true;
				case 1 -> acciónBuscarDueño(Principal.dueños);
                default -> System.out.println(mensajeOpcionInválida);
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
			case 1 -> acciónMostrarDetalleDueño(dueño);
			case 2 -> acciónModificarDueño(dueño);
			case 3 -> {
				dueños.remove(dueño);
				System.out.println("Eliminado correctamente");
			}
			case 4 -> {
				// TODO esto está bien?
				new ListaMascota(sc, formatoPrompt, waiting, mensajeOpcionInválida, dueño).iniciar();
			}
            default -> System.out.println(mensajeOpcionInválida);
		}
		
		if (salir)
			return;
	}
	
	private void imprimirTabla(List<Dueño> dueños) {
		String[] cabeceras = {"ID", "NOMBRE", "TELÉFONO"};
		
		if (dueños.isEmpty()) {
			System.out.println("\t# No se encontraron registros para mostrar.");
			return;
		}

		// TODO agregar el resto de campos
		int max_id = Utiles.maximaLongitudDueños(dueños, d -> Integer.toString(d.getId()), cabeceras[0].length());
		int max_nombre = Utiles.maximaLongitudDueños(dueños, Dueño::getNombre, cabeceras[1].length());
		int max_telefono = Utiles.maximaLongitudDueños(dueños, Dueño::getTeléfono, cabeceras[2].length());
		
		var formato = "|%" +
                max_id +
                "s|%" +
                max_nombre +
                "s|%" +
                max_telefono +
                "s|\n";
		
		System.out.printf(formato, (Object[]) cabeceras);

		for (var d : dueños) {
			System.out.printf(formato,
                    d.getId(),
					d.getNombre(),
					d.getTeléfono());
		}

		System.out.println("\t# Se encontraron "+dueños.size()+" registros.");
	}

	private void acciónMostrarDetalleDueño(Dueño dueño) {
		List<Campo> info = new LinkedList<>();

		info.add(new Campo("ID", dueño.getId()));
		info.add(new Campo("Nombre", dueño.getNombre()));
		info.add(new Campo("Teléfono", dueño.getTeléfono()));

		int maxAncho = 0;
		for (Campo campo : info)
			maxAncho = Integer.max(maxAncho, campo.etiqueta.length());

		String formatoCampo = "%"+maxAncho+"s: %s\n";

		System.out.println("--- Detalle Dueño ----");
		info.forEach(c -> System.out.printf(formatoCampo, c.etiqueta, c.valor));

		// TODO mostrar el resto de atributos

		System.out.println();

		System.out.println(waiting);
		sc.nextLine();
	}

	private void acciónCrearDueño() {
		// TODO implementar esto
	}

	private void acciónModificarDueño(Dueño dueño) {
		String cabecera = "# %s:\n";
		String formatoAnterior = "Valor anterior: %s\n";

		System.out.printf(cabecera, "Nombre");
		System.out.printf(formatoAnterior, dueño.getNombre());
		System.out.printf(formatoPrompt, "Nombre");
		String nombre = sc.nextLine();

		System.out.printf(cabecera, "Teléfono");
		System.out.printf(formatoAnterior, dueño.getTeléfono());
		System.out.printf(formatoPrompt, "Teléfono");
		String teléfono = sc.nextLine();

		System.out.printf(cabecera, "Dirección");
		System.out.printf(formatoAnterior, dueño.getDirección());
		System.out.printf(formatoPrompt, "Dirección");
		String dirección = sc.nextLine();

		// TODO agregar los atributos que faltan por preguntar
		// TODO cambiar isEmpty por isBlank
		if (!nombre.isEmpty())
			dueño.setNombre(nombre);
		if (!teléfono.isEmpty())
			dueño.setTeléfono(teléfono);
		if (!dirección.isEmpty())
			dueño.setDirección(dirección);
	}
}
