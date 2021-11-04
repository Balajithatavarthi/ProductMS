package com.infosys.infytel.service;

import java.util.ArrayList;  
import java.util.List;
import java.util.Optional; 

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.infytel.controller.UserFeign;
import com.infosys.infytel.dto.SellerDTO;
import com.infosys.infytel.dto.productDTO;
import com.infosys.infytel.entity.product;
import com.infosys.infytel.repository.productRepository;
@Service
public class ProductService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@PersistenceContext
	private EntityManager entm;
	@Autowired
	productRepository pRepo;
	@Autowired
	UserFeign uf;


	public String addproduct(productDTO pdto,String sellerId) throws Exception{
		SellerDTO sm=uf.findSeller(sellerId);
		if(sm==null) throw new Exception("Service.SELLER_DOES_NOT_EXIST");
		product pd=pRepo.findTopByOrderByProdIdDesc();
		String rt=pd.getProdId().substring(1);
		Integer st=Integer.parseInt(rt);
		String prodId="";
		st++;
       	prodId="P"+String.valueOf(st);
		product s=new product();
		
		s.setProdId(prodId);
		s.setProdName(pdto.getProdName());
		s.setCategory(pdto.getCategory());
		s.setDescription(pdto.getDescription());
		
		s.setProdRating(pdto.getProdRating());
		s.setStock(pdto.getStock());
		s.setSubCateg(pdto.getSubCateg());
		s.setPrice(pdto.getPrice());
		s.setImage(pdto.getImage());
		s.setSellerId(sellerId);
		pRepo.save(s);
		return s.getProdId();
	}
	
	public List<productDTO> findproductbyName(String prodName) throws Exception {
		List<productDTO> pDTO=new ArrayList<productDTO>();
		List<product> cust = pRepo.findAll();
		for(product p : cust)
		{
			if(p.getProdName().equals(prodName))
			{
				productDTO pd=productDTO.valueOf(p);
				pDTO.add(pd);
			}
		}
		if(pDTO==null) throw new Exception("Service.NO_PRODUCT_FOUND_WITH_THIS_NAME");
		return pDTO;
	}
	
	public void deleteProduct(String prodId,String sellerId) throws Exception {
		SellerDTO sm=uf.findSeller(sellerId);
		if(sm==null) throw new Exception("Service.SELLER_DOES_NOT_EXIST");
		Optional<product> s = pRepo.findById(prodId);
		product sl=s.orElseThrow(() -> new Exception("Service.PRODUCT_NOT_FOUND"));
		pRepo.delete(sl);
	}
	
	public List<productDTO> findproductbyCategory(String category) throws Exception {
		List<productDTO> pDTO=new ArrayList<productDTO>();
		List<product> cust = pRepo.findAll();
		if(cust==null)
			throw new Exception("SERVICE.NO_PRODUCT_FOUND_BASED_ON_CATEGORY");
		for(product p : cust)
		{
			if(p.getCategory().equals(category))
			{
				productDTO pd=productDTO.valueOf(p);
				pDTO.add(pd);
			}
		}
		return pDTO;
	}
	public void updateStock(int quantity,String prodId,String sellerId) throws Exception {
		Optional<product> s = pRepo.findById(prodId);
		product sl=s.orElseThrow(() -> new Exception("Service.PRODUCT_NOT_FOUND"));
		SellerDTO sm=uf.findSeller(sellerId);
		if(sm==null) throw new Exception("Service.SELLER_DOES_NOT_EXIST");
		sl.setStock(sl.getStock()-quantity);
		pRepo.saveAndFlush(sl);
	}
	public productDTO findProduct(String prodId) throws Exception {
		productDTO pDTO=null;
		Optional<product> cust = pRepo.findById(prodId);
		if(!cust.isPresent())
			throw new Exception("Service.PRODUCT_NOT_FOUND");
		pDTO = productDTO.valueOf(cust.get());

		return pDTO;
	}
	public List<productDTO> findallproducts() throws Exception {
		List<product> cust = pRepo.findAll();
		List<productDTO> cu=new ArrayList<>();
		for(product pr:cust) {
			cu.add(productDTO.valueOf(pr));
		}
		return cu;
	}
}
