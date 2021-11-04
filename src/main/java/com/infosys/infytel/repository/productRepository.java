package com.infosys.infytel.repository;


import org.springframework.data.jpa.repository.JpaRepository;  
import com.infosys.infytel.entity.product;

public interface productRepository extends JpaRepository<product, String> {
	product findTopByOrderByProdIdDesc();
	
}
