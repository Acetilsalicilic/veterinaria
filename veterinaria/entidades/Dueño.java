package veterinaria.entidades;

import java.util.Set;

public class Dueño {
	private int id;
	private boolean activo;
	private String nombre;
	private String teléfono;
	private String dirección;
	private String telefonoDeEmergencia;
	private String nombreDeEmergencia;
	private Set<Mascota> mascotas;
}
