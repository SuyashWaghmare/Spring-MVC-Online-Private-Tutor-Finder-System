package com.cjc.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjc.model.Admin;
import com.cjc.repository.AdminRepository;
import com.cjc.service.AdminService;

@Service
public class AdminServiceIMPL implements AdminService {

	@Autowired
	private AdminRepository adminrepo;

	@Override
	public Admin loginSave(Admin admin) {

		return adminrepo.save(admin);
	}

	@Override
	public String adminLogin(Admin admin) {
		Admin savedAdmin = adminrepo.findByUsername(admin.getUsername());

		if (savedAdmin != null && savedAdmin.getPassword().equals(admin.getPassword())) {
			return "Login successful";
		} else {
			return "Invalid credentials";
		}
	}

}
