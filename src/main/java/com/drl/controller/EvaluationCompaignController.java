package com.drl.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drl.config.AppConstantUtil;
import com.drl.entities.HrEvaluationCampaign;
import com.drl.service.HrEvaluationCampaignService;
import com.drl.utils.EvaluationSetupResponse;


@RestController
@RequestMapping(AppConstantUtil.CAMPAIGN_PATH)
public class EvaluationCompaignController {

    @Autowired
    private HrEvaluationCampaignService campaignService;

    @PostMapping("/create")
    public ResponseEntity<?> createCampaign(@RequestBody HrEvaluationCampaign dto) throws Exception {
        if (campaignService.isDuplicateEvaluationCampaign(dto)) {
        	return ResponseEntity
        	        .badRequest()
        	        .body(Map.of("status", "error", "message", "Duplicate Evaluation Campaign"));
        }

        campaignService.makeNewEvaluationCampaign(dto);
        
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Evaluation campaign created successfully"
            ));    
        
    }
        
    @PostMapping("/update")
    public ResponseEntity<?> updateCampaign(@RequestBody HrEvaluationCampaign dto) throws Exception {
        	
        	
        	 campaignService.updateEvaluationCampaign(dto);
        	 
 
             return ResponseEntity.ok(Map.of(
                     "status", "success",
                     "message", "Evaluation campaign Updated successfully"
                 ));    
                    
        
    }
    @PostMapping("/delete")
    public ResponseEntity<?> deleteCampaign(@RequestBody HrEvaluationCampaign dto) throws Exception {
        	
        	
        	 campaignService.deleteEvaluationCampaign(dto);
        	 
 
             return ResponseEntity.ok(Map.of(
                     "status", "success",
                     "message", "Evaluation campaign deleted successfully"
                 ));    
                           
    }
    @PostMapping("/viewAll")
    public ResponseEntity<List<HrEvaluationCampaign>> viewCampaigns( HrEvaluationCampaign criteria) throws Exception {
        List<HrEvaluationCampaign> campaigns = campaignService.viewEvaluationCampaignList(criteria);
        return ResponseEntity.ok(campaigns);
    }
    
    @GetMapping("/getActive")
    public ResponseEntity<?> getActiveCampaign() throws Exception {
        try {
            List<HrEvaluationCampaign> activeCampaigns = campaignService.getActiveCampaign();
            return ResponseEntity.ok(activeCampaigns);  // This will return the list in JSON format
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An error occurred while fetching active campaigns"));
        }
    }

    @GetMapping("/getByEvaluators")
    public ResponseEntity<?> getCampaignByEvaluator() throws Exception {
        try {
            List<EvaluationSetupResponse> evaluatorCampaigns = campaignService.getCampaignByEvaluators();
            return ResponseEntity.ok(evaluatorCampaigns);  // This will return the list in JSON format
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An error occurred while fetching evaluators campaigns"));
        }
    }

}

