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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.drl.config.AppConstantUtil;
import com.drl.service.EvaluationService;
import com.drl.utils.EmpEvaluationDTO;
import com.drl.utils.EvaluationDTO;
import com.drl.utils.OverallAssesmentDTO;

@RestController
@RequestMapping(AppConstantUtil.EVALUATION)
public class HrEvaluationController {
	
		@Autowired
		private EvaluationService evaluationService;
	
		 @GetMapping("/getEvaluation")
		    public ResponseEntity<?> getEvaluation(@RequestParam("employeeId") int employeeID, 
		                                            @RequestParam("campaignId") int CampaignID) {
			 return evaluationService.getEvaluation(employeeID, CampaignID);		 
		 }
		 
		 @GetMapping("/assignments")
		 public ResponseEntity<?> getEvaluationAssignments(
				 @RequestParam(required = false) Integer campaignId,
				 @RequestParam(required = false) Integer employeeId,
				 @RequestParam(required = false, defaultValue = "all") String status) {
			 return evaluationService.getEvaluationAssignments(campaignId, employeeId, status);
		 }
		 
		 @GetMapping("/test")
		 public ResponseEntity<?> testEndpoint() {
			 return ResponseEntity.ok(Map.of(
					 "status", "success",
					 "message", "Evaluation API is working"
			 ));
		 }
		 
	 	 
		 @PostMapping("/createEvaluation") 
		 public ResponseEntity<?> createEvaluation( @RequestBody List<EvaluationDTO> evaluationDTO)throws Exception{ 
			 try { 
				 System.out.println("yes it runing \n\n"); 
				 evaluationService.saveEvaluation(evaluationDTO); 
				 return ResponseEntity.status(HttpStatus.CREATED).body("Records saved successfully"); 
				 } 
			 catch (Exception e) { 
				 // Return a 400 Bad Request for any unexpected errors (or add specific custom exceptions) 
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error saving records: " + e.getMessage()); 
				 } 
			 }

	 
		 @PostMapping("/submitEvaluation")
		 public ResponseEntity<?> submitEvaluation( @RequestBody List<EvaluationDTO> evaluationDTO)throws Exception{
			 try {
				 System.out.println("yes it runing \n\n");
				 evaluationService.submitEvaluation(evaluationDTO);
		            return ResponseEntity.status(HttpStatus.CREATED).body("Records saved successfully");
		        } catch (Exception e) {
		            // Return a 400 Bad Request for any unexpected errors (or add specific custom exceptions)
		            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error saving records: " + e.getMessage());
		        }
		 }
	 
	 	@PostMapping(value = "/document", headers = "Accept=application/json")
		public ResponseEntity<Object> addDocument(@RequestPart("file") MultipartFile file, @RequestParam("campaignId") Integer CampaignID,
				@RequestParam("employeeId") Integer employeeId) {
			return evaluationService.addDocument(file,CampaignID,employeeId);
		}
	 
		@DeleteMapping(value = "/document/{id}")
		public ResponseEntity<Object> deleteDocument(@PathVariable("id") Integer documentId) {
		    return evaluationService.deleteDocument(documentId);
		}
		
		
		@PostMapping("/overallAssesment") 
		public ResponseEntity<?> createOverallAssesment(@RequestBody OverallAssesmentDTO dto)throws Exception{ 
		 try { 
				 return evaluationService.createAssessment(dto);
				 } 
		 catch (Exception e) { 
				 // Return a 400 Bad Request for any unexpected errors (or add specific custom exceptions) 
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error saving records: " + e.getMessage()); 
				 } 
		 }
	 
}
