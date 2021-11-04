package com.infosys.infytel.controller;


import java.util.List; 

import javax.validation.Valid;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import com.infosys.infytel.dto.productDTO;
import com.infosys.infytel.service.ProductService;


@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class ProductController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ProductService productService;

	@Autowired
	private Environment environment;
	
	@GetMapping(value = "/products/name={prodName}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<productDTO>> findproductbyname(@PathVariable String prodName) throws Exception{
		try {
			List<productDTO> cust= productService.findproductbyName(prodName);
			return new ResponseEntity<>(cust,HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	@PostMapping(value = "/seller/{sellerId}/products",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addProduct(@Valid @RequestBody  productDTO sdto,@PathVariable String sellerId) throws Exception{
		try {
			String sd=productService.addproduct(sdto,sellerId);
			String successMessage = environment.getProperty("PRODUCT.INSERT_SUCCESS") + sd ;
			return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		}catch(Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}	
	}
	@DeleteMapping(value = "/seller/{sellerId}/products/{prodId}")
	public ResponseEntity<String> deleteProduct(@PathVariable String prodId,@PathVariable String sellerId) throws Exception {
		try {
		productService.deleteProduct(prodId,sellerId);
		String successMessage = environment.getProperty("PRODUCT.DELETE_SUCCESS");
		return new ResponseEntity<>(successMessage, HttpStatus.OK);
		}	catch(Exception exception) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
	}
}
	@GetMapping(value = "/products/category={category}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<productDTO>> findproductbycategory(@PathVariable String category) throws Exception{
		try {
			List<productDTO> cust= productService.findproductbyCategory(category);
			return new ResponseEntity<>(cust,HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	@PutMapping(value = "seller/{sellerId}/products/{prodId}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updatestock(@RequestBody Integer quantity,@PathVariable String prodId,@PathVariable String sellerId) throws Exception{
		try {
		    productService.updateStock(quantity,prodId,sellerId);
			String successMessage = environment.getProperty("PRODUCT.UPDATE_SUCCESS") ;
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
	
		}
	}
	@GetMapping(value = "/products/{prodId}")
	public ResponseEntity<productDTO> getProduct(@PathVariable String prodId) throws Exception {
		try {
		   productDTO p=productService.findProduct(prodId);
		
		   return new ResponseEntity<>(p, HttpStatus.OK);
		}	catch(Exception exception) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
	@GetMapping(value = "/products")
	public ResponseEntity<List<productDTO>> getAllProduct() throws Exception {
		try {
		   List<productDTO> p=productService.findallproducts();
		   return new ResponseEntity<>(p, HttpStatus.OK);
		}	catch(Exception exception) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()), exception);
		}
	}
}

