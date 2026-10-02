package veterinaria.interfaz.cli;

import java.util.List;
import java.util.function.Function;

import veterinaria.entidades.Dueño;

public class TablaDueños {
	private static final String[] cabeceras = {"ID", "NOMBRE", "TELÉFONO"};
	
	private static int maximaLongitud(List<Dueño> dueños, Function<Dueño, String> obtenerAtributo, int def) {
		int ans = def;
		for (var d : dueños) {
			int actual = obtenerAtributo.apply(d).length();
			ans = Integer.max(actual, ans);
		}
		return ans;
	}

	static void imprimirTabla(List<Dueño> dueños) {
		
		if (dueños.size() == 0) {
			System.out.println("# No se encontraron registros para mostrar.");
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
		System.out.println("# Se encontraron "+dueños.size()+" registros.");
	}
	
}
