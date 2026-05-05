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
import com.drl.entities.HrEvaluationFactor;
import com.drl.service.HrEvaluationFactorService;

@RestController
@RequestMapping(AppConstantUtil.FACTOR_PATH)
public class EvaluationFactorController {
	@Autowired
	private HrEvaluationFactorService service;

	@PostMapping("/create")
	public ResponseEntity<?> createFactor(@RequestBody HrEvaluationFactor factor) throws Exception {
		try {
			HrEvaluationFactor saved = service.createFactor(factor);
			return ResponseEntity.ok().body("Factor created successfully with Name: " + saved.getName());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/view")
	public ResponseEntity<?> getAllFactors() throws Exception {
		List<HrEvaluationFactor> factors = service.listAllFactors();
		return ResponseEntity.ok(factors);
	}

	@DeleteMapping("/delete/{name}")
	public ResponseEntity<?> deleteFactorByName(@PathVariable String name) {
		try {
			service.deleteFactorByName(name);
			return ResponseEntity.ok("Factor with name '" + name + "' deleted successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping("/update")
	public ResponseEntity<?> updateFactor(@RequestBody HrEvaluationFactor factor) {
		try {
			HrEvaluationFactor updated = service.updateFactor(factor);
			return ResponseEntity.ok(updated);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}
	
	@GetMapping("/getActiveFactor")
	public ResponseEntity<?> getActiveFactors(@RequestParam String group) throws Exception {
		List<HrEvaluationFactor> factors = service.getActiveFactors(group);
		return ResponseEntity.ok(factors);
	}

	@GetMapping("/getlevels")
	 public ResponseEntity<List<String>> getUniqueQualitativeLevels() {
        List<String> levels = service.getUniqueQualitativeLevels();
        return ResponseEntity.ok(levels);
    }
	
	
	
}
