package com.example.demo.entity;


import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name="goods")
public class Goods {
	@Id
	private int no;
	private String item;
	private int price;
	private int qty;
	private String fname;
	
	@Transient
	private MultipartFile uploadFile;
}
