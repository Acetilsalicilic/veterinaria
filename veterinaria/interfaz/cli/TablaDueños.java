package veterinaria.interfaz.cli;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import veterinaria.entidades.Dueño;

public class TablaDueños {
	private static final String[] cabeceras = {"NOMBRE", "TELÉFONO"};
	
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
		
		int max_nombre = maximaLongitud(dueños, Dueño::getNombre, cabeceras[0].length());
		int max_telefono = maximaLongitud(dueños, Dueño::getTeléfono, cabeceras[1].length());
		
		var cabecera = new StringBuilder()
				.append("|%")
				.append(max_nombre)
				.append("s|%")
				.append(max_telefono)
				.append("s|\n")
				.toString();
		
		System.out.printf(cabecera, (Object[]) cabeceras);
		
		// Imprimir las entidades
		var formato = new StringBuilder()
				.append("|%")
				.append(max_nombre)
				.append("s|%")
				.append(max_telefono)
				.append("s|\n")
				.toString();
		
		for (var d : dueños) {
			System.out.printf(formato, 
					d.getNombre(),
					d.getTeléfono());
		}
		
		// Imprimir el número de entidades
		System.out.println("# Se encontraron "+dueños.size()+" registros.");
	}
	
}
