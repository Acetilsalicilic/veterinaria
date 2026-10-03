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

	public Mascota(int id, boolean activo, String nombre, String especie, String raza, LocalDateTime fechaNacimiento,Dueño dueño)
	{
		this.id = id;
		this.activo = activo;
		this.nombre = nombre;
		this.especie = especie;
		this.raza = raza;
		this.fechaNacimiento = fechaNacimiento;
		this.dueño = dueño;
	}

	public int getId() {
		return id;
	}

	public boolean getActivo() {
		return activo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getEspecie() {
		return especie;
	}

	public String getRaza() {
		return raza;
	}

	public LocalDateTime getFechaNacimiento() {
		return fechaNacimiento;
	}

	public Dueño getDueño() {
		return dueño;
	}

	public void setDueño(Dueño dueño) {
		this.dueño = dueño;
	}
}
