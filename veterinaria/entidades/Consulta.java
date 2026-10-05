package veterinaria.entidades;

import java.time.LocalDateTime;

public class Consulta {
	private static int ultimoId = 0;
	private final int id;
	private LocalDateTime fechaYHora;
	private String motivo;
	private String diagnóstico;
	private String tratamiento;
	private Mascota mascota;
	private Veterinario veterinario;

	public Consulta(int id, LocalDateTime fechaYHora, String motivo, String diagnóstico, String tratamiento, Mascota mascota, Veterinario veterinario)
	{
		this.id = id;
		this.fechaYHora = fechaYHora;
		this.motivo = motivo;
		this.diagnóstico = diagnóstico;
		this.tratamiento = tratamiento;
		this.mascota = mascota;
		if (veterinario == null)
			this.veterinario = new Veterinario("", "");
		else
			this.veterinario = veterinario;
		ultimoId = Integer.max(id, ultimoId) + 1;
	}

	@Override
	public String toString() {
		return "Consulta{" +
				"id=" + id +
				", fechaYHora=" + fechaYHora +
				", motivo='" + motivo + '\'' +
				", diagnóstico='" + diagnóstico + '\'' +
				", tratamiento='" + tratamiento + '\'' +
				", mascota=" + mascota +
				", veterinario=" + veterinario +
				'}';
	}

	public Consulta(LocalDateTime fechaYHora, String motivo, Mascota mascota, Veterinario veterinario) {
		this(ultimoId, fechaYHora, motivo, "", "", mascota, veterinario);
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

	public void setFechaYHora(LocalDateTime fechaYHora) {
		this.fechaYHora = fechaYHora;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public void setDiagnóstico(String diagnóstico) {
		this.diagnóstico = diagnóstico;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}
}


