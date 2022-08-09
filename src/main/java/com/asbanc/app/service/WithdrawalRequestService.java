package com.asbanc.app.service;

import java.util.List;
import java.util.Optional;

import com.asbanc.app.models.entity.Afp;
import com.asbanc.app.models.entity.Cliente;
import com.asbanc.app.models.entity.WithdrawalRequest;

public interface WithdrawalRequestService {
	
	public List<WithdrawalRequest> findAll();
	public Optional<WithdrawalRequest> findByDni(String dni);
	public WithdrawalRequest save(WithdrawalRequest withdrawalRequest);
	public void deleteByDni(WithdrawalRequest withdrawalRequest);
	
	//public Optional<Cliente> findByDniFeign(String dni);
	//public Optional<Afp> findByIdFeign(String id);

}
