package com.example.demo.controller;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;

import com.example.demo.dao.GoodsDAO;
import com.example.demo.entity.Goods;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class GoodsController {

	@Autowired
	private GoodsDAO dao;

	@GetMapping("/listGoods")
	public void listGoods(Model model) {
		model.addAttribute("list", dao.findAll());
	}

	@PostMapping("/saveGoods")
	public String saveGoods(Goods g, HttpServletRequest request) {
		String path = request.getServletContext().getRealPath("images");
		MultipartFile uploadFile = g.getUploadFile();
		String fname = null;
		fname = uploadFile.getOriginalFilename();
		if (fname != null && !fname.equals("")) {
			if(g.getFname()!=null) {
				File oldFile = new File(path + "/" + g.getFname());
				oldFile.delete();
			}
			uploadFile = g.getUploadFile();
			try {
				File file = new File(path + "/" + fname);
				FileOutputStream fos = new FileOutputStream(file);
				FileCopyUtils.copy(uploadFile.getBytes(), fos);
				fos.close();
				g.setFname(fname);
			} catch (Exception e) {
				System.out.println("파일 저장 예외 발생 : "+e.getMessage());
			}
		}
		dao.save(g);
		return "redirect:/listGoods";
	}

	@GetMapping("/deleteGoods/{no}")
	public String deleteGoods(@PathVariable int no, HttpServletRequest request) {
		Goods g = dao.findById(no).get();
		String path = request.getServletContext().getRealPath("images");
		if(g.getFname()!=null) {
			File oldFile = new File(path + "/" + g.getFname());
			oldFile.delete();
		}
		dao.deleteById(no);
		return "redirect:/listGoods";
	}

	@GetMapping("/detailGoods")
	public void detailGoods(int no, Model model) {
		model.addAttribute("g", dao.findById(no).get());
	}

}
