package com.drl.serviceimplementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drl.config.AppConstantUtil;
import com.drl.entities.HrEvaluationCampaign;
import com.drl.repositry.EmpEvaluationSetupRepository;
import com.drl.repositry.HrEvaluationCampaignRepository;
import com.drl.service.HrEvaluationCampaignService;
import com.drl.utils.EvaluationSetupResponse;
import com.drl.utils.Utils;

@Service
public class HrEvaluationCampaignServiceImpl implements HrEvaluationCampaignService {

	@Autowired
	private HrEvaluationCampaignRepository repository;
	@Autowired
	private EmpEvaluationSetupRepository setupRepository;

	@Override
	public HrEvaluationCampaign makeNewEvaluationCampaign(HrEvaluationCampaign campaign) throws Exception {

		LocalDateTime now = LocalDateTime.now();
		campaign.setCreatedDate(now);
		if (!Utils.isEmpty(Utils.getCurrentUser())) {
			campaign.setCreatedUserId(Utils.getCurrentUser().getSerUserId());
		}
		return repository.save(campaign);
	}

	public boolean isDuplicateEvaluationCampaign(HrEvaluationCampaign campaign) {
		return repository.existsByName(campaign.getName()); // or a better match logic
	}

	@Override
	public HrEvaluationCampaign updateEvaluationCampaign(HrEvaluationCampaign campaign) throws Exception {
		if(setupRepository.existsByCampaign_CampaignId(campaign.getCampaignId()))
		{
			throw new RuntimeException("This Campaign '" + campaign.getName() + "' founded in setup can't update");
		}
		// Step 1: Find the existing campaign by ID
		Optional<HrEvaluationCampaign> existingOptional = repository.findById(campaign.getCampaignId());

		if (existingOptional.isEmpty()) {
			throw new RuntimeException("Campaign '" + campaign.getName() + "' not found.");
		}
		

		HrEvaluationCampaign existing = existingOptional.get();

		// Step 2: Update all fields (including name)
		existing.setName(campaign.getName());
		existing.setDescription(campaign.getDescription());
		existing.setYear(campaign.getYear());
		existing.setStartDate(campaign.getStartDate());
		existing.setEndDate(campaign.getEndDate());
		existing.setStatus(campaign.getStatus());
		existing.setMachineIp(campaign.getMachineIp());		
		if (!Utils.isEmpty(Utils.getCurrentUser())) {
			campaign.setModifiedUserId(Utils.getCurrentUser().getSerUserId());
		}
		existing.setModifiedDate(LocalDateTime.now());

		// Step 3: Save and return the updated campaign
		return repository.save(existing);
	}

	@Override
	public HrEvaluationCampaign deleteEvaluationCampaign(HrEvaluationCampaign campaign) throws Exception {
		// Step 1: Find the campaign by name
		Optional<HrEvaluationCampaign> existingOptional = repository.findFirstByName(campaign.getName());

		if (existingOptional.isEmpty()) {
			throw new RuntimeException("Campaign with name '" + campaign.getName() + "' not found.");
		}

		HrEvaluationCampaign existing = existingOptional.get();

		// Step 2: Delete the campaign
		repository.delete(existing);

		// Step 3: Return the deleted campaign (optional)
		return existing;
	}

	@Override
	public String removeEvaluationCampaign(String campaignId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HrEvaluationCampaign> viewEvaluationCampaignList(HrEvaluationCampaign criteria) throws Exception {
		return repository.findAll();
	}
	
	@Override
	public List<HrEvaluationCampaign> getActiveCampaign() throws Exception {
	    try {
	        return repository.findByStatusTrue();  // This returns a List of active campaigns
	    } catch (Exception e) {
	        // Handle any service-related exceptions here
	        throw new Exception("Error fetching active campaigns");
	    }
	}
	
	@Override
	public List<EvaluationSetupResponse> getCampaignByEvaluators()  throws Exception {
		try {
			return repository.findByEvaluators_serUserId(Utils.getCurrentUser().getSerUserId());
		} catch(Exception e) {
			throw new Exception("Error fetching Campaigns");
		}
		
	}

}
