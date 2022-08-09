package com.asbanc.app.models.entity;

public class Cliente {
	
	private Long id;
	private String dni;
	private String nombre;
	private String apellido;
	private String telefono;
	private String correo;
	private Double monto_disponible;
	private Afp afp;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public Double getMonto_disponible() {
		return monto_disponible;
	}
	public void setMonto_disponible(Double monto_disponible) {
		this.monto_disponible = monto_disponible;
	}

	public Afp getAfp() {
		return afp;
	}
	public void setAfp(Afp afp) {
		this.afp = afp;
	}
	
	

}
