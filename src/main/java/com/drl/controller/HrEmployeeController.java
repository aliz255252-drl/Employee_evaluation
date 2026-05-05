package com.drl.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drl.config.AppConstantUtil;
import com.drl.entities.HrEmployee;
import com.drl.service.HrEmployeeService;
import com.drl.utils.HrEmployeeDto;

@RestController
@RequestMapping(AppConstantUtil.EMPLOYEE_PATH)
public class HrEmployeeController {

    @Autowired
    private HrEmployeeService service;

    @GetMapping("/list")
    public List<HrEmployeeDto> getAllEmployees() {
        return service.listAllEmployees();
    }
    
    @GetMapping
    public ResponseEntity<List<HrEmployee>> getEmployees(
    		@RequestParam(required = false) String name,
    		@RequestParam(required = false) String statusReason) {
    	return ResponseEntity.ok(service.listEmployees(name, statusReason));
    }
    
    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Integer employeeId) {
    	try {
    		return ResponseEntity.ok(service.getEmployeeById(employeeId));
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
    	}
    }
    
    @PostMapping
    public ResponseEntity<HrEmployee> createEmployee(@RequestBody HrEmployee employee) {
    	HrEmployee created = service.createEmployee(employee);
    	return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer employeeId, @RequestBody HrEmployee employee) {
    	try {
    		return ResponseEntity.ok(service.updateEmployee(employeeId, employee));
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
    	}
    }
    
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer employeeId) {
    	try {
    		service.softDeleteEmployee(employeeId);
    		return ResponseEntity.ok(Map.of("message", "Employee deactivated successfully"));
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
    	}
    }
    
	
	  @GetMapping("/getActive") public ResponseEntity<?>  getActiveCampaign(@RequestParam String group) throws Exception {
	   try 
	  { 
		  List<HrEmployeeDto> employeeList = service.getEmployeeByGroup(group); 
		if (employeeList.isEmpty()) { 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No active campaigns found with the given name")); 
			} return ResponseEntity.ok(employeeList); 
			} 
			catch (Exception e) { 
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An error occurred while fetching active campaigns")); 
			}
	  }
	 
	  @GetMapping("/getByEvaluators/{campaignId}")
	  public ResponseEntity<?> getEmployeeByEvaluators(@PathVariable Integer campaignId) {
		  try 
		  { 
			  List<HrEmployeeDto> employeeList = service.getEmployeeByEvaluatrors(campaignId); 
			if (employeeList.isEmpty()) { 
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No active campaigns found with the given name")); 
				} return ResponseEntity.ok(employeeList); 
				} 
				catch (Exception e) { 
					System.out.println(e);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An error occurred while fetching active campaigns")); 
				}
	  }
	  

}
