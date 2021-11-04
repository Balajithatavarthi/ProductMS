package com.infosys.infytel.controller;



import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.infosys.infytel.dto.SellerDTO;
@FeignClient("UserMS")
@CrossOrigin
public interface UserFeign{
	
	@GetMapping(value = "api/seller/{sellerId}")
	SellerDTO findSeller(@PathVariable String sellerId);
}
