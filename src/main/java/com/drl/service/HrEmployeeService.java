package com.drl.service;

import com.drl.utils.HrEmployeeDto;
import com.drl.utils.Utils;
import com.drl.entities.HrEmployee;
import com.drl.repositry.HrEmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HrEmployeeService {

    @Autowired
    private HrEmployeeRepository repository;

    public List<HrEmployeeDto> listAllEmployees() {
        return new ArrayList<>();//repository.findAllProjected();
    }
    
    public List<HrEmployee> listEmployees(String name, String statusReason) {
    	if (name != null && !name.isBlank()) {
    		return repository.findByNameContainingIgnoreCase(name.trim());
    	}
    	if (statusReason != null && !statusReason.isBlank()) {
    		return repository.findByStatusReason(statusReason.trim());
    	}
    	return repository.findAll();
    }
    
    public HrEmployee getEmployeeById(Integer employeeId) {
    	Optional<HrEmployee> employee = repository.findById(employeeId);
    	return employee.orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));
    }
    
    public HrEmployee createEmployee(HrEmployee employee) {
    	employee.setEmpId(null);
    	if (employee.getActiveStatus() == null) {
    		employee.setActiveStatus(Boolean.TRUE);
    	}
    	if (employee.getStatusReason() == null || employee.getStatusReason().isBlank()) {
    		employee.setStatusReason("Working");
    	}
    	return repository.save(employee);
    }
    
    public HrEmployee updateEmployee(Integer employeeId, HrEmployee employee) {
    	HrEmployee existing = getEmployeeById(employeeId);
    	employee.setEmpId(existing.getEmpId());
    	return repository.save(employee);
    }
    
    public void softDeleteEmployee(Integer employeeId) {
    	HrEmployee employee = getEmployeeById(employeeId);
    	employee.setActiveStatus(Boolean.FALSE);
    	employee.setStatusReason("Inactive");
    	repository.save(employee);
    }
    
    public List<HrEmployeeDto> getEmployeeByGroup(String group){
    	return repository.getEmployeeByGroup("Working", group);
    	
    }
    
    public List<HrEmployeeDto> getEmployeeByEvaluatrors(int campaignId){
    	int userId = Utils.getCurrentUser().getSerUserId();
    	return repository.getEmployeebyEvaluator(userId, campaignId);    	   	
    }
    
    
}
