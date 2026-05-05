package com.drl.serviceimplementation;

import com.drl.entities.HrEvaluationFactor;
import com.drl.repositry.HrEvaluationFactorRepository;
import com.drl.service.HrEvaluationFactorService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class HrEvaluationFactorServiceImpl implements HrEvaluationFactorService {

	@Autowired
	private HrEvaluationFactorRepository repository;

	@Override
	public HrEvaluationFactor createFactor(HrEvaluationFactor factor) throws Exception {
		if (repository.existsByName(factor.getName())) {
			throw new Exception("Evaluation factor with name '" + factor.getName() + "' already exists.");
		}

		LocalDateTime now = LocalDateTime.now();
		factor.setCreatedDate(now);
		factor.setModifiedDate(now);
		factor.setStatus(true);
		factor.setMachineIp("");
		factor.setCreatedUserId(1);
		factor.setModifiedUserId(1);

		return repository.save(factor);
	}

	@Override
	public List<HrEvaluationFactor> listAllFactors() {
		return repository.findAll();
	}

	@Override
	@Transactional
	public void deleteFactorByName(String name) {
		if (!repository.existsByName(name)) {
			throw new RuntimeException("No factor found with name: " + name);
		}
		repository.deleteByName(name);
	}

	@Override
	public HrEvaluationFactor updateFactor(HrEvaluationFactor updatedFactor) {
		Optional<HrEvaluationFactor> existingOpt = repository.findById(updatedFactor.getFactorId());

		if (existingOpt.isEmpty()) {
			throw new RuntimeException("Evaluation Factor with ID " + updatedFactor.getFactorId() + " not found.");
		}

		HrEvaluationFactor existing = existingOpt.get();

		existing.setName(updatedFactor.getName());
		existing.setDescription(updatedFactor.getDescription());
		existing.setStatus(updatedFactor.getStatus());
		existing.setPassingMarks(updatedFactor.getPassingMarks());
		existing.setTotalMarks(updatedFactor.getTotalMarks());
		existing.setMachineIp("");
		existing.setLevels(updatedFactor.getLevels());
		existing.setModifiedUserId(1);
		existing.setModifiedDate(LocalDateTime.now());
		existing.setEvaluationGroup(updatedFactor.getEvaluationGroup());

		return repository.save(existing);
	}
	@Override
	public List<HrEvaluationFactor> getActiveFactors(String group) {
		return repository.getActiveFactors(group);
	}
	
	public List<String> getUniqueQualitativeLevels() {
        String[] levelsArray = repository.findUniqueQualitativeLevels();
        if (levelsArray == null) {
            return Collections.emptyList();
        }
        // Convert to list, normalize (optional), and sort if needed
        return Arrays.asList(levelsArray);
    }
	

}
