package com.asbanc.app.models.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="withdrawalrequests")
public class WithdrawalRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="not may be empty")
	@Size(min=8,max=8,message="El tamaño debe ser de 8 digitos")
	private String dni;
	@NotNull(message="not may be empty")
	private Double withdrawal_amount;
	private String name_afp;
	
	@NotNull(message="not may be empty")
	private LocalDate withdrawal_date;
	
	@NotEmpty(message="not may be empty")
	private String numero_cuenta;
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
	public Double getWithdrawal_amount() {
		return withdrawal_amount;
	}
	public void setWithdrawal_amount(Double withdrawal_amount) {
		this.withdrawal_amount = withdrawal_amount;
	}
	
	public String getNumero_cuenta() {
		return numero_cuenta;
	}
	public void setNumero_cuenta(String numero_cuenta) {
		this.numero_cuenta = numero_cuenta;
	}
	

	public String getName_afp() {
		return name_afp;
	}
	public void setName_afp(String name_afp) {
		this.name_afp = name_afp;
	}
	public LocalDate getWithdrawal_date() {
		return withdrawal_date;
	}
	public void setWithdrawal_date(LocalDate withdrawal_date) {
		this.withdrawal_date = withdrawal_date;
	}

}
