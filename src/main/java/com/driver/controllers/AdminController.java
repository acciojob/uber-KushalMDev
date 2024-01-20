package com.driver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.driver.model.Admin;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.services.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AdminService AdminServiceImpl;

	@PostMapping("/register")
	public ResponseEntity<Void> registerAdmin(@RequestBody Admin admin){
		AdminServiceImpl.adminRegister(admin);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<Admin> updateAdminPassword(@RequestParam Integer adminId, @RequestParam String password){
		Admin updatedAdmin=AdminServiceImpl.updatePassword(adminId, password);
		return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public void deleteAdmin(@RequestParam Integer adminId){
		AdminServiceImpl.deleteAdmin(adminId);
	}

	@GetMapping("/listOfCustomers")
	public List<Customer> listOfCustomers() {
		List<Customer> listOfCustomers=AdminServiceImpl.getListOfCustomers();
		return listOfCustomers;
	}

	@GetMapping("/listOfDrivers")
	public List<Driver> listOfDrivers() {
		List<Driver> listOfDrivers=AdminServiceImpl.getListOfDrivers();
		return listOfDrivers;
	}
}
