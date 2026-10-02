package veterinaria.utiles;

import java.util.List;
import java.util.function.Function;

import veterinaria.entidades.Dueño;
import veterinaria.entidades.Mascota;

public class Utiles {
	public static int maximaLongitudDueños(List<Dueño> dueños, Function<Dueño, String> obtenerAtributo, int def) {
		int ans = def;
		for (var d : dueños) {
			int actual = obtenerAtributo.apply(d).length();
			ans = Integer.max(actual, ans);
		}
		return ans;
	}

	// TODO hacer que esto no use lambdas
	public static int maximaLongitudMascotas(List<Mascota> mascotas, Function<Mascota, String> obtenerAtributo, String def) {
		int ans = def.length();
		for (var d : mascotas) {
			int actual = obtenerAtributo.apply(d).length();
			ans = Integer.max(actual, ans);
		}
		return ans;
	}
}
