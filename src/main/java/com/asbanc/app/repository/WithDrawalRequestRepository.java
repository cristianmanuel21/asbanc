package com.asbanc.app.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.asbanc.app.models.entity.WithdrawalRequest;

public interface WithDrawalRequestRepository extends CrudRepository<WithdrawalRequest,Long>{
	
	public Optional<WithdrawalRequest> findByDni(String dni);

}
