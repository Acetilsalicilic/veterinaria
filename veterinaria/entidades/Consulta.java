package veterinaria.entidades;

import java.time.LocalDateTime;

public class Consulta {
	private int id;
	private LocalDateTime fechaYHora;
	private String motivo;
	private String diagnóstico;
	private String tratamiento;
	private Mascota mascota;
	private Veterinario veterinario;

	public Consulta(int id, LocalDateTime fechaYHora, String motivo, String diagnóstico, String tratamiento, Mascota mascota)
	{
		this.id = id;
		this.fechaYHora = fechaYHora;
		this.motivo = motivo;
		this.diagnóstico = diagnóstico;
		this.tratamiento = tratamiento;
		this.mascota = mascota;
	}

	public int getId() {
		return id;
	}

	public LocalDateTime getFechaYHora() {
		return fechaYHora;
	}

	public String getMotivo() {
		return motivo;
	}

	public String getDiagnóstico() {
		return diagnóstico;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public Mascota getMascota() {
		return mascota;
	}

	public Veterinario getVeterinario() {
		return veterinario;
	}
	public void setMascota(Mascota mascota) {
		this.mascota = mascota;
	}

	public void setVeterinario(Veterinario veterinario) {
		this.veterinario = veterinario;
	}
	
}


