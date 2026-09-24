package veterinaria.entidades;

import java.time.LocalDateTime;

public class Consulta {
	private int id;
	private LocalDateTime fechaYHora;
	private String motivo;
	private String diagnóstico;
	private String tratamiento;
	private Mascota mascota;
}
