package veterinaria.entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Mascota {
	private static int ultimoId = 0;
	private int id;
	private boolean activo;
	private String nombre;
	private String especie;
	private String raza;
	private LocalDate fechaNacimiento;
	private Dueño dueño;

	public Mascota(String nombre, String especie, String raza, LocalDate fechaNacimiento, Dueño dueño) {
		this.activo = true;
		this.nombre = nombre;
		this.especie = especie;
		this.raza = raza;
		this.fechaNacimiento = fechaNacimiento;
		this.dueño = dueño;

		this.id = ultimoId++;
	}

	public long getEdad() {
		return fechaNacimiento.until(LocalDateTime.now(), ChronoUnit.YEARS);
	}

	public int getId() {
		return id;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Dueño getDueño() {
		return dueño;
	}

	public void setDueño(Dueño dueño) {
		this.dueño = dueño;
	}
}
