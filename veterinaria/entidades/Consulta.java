package veterinaria.entidades;

import java.time.LocalDateTime;

public class Consulta {
	private int id;
	private LocalDateTime fechaYHora;
	private String motivo;
	private String diagnóstico;
	private String tratamiento;
	private Mascota mascota;

	public Consulta(LocalDateTime fechaYHora, String motivo, Mascota mascota) {
		this.fechaYHora = fechaYHora;
		this.motivo = motivo;
		this.mascota = mascota;
	}

	public int getId() {
		return id;
	}

	public LocalDateTime getFechaYHora() {
		return fechaYHora;
	}

	public void setFechaYHora(LocalDateTime fechaYHora) {
		this.fechaYHora = fechaYHora;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getDiagnóstico() {
		return diagnóstico;
	}

	public void setDiagnóstico(String diagnóstico) {
		this.diagnóstico = diagnóstico;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	public Mascota getMascota() {
		return mascota;
	}

	public void setMascota(Mascota mascota) {
		this.mascota = mascota;
	}
}
