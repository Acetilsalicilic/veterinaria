package veterinaria.utiles;

import java.util.List;
import java.util.function.Function;

import veterinaria.entidades.Dueño;

public class Utiles {
	public static int maximaLongitud(List<Dueño> dueños, Function<Dueño, String> obtenerAtributo, int def) {
		int ans = def;
		for (var d : dueños) {
			int actual = obtenerAtributo.apply(d).length();
			ans = Integer.max(actual, ans);
		}
		return ans;
	}
}
