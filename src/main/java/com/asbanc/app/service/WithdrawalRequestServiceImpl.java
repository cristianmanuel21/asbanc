package com.asbanc.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asbanc.app.models.entity.WithdrawalRequest;
import com.asbanc.app.repository.WithDrawalRequestRepository;

@Service
public class WithdrawalRequestServiceImpl implements WithdrawalRequestService{
	
	@Autowired
	private WithDrawalRequestRepository repository;
	
	

	@Override
	@Transactional(readOnly=true)
	public List<WithdrawalRequest> findAll() {
		return (List<WithdrawalRequest>) repository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<WithdrawalRequest> findByDni(String dni) {
		return repository.findByDni(dni);
	}

	@Override
	@Transactional
	public WithdrawalRequest save(WithdrawalRequest withdrawalRequest) {
		return repository.save(withdrawalRequest);
	}

	@Override
	@Transactional
	public void deleteByDni(WithdrawalRequest withdrawalRequest) {
		repository.delete(withdrawalRequest);
	}




	

}
