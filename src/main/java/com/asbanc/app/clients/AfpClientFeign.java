package com.asbanc.app.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.asbanc.app.models.entity.Afp;
import com.asbanc.app.models.entity.Cliente;

@FeignClient(name="micro-afp",url="localhost:8005")
public interface AfpClientFeign {
	
	@GetMapping("/cliente/{dni}")
	Cliente detailCustomer(@PathVariable String dni);
	
	@GetMapping("/afp/{id}")
	Afp detailAfp(@PathVariable Long id);
	
	

}
