package veterinaria.entidades;

import java.time.LocalDateTime;

public class Mascota {
	private int id;
	private boolean activo;
	private String nombre;
	private String especie;
	private String raza;
	private LocalDateTime fechaNacimiento;
	private Dueño dueño;
}
