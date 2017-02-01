package com.teqnihome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teqnihome.service.CodeGenEntityService;

/**
 *
 * @author Vinod
 */
@Controller
@RequestMapping(value = "/codegen-entity")
public class CodeGenEntityController {

	@Autowired
	private CodeGenEntityService codeGenEntityService;
	
	@RequestMapping("/create")
	@ResponseBody
	public String create() {
		try {
			return codeGenEntityService.createEntity().toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Error creating the entity: " + ex.toString();
		}
	}

}
