package com.drl.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.drl.entities.EmpEvaluationSetup;
import com.drl.entities.EvaluationOverallAssessment;
import com.drl.entities.EvaluationSetupEvaluator;
import com.drl.entities.EvaluatorEvaluation;
import com.drl.entities.EvaluatorEvaluationDocument;
import com.drl.entities.HrEvaluationFactor;
import com.drl.entities.User;
import com.drl.repositry.EmpEvaluationSetupRepository;
import com.drl.repositry.EvaluationGradeRepository;
import com.drl.repositry.EvaluationOverallAssessmentRepository;
import com.drl.repositry.EvaluationSetupEvaluatorRepository;
import com.drl.repositry.EvaluationSetupFactorRepository;
import com.drl.repositry.EvaluatorEvaluationDocumentRepository;
import com.drl.repositry.EvaluatorEvaluationRepository;
import com.drl.repositry.HrEvaluationFactorRepository;
import com.drl.repositry.UserRepository;
import com.drl.utils.EmpEvaluationDTO;
import com.drl.utils.EmployeeEvaluationSetupDTO;
import com.drl.utils.EvaluationDTO;
import com.drl.utils.EvaluationGradesDTO;
import com.drl.utils.EvaluationSetupResponse;
import com.drl.utils.EvaluatorDTO;
import com.drl.utils.EvaluationAssignmentView;
import com.drl.utils.EvaluatorEvaluationDTO;
import com.drl.utils.EvaluatorEvaluationDocumentDTO;
import com.drl.utils.OverallAssesmentDTO;
import com.drl.utils.Utils;

@Service
public class EvaluationService {
	@Autowired
	private EmpEvaluationSetupRepository setupRepository;
	@Autowired
	private EvaluationSetupFactorRepository factorRepository;
	@Autowired
	private EvaluationSetupEvaluatorRepository evaluatorsRepository;
	@Autowired
	private EvaluatorEvaluationRepository evaluationRepository;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private HrEvaluationFactorRepository factorRepo;
	@Autowired
	private EvaluatorEvaluationRepository evaluatorEvaluationRepository;
	@Autowired
	private EvaluationGradeRepository gradeRepository;
	@Autowired
	private EvaluatorEvaluationDocumentRepository docRepository;
	@Autowired
	private EvaluationOverallAssessmentRepository assesmentRepository;

