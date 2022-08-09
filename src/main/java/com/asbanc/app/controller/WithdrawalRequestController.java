package com.asbanc.app.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asbanc.app.clients.AfpClientFeign;
import com.asbanc.app.models.entity.Afp;
import com.asbanc.app.models.entity.Cliente;
import com.asbanc.app.models.entity.WithdrawalRequest;
import com.asbanc.app.service.WithdrawalRequestService;

import feign.FeignException;

@RestController
@RequestMapping("/request")
public class WithdrawalRequestController {
	
	@Autowired
	private WithdrawalRequestService service;
	
	@Autowired
	private AfpClientFeign afpClientFeign;
	
	@GetMapping
	public ResponseEntity<?> withdrawalRequestList(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("/{dni}")
	public ResponseEntity<?> withdrawalRequest(@PathVariable String dni){
		Optional<WithdrawalRequest> o=service.findByDni(dni);
		if(o.isPresent()) {
			return ResponseEntity.ok(service.findByDni(dni));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody WithdrawalRequest withdrawalRequest, BindingResult result){
		
		Cliente cliente=null;
		Afp afp=null;
		Map<String,Object> response= new HashMap<>();

		if(result.hasErrors()) {
			return validar(result,response);
		}
		
		/*if(result.hasErrors()) {
			result.getFieldErrors().stream()
			.map(k->{
				return response.put(k.getField(), "El campo "+k.getField()+" "+k.getDefaultMessage());
			}).collect(Collectors.toList());
			return ResponseEntity.badRequest().body(response);
		}*/
		
		//llamar con feign al monto de acuerdo a esa afps
		//1. el monto a retirar no debe ser mayor al monto total que obtengo de la afp, caso contrario, mostrar mensaje del pdf
		//2. el monto a retirar debe ser mayor al 50% del monto total de la afp, caso contrario, mostrar mensaje del pdf
		try{
			cliente= afpClientFeign.detailCustomer(withdrawalRequest.getDni());
			afp=afpClientFeign.detailAfp(cliente.getAfp().getId());
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje","El cliente no esta registrado en la base de datos o hay un error en la comunicacion "+e.getMessage()));
        }
		
		if(cliente!=null && afp!=null) {
			if(withdrawalRequest.getWithdrawal_amount() > cliente.getMonto_disponible()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", "No se puede registrar la solicitud. Monto mayor que el permitido "));
			}else if(withdrawalRequest.getWithdrawal_amount() < cliente.getMonto_disponible()*.5 ) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", "Monto mínimo no cubierto, por favor revise el monto mínimo a retirar "));
			}else {
				withdrawalRequest.setName_afp(afp.getName());
				return ResponseEntity.status(HttpStatus.CREATED).body(service.save(withdrawalRequest));
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@PutMapping("/{dni}")
	public ResponseEntity<?> updateRequest(@Valid @RequestBody WithdrawalRequest withdrawalRequest, BindingResult result, @PathVariable String dni){
		Map<String,Object> response= new HashMap<>();
		
		if(result.hasErrors()) {
			return validar(result,response);
		}
		
		Optional<WithdrawalRequest> o=service.findByDni(dni);
		if(o.isPresent()) {
			WithdrawalRequest withdrawalRequestBd= o.get();
			withdrawalRequestBd.setNumero_cuenta(withdrawalRequest.getNumero_cuenta());
			withdrawalRequestBd.setWithdrawal_date(LocalDate.now());
			return ResponseEntity.status(HttpStatus.CREATED).body(service.save(withdrawalRequestBd));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	private ResponseEntity<?> validar(BindingResult result, Map<String,Object> response) {
		if(result.hasErrors()) {
			result.getFieldErrors().stream()
			.map(k->{
				return response.put(k.getField(), "El campo "+k.getField()+" "+k.getDefaultMessage());
			}).collect(Collectors.toList());
			
		}
		return ResponseEntity.badRequest().body(response);
	}
	
	@DeleteMapping("/{dni}")
	public ResponseEntity<?> deleteRequest(String dni){
		Optional<WithdrawalRequest> w=service.findByDni(dni);
		if(w.isPresent()) {
			service.deleteByDni(w.get());
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}
