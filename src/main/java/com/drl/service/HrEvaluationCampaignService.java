package com.drl.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.drl.entities.HrEvaluationCampaign;
import com.drl.utils.EvaluationSetupResponse;

@Service
public interface HrEvaluationCampaignService {

	HrEvaluationCampaign makeNewEvaluationCampaign(HrEvaluationCampaign campaign) throws Exception;

	boolean isDuplicateEvaluationCampaign(HrEvaluationCampaign campaign) throws Exception;

	HrEvaluationCampaign updateEvaluationCampaign(HrEvaluationCampaign campaign) throws Exception;

	HrEvaluationCampaign deleteEvaluationCampaign(HrEvaluationCampaign campaign) throws Exception;

	String removeEvaluationCampaign(String campaignId) throws Exception;

	List<HrEvaluationCampaign> viewEvaluationCampaignList(HrEvaluationCampaign criteria) throws Exception;
	
	List<HrEvaluationCampaign> getActiveCampaign() throws Exception;
	
	List<EvaluationSetupResponse> getCampaignByEvaluators() throws Exception;


}