	public ResponseEntity<?> getEvaluation(int employeeId, int campaignId) {
		Integer evaluatorLevel = null;
		EmployeeEvaluationSetupDTO dto = new EmployeeEvaluationSetupDTO();
		try {
			Optional<EmpEvaluationSetup> evaluationSetup = setupRepository
					.findFirstByCampaign_CampaignIdAndEmployee_EmpId(campaignId, employeeId);
			if (evaluationSetup.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(Map.of("error", "Evaluation setup not found for this employee and campaign."));
			}

			EmpEvaluationSetup setup = evaluationSetup.get();
			dto.setCalculationMethod(setup.getCalculationMethod());
			dto.setFinishDate(setup.getFinsihDate());

			List<HrEvaluationFactor> factors = factorRepository.findByEvaluationSetupID(setup.getId());
			if (!factors.isEmpty()) {
				dto.setFactorlist(factors);
			}

			List<EvaluatorDTO> evaluators = evaluatorsRepository.findEvaluatorsBySetupId(evaluationSetup.get().getId());
			if (!evaluators.isEmpty()) {
				User user1 = Utils.getCurrentUser();
				dto.setEvaluatorlist(evaluators);
				Optional<EvaluatorDTO> matchedEvaluator = evaluators.stream()
						.filter(e -> e.getUserName().equalsIgnoreCase(user1.getTxtUserName()))
						.findFirst();
				if (matchedEvaluator.isPresent()) {
					// store evaluator level in a variable
					evaluatorLevel = matchedEvaluator.get().getEvaluatorLevel();
				}
			}

			List<EvaluationGradesDTO> gradelist = gradeRepository.findbySetupId(evaluationSetup.get().getId());
			if (!gradelist.isEmpty()) {
				dto.setGradelist(gradelist);
			}

			Map<String, List<EvaluatorEvaluationDTO>> evualtions = new HashMap<>();
			List<EvaluatorEvaluationDocumentDTO> doclist = new ArrayList<>();
			for (EvaluatorDTO evaluator : evaluators) {
				Optional<User> user = userRepo.findByTxtUserName(evaluator.getUserName());
				EvaluationSetupEvaluator setupEvaluator = evaluatorsRepository
						.findbyevaluationsetupId(evaluationSetup.get().getId(), user.get().getSerUserId());

				// Evaluator level check removed so that ALL assigned evaluators can see each
				// other's grades

				List<EvaluatorEvaluationDTO> evaluatorEvalution = evaluationRepository
						.findBySetupIdAndUserId(setupEvaluator.getId(), user.get().getSerUserId());
				if (!evaluatorEvalution.isEmpty()) {
					evualtions.put(evaluator.getUserName(), evaluatorEvalution);
					List<EvaluatorEvaluationDocumentDTO> list = docRepository
							.findByEvaluatorEvaluation(setupEvaluator.getId());
					if (list != null && !list.isEmpty()) {
						doclist.addAll(list);
					}
				}
			}
			dto.setDocumentlist(doclist);
			dto.setEvualtions(evualtions);
			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "An error occurred while fetching active campaigns"));

		}

	}

	public void saveEvaluation(List<EvaluationDTO> evaluationDTO) throws Exception {
		// List<EvaluationDTO> evaluationDTO = empEvaluationDTO.getEvaluationDTO();
		// List<EvaluatorEvaluationDocument> documentlist =
		// empevaluationDTO.getDocumentsList();
		if (evaluationDTO == null || evaluationDTO.isEmpty()) {
			throw new Exception("List of Evaluator Evaluations cannot be empty");
		}
		for (EvaluationDTO dto : evaluationDTO) {
			// factor Id setting to entity
			if (dto.getEvaluationFactorId() == null) {
				throw new Exception("Missing required fields in DTO");
			}
			EvaluatorEvaluation evaluatorEvaluation = new EvaluatorEvaluation();
			HrEvaluationFactor factor = factorRepo.findFirstByName(dto.getFactorName());
			if (factor != null) {
				evaluatorEvaluation.setEvaluationFactor(factor);
			}

			// ser_user_id is setting into entity
			Optional<User> user = userRepo.findByTxtUserName(dto.getUserName());
			if (user.isPresent()) {
				evaluatorEvaluation.setUser(user.get());
			}

			// evaluators id is setting into the entity
			Optional<EmpEvaluationSetup> evaluatorSetup = setupRepository
					.findFirstByCampaign_CampaignIdAndEmployee_EmpId(dto.getCampaignId(), dto.getSerEmpId());
			if (evaluatorSetup.isPresent()) {
				EvaluationSetupEvaluator evaluationEvaluator = (EvaluationSetupEvaluator) evaluatorsRepository
						.findbyevaluationsetupId(evaluatorSetup.get().getId(), user.get().getSerUserId());
				evaluatorEvaluation.setEvaluationSetupEvaluator(evaluationEvaluator);
			}
			evaluatorEvaluation.setObtainMarks(dto.getObtainMarks());
			evaluatorEvaluation.setObtainLevel(dto.getObtainLevel());
			evaluatorEvaluation.setRecommendation(dto.getRecommendation());
			evaluatorEvaluation.setUserComments(dto.getUserComments());
			// Save the entity
			evaluatorEvaluationRepository.save(evaluatorEvaluation);
			// evaluationEvaluator.setBlnEvaluate("ture");
			// evaluatorsRepository.saveAndFlush(evaluationEvaluator);

		}
	}

	public void submitEvaluation(List<EvaluationDTO> evaluationDTO) throws Exception {
		if (evaluationDTO == null || evaluationDTO.isEmpty()) {
			throw new Exception("List of Evaluator Evaluations cannot be empty");
		}
		EvaluationDTO deldto = evaluationDTO.get(0);
		Optional<EmpEvaluationSetup> delevaluatorSetup = setupRepository
				.findFirstByCampaign_CampaignIdAndEmployee_EmpId(deldto.getCampaignId(), deldto.getSerEmpId());
		EvaluationSetupEvaluator delevaluationEvaluator = (EvaluationSetupEvaluator) evaluatorsRepository
				.findbyevaluationsetupId(delevaluatorSetup.get().getId(), Utils.getCurrentUser().getSerUserId());
		System.out.println(delevaluationEvaluator.getId() + "\n\n ::::::::::::::::::::::::::::::");
		int delnum = evaluatorEvaluationRepository.deletebysetupId(delevaluationEvaluator.getId());
		System.out.println(delnum);
		for (EvaluationDTO dto : evaluationDTO) {
			// factor Id setting to entity
			if (dto.getEvaluationFactorId() == null) {
				throw new Exception("Missing required fields in DTO");
			}
			EvaluatorEvaluation evaluatorEvaluation = new EvaluatorEvaluation();
			HrEvaluationFactor factor = factorRepo.findFirstByName(dto.getFactorName());
			if (factor != null) {
				evaluatorEvaluation.setEvaluationFactor(factor);
			}

			// ser_user_id is setting into entity
			Optional<User> user = userRepo.findByTxtUserName(dto.getUserName());
			if (user.isPresent()) {
				evaluatorEvaluation.setUser(user.get());
			}

			// evaluators id is setting into the entity
			Optional<EmpEvaluationSetup> evaluatorSetup = setupRepository
					.findFirstByCampaign_CampaignIdAndEmployee_EmpId(dto.getCampaignId(), dto.getSerEmpId());
			Optional<EvaluationSetupEvaluator> updateevaluator = null;
			if (evaluatorSetup.isPresent()) {
				EvaluationSetupEvaluator evaluationEvaluator = (EvaluationSetupEvaluator) evaluatorsRepository
						.findbyevaluationsetupId(evaluatorSetup.get().getId(), user.get().getSerUserId());
				evaluatorEvaluation.setEvaluationSetupEvaluator(evaluationEvaluator);
				updateevaluator = evaluatorsRepository.findById(evaluationEvaluator.getId());
			}
			evaluatorEvaluation.setObtainMarks(dto.getObtainMarks());
			evaluatorEvaluation.setObtainLevel(dto.getObtainLevel());
			evaluatorEvaluation.setRecommendation(dto.getRecommendation());
			evaluatorEvaluation.setUserComments(dto.getUserComments());
			// Save the entity
			evaluatorEvaluationRepository.save(evaluatorEvaluation);
			EvaluationSetupEvaluator evaluationEvaluator = updateevaluator.get();
			evaluationEvaluator.setBlnEvaluate("true");
			evaluatorsRepository.saveAndFlush(evaluationEvaluator);
		}
	}

	public ResponseEntity<Object> addDocument(MultipartFile file, Integer campaignID, Integer employeeId) {
		EvaluatorEvaluationDocument document = new EvaluatorEvaluationDocument();
		try {
			Optional<EmpEvaluationSetup> evaluatorSetup = setupRepository
					.findFirstByCampaign_CampaignIdAndEmployee_EmpId(campaignID, employeeId);
			if (evaluatorSetup.isPresent()) {
				EvaluationSetupEvaluator evaluationEvaluator = evaluatorsRepository.findbyevaluationsetupId(
						evaluatorSetup.get().getId(), Utils.getCurrentUser().getSerUserId());

				document.setEvaluatorEvaluation(evaluationEvaluator);
				document.setFileName(file.getOriginalFilename());
				document.setFileType(file.getContentType());
				byte[] fileData = file.getBytes();
				System.out.println("File data length: " + fileData.length); // Log file data length
				document.setFileData(fileData);
				document.setUploadDate(LocalDateTime.now());
				document = docRepository.saveAndFlush(document);
				document.setEvaluatorEvaluation(null);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(Map.of("error", "An error occurred while fetching active campaigns"));
			}
		} catch (Exception e) {
			e.printStackTrace(); // Log full stack trace for debugging
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "File Cannot Uploaded: " + e.getMessage()));
		}
		return ResponseEntity.ok(document);
	}

	public ResponseEntity<Object> deleteDocument(Integer id) {
		Optional<EvaluatorEvaluationDocument> document = docRepository.findById(id);
		EvaluationSetupEvaluator evaluationEvaluator = document.get().getEvaluatorEvaluation();
		if (evaluationEvaluator.getBlnEvaluate().equalsIgnoreCase("true")) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "This evaluation is completed so document is not deletable"));
		}
		docRepository.deleteById(id);
		return ResponseEntity.ok("Document deleted Successfully");
	}

	public ResponseEntity<?> createAssessment(OverallAssesmentDTO dto) {
		try {
			// Validate request DTO
			if (dto.getCampaignId() == null || dto.getEmployeeId() == null) {
				return ResponseEntity.badRequest().body("Campaign ID and Employee ID must not be null.");
			}

			Optional<EmpEvaluationSetup> setupOpt = setupRepository
					.findFirstByCampaign_CampaignIdAndEmployee_EmpId(dto.getCampaignId(), dto.getEmployeeId());

			if (setupOpt.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("No evaluation setup found for given campaign and employee.");
			}

			EmpEvaluationSetup setup = setupOpt.get();
			System.out.println("Setup id :::::::::::::::::::" + setup.getId());
			if (!setupRepository.shouldProceedWithEvaluation(setup.getId())) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body("Evaluation cannot proceed. It may already be completed or locked.");
			}

			EvaluationOverallAssessment assessment = new EvaluationOverallAssessment();
			assessment.setAdditionalComments(dto.getAdditionalComments());
			assessment.setEmpId(dto.getEmpId());
			assessment.setEvaluationSetup(setup);
			assessment.setGradingExpression(dto.getGradingExpression());
			assessment.setObtainMarks(dto.getObtainMarks());
			assessment.setTotalMarks(dto.getTotalMarks());

			EvaluationOverallAssessment savedAssessment = assesmentRepository.saveAndFlush(assessment);

			return ResponseEntity.status(HttpStatus.CREATED).body("Evaluation Completed Successfully");

		} catch (Exception ex) {
			// Log the error (use logger in real projects)
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while creating the assessment.");
		}
	}

	public ResponseEntity<?> getEvaluationAssignments(Integer campaignId, Integer employeeId, String status) {
		try {
			User currentUser = Utils.getCurrentUser();
			Integer userId = (currentUser != null) ? currentUser.getSerUserId() : null;
			List<EvaluationAssignmentView> assignments;

			if (campaignId != null) {
				assignments = setupRepository.findAllAssignmentsByCampaign(campaignId);
			} else {
				assignments = setupRepository.findAllAssignments();
			}

			List<EvaluationSetupResponse> response = assignments.stream()
					.filter(item -> {
						if (employeeId != null && !employeeId.equals(item.getSerEmpId())) {
							return false;
						}
						if (status == null || status.isBlank() || "all".equalsIgnoreCase(status)) {
							return true;
						}
						boolean isDone = "true".equalsIgnoreCase(item.getBlnEvaluate());
						if ("completed".equalsIgnoreCase(status)) {
							return isDone;
						}
						if ("remaining".equalsIgnoreCase(status) || "pending".equalsIgnoreCase(status)) {
							return !isDone;
						}
						return true;
					})
					.map(item -> new EvaluationSetupResponse(
							item.getCampaignId(),
							item.getSerEmpId(),
							item.getName(),
							item.getEmployeeId(),
							item.getEmpName(),
							item.getDepartment(),
							item.getDesignation(),
							item.getGroupName(),
							item.getFinishDate(),
							item.getBlnEvaluate()))
					.toList();

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Unable to fetch evaluator assignments"));
		}
	}
}
