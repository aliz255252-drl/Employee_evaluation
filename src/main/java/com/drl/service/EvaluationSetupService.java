package com.drl.service;

import lombok.RequiredArgsConstructor;

import org.apache.http.HttpStatus;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.drl.entities.EmpEvaluationSetup;
import com.drl.entities.EvaluationGrades;
import com.drl.entities.EvaluationSetupEvaluator;
import com.drl.entities.EvaluationSetupFactor;
import com.drl.entities.EvaluatorEvaluation;
import com.drl.entities.HrEmployee;
import com.drl.entities.HrEvaluationCampaign;
import com.drl.entities.HrEvaluationFactor;
import com.drl.entities.User;
import com.drl.repositry.EmpEvaluationSetupRepository;
import com.drl.repositry.EvaluationGradeRepository;
import com.drl.repositry.EvaluationSetupEvaluatorRepository;
import com.drl.repositry.EvaluationSetupFactorRepository;
import com.drl.repositry.EvaluatorEvaluationRepository;
import com.drl.repositry.HrEmployeeRepository;
import com.drl.repositry.HrEvaluationCampaignRepository;
import com.drl.repositry.HrEvaluationFactorRepository;
import com.drl.repositry.UserRepository;
import com.drl.utils.EmployeeEvaluationSetupDTO;
import com.drl.utils.EvaluationGradesDTO;
import com.drl.utils.EvaluationSetupResponse;
import com.drl.utils.EvaluatorDTO;
import com.drl.utils.HrEmployeeDto;
import com.drl.utils.Utils;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EvaluationSetupService {

    private final HrEvaluationFactorRepository factorRepository;
    private final UserRepository userRepoistory;
    private final HrEmployeeRepository employeeRepository;
    private final HrEvaluationCampaignRepository evaluationCampaignRepository;
    private final EmpEvaluationSetupRepository evaluationSetupRepository;
    private final EvaluationSetupFactorRepository factorSetupRepository;
    private final EvaluationSetupEvaluatorRepository evaluatorRepository;
    private final EvaluationGradeRepository gradeRepository;
    private final EvaluatorEvaluationRepository evaluatorEvlauationRepository;

    @Transactional(rollbackFor = Exception.class) // rollback on ANY exception
    public void createEvaluationSetups(EmployeeEvaluationSetupDTO dto) {

        // Validate campaign
        Optional<HrEvaluationCampaign> campaign = evaluationCampaignRepository
                .findFirstByName(dto.getEvaluationCampaign());
        if (campaign.isEmpty()) {
            try {
                Integer id = Integer.parseInt(dto.getEvaluationCampaign());
                campaign = evaluationCampaignRepository.findById(id);
            } catch (NumberFormatException e) {
                // Not an ID
            }
        }
        if (campaign.isEmpty() || !campaign.get().getStatus()) {
            throw new IllegalStateException("Campaign is not found with Active Status: " + dto.getEvaluationCampaign());
        }
        if (dto.getEmplist() == null || dto.getEmplist().isEmpty()) {
            throw new IllegalArgumentException("No employees provided for evaluation setup.");
        }

        for (HrEmployee employee : dto.getEmplist()) {
            employee = employeeRepository.findFirstByEmployeeId(employee.getEmployeeId());
            if (!"Working".equals(employee.getStatusReason())) {
                throw new IllegalStateException("Employee " + employee.getName() + " not in Working status");
            }

            EmpEvaluationSetup setup = evaluationSetupRepository
                    .findFirstByCampaignAndEmployee(campaign.get(), employee)
                    .orElse(null);

            if (setup != null) {
                throw new IllegalStateException(
                        "Evaluation Setup of Employee " + employee.getName() + " already created");
            }

            // Create new setup
            setup = new EmpEvaluationSetup();
            setup.setCampaign(campaign.get());
            setup.setEmployee(employee);
            setup.setFinsihDate(dto.getFinishDate());
            setup.setCalculationMethod(dto.getCalculationMethod());
            setup = evaluationSetupRepository.save(setup);

            // Save Factors
            if (dto.getFactorlist() != null && !dto.getFactorlist().isEmpty()) {
                for (HrEvaluationFactor factor : dto.getFactorlist()) {
                    factor = factorRepository.findFirstByName(factor.getName());
                    if (!factor.getStatus()) {
                        throw new IllegalStateException("Factor " + factor.getName() + " is not Active");
                    }

                    EvaluationSetupFactor esf = new EvaluationSetupFactor();
                    esf.setEvaluationSetup(setup);
                    esf.setFactor(factor);
                    factorSetupRepository.save(esf);
                }
            }

            // Save Evaluators
            if (dto.getEvaluatorlist() != null && !dto.getEvaluatorlist().isEmpty()) {
                for (EvaluatorDTO evaluatorDTO : dto.getEvaluatorlist()) {
                    User evaluator = userRepoistory.findByTxtUserName(evaluatorDTO.getUserName())
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "Evaluator not found: " + evaluatorDTO.getUserName()));

                    EvaluationSetupEvaluator ese = new EvaluationSetupEvaluator();
                    ese.setEvaluationSetup(setup);
                    ese.setEvaluator(evaluator);
                    ese.setBlnEvaluate("false");
                    ese.setEvaluatorLevel(evaluatorDTO.getEvaluatorLevel());

                    evaluatorRepository.save(ese);
                }
            }

            // Save Evaluation Setup Grades
            if (dto.getGradelist() != null && !dto.getGradelist().isEmpty()) {
                for (EvaluationGradesDTO gradeDto : dto.getGradelist()) {
                    EvaluationGrades grade = new EvaluationGrades();
                    grade.setGrade(gradeDto.getGrade());
                    grade.setInitialMarks(gradeDto.getInitialMarks());
                    grade.setFinalMarks(gradeDto.getFinalMarks());
                    grade.setDescription(gradeDto.getDescription());
                    // attach parent setup entity
                    grade.setEvaluationSetup(setup);
                    gradeRepository.save(grade);
                }
            }
        }
    }

    @Transactional
    public EmployeeEvaluationSetupDTO updateEvaluationSetup(EmployeeEvaluationSetupDTO dto) {
        System.out.println("Received DTO for update: " + dto);
        try {
            Optional<HrEvaluationCampaign> campaign = evaluationCampaignRepository
                    .findFirstByName(dto.getEvaluationCampaign());
            if (campaign.isEmpty()) {
                try {
                    Integer id = Integer.parseInt(dto.getEvaluationCampaign());
                    campaign = evaluationCampaignRepository.findById(id);
                } catch (NumberFormatException e) {
                    // Not an ID
                }
            }
            if (campaign.isEmpty()) {
                throw new IllegalStateException("Campaign not found: " + dto.getEvaluationCampaign());
            }

            if (dto.getEmplist() == null || dto.getEmplist().isEmpty()) {
                System.out.println("Warning: Received empty emplist in DTO");
                throw new IllegalArgumentException("No employees provided for evaluation setup update.");
            }

            boolean hasFactors = dto.getFactorlist() != null && !dto.getFactorlist().isEmpty();
            boolean hasEvaluators = dto.getEvaluatorlist() != null && !dto.getEvaluatorlist().isEmpty();

            if (!hasFactors && !hasEvaluators) {
                throw new IllegalArgumentException(
                        "Nothing to update! Please provide either a 'factorlist' or 'evaluatorlist' in your request body.");
            }

            if (dto.getFactorlist() == null)
                System.out.println("Warning: factorlist is NULL in DTO");
            if (dto.getEvaluatorlist() == null)
                System.out.println("Warning: evaluatorlist is NULL in DTO");

            for (HrEmployee employeeDto : dto.getEmplist()) {
                HrEmployee emp = null;
                if (employeeDto.getEmpId() != null) {
                    emp = employeeRepository.findById(employeeDto.getEmpId()).orElse(null);
                    if (emp != null)
                        System.out.println("Found employee by empId: " + emp.getName());
                } else if (employeeDto.getEmployeeId() != null) {
                    emp = employeeRepository.findFirstByEmployeeId(employeeDto.getEmployeeId());
                    if (emp != null)
                        System.out.println("Found employee by employeeId: " + emp.getName());
                }

                if (emp == null) {
                    System.out.println("Employee NOT FOUND with provided data: " + employeeDto);
                    continue;
                }

                System.out.println("Looking for setups with Campaign: " + campaign.get().getName() + " (ID: "
                        + campaign.get().getCampaignId() + ") and Employee: " + emp.getName() + " (ID: "
                        + emp.getEmpId() + ")");
                List<EmpEvaluationSetup> setups = evaluationSetupRepository.findAllByCampaignAndEmployee(campaign.get(),
                        emp);
                System.out.println("Found " + setups.size() + " setups to update.");

                if (setups.isEmpty()) {
                    System.out.println("NO SETUP FOUND to update for this employee/campaign combination.");
                    continue;
                }

                for (EmpEvaluationSetup setup : setups) {
                    System.out.println("---- PROCESSING SETUP ID: " + setup.getId() + " ----");

                    // Check if it's safe to update
                    boolean canUpdate = evaluatorEvlauationRepository.shouldProceedWithEvaluation(setup.getId());
                    System.out.println("Evaluation status (canUpdate): " + canUpdate);

                    if (!canUpdate) {
                        boolean hasFactorsFlag = dto.getFactorlist() != null && !dto.getFactorlist().isEmpty();
                        boolean hasEvaluatorsFlag = dto.getEvaluatorlist() != null && !dto.getEvaluatorlist().isEmpty();

                        String message = "Cannot update the configuration because evaluation has already been started";
                        if (hasFactorsFlag && hasEvaluatorsFlag) {
                            message = "Cannot update the factors and evaluators because evaluation has already been started";
                        } else if (hasFactorsFlag) {
                            message = "Cannot update the factors because evaluation has already been started";
                        } else if (hasEvaluatorsFlag) {
                            message = "Cannot update the evaluators because evaluation has already been started";
                        }

                        throw new IllegalStateException(message);
                    }

                    // Update the factors
                    if (dto.getFactorlist() != null) {
                        System.out.println("Refreshing factors for Setup ID: " + setup.getId() + ". New count: "
                                + dto.getFactorlist().size());
                        factorSetupRepository.deleteBySetupId(setup.getId());
                        factorSetupRepository.flush();
                        for (HrEvaluationFactor factorDto : dto.getFactorlist()) {
                            HrEvaluationFactor factor = factorRepository.findFirstByName(factorDto.getName());
                            if (factor == null) {
                                throw new IllegalStateException("Factor " + factorDto.getName() + " not found");
                            }
                            System.out.println("Adding factor: " + factor.getName());
                            if (!factor.getStatus()) {
                                throw new IllegalStateException("Factor " + factor.getName() + " is not Active");
                            }
                            EvaluationSetupFactor esf = new EvaluationSetupFactor();
                            esf.setEvaluationSetup(setup);
                            esf.setFactor(factor);
                            factorSetupRepository.saveAndFlush(esf);
                        }
                    }

                    // Update Evaluators
                    if (dto.getEvaluatorlist() != null) {
                        System.out.println("Refreshing evaluators for Setup ID: " + setup.getId() + ". New count: "
                                + dto.getEvaluatorlist().size());
                        evaluatorRepository.deleteBySetupId(setup.getId());
                        evaluatorRepository.flush();
                        for (EvaluatorDTO evaluatorDTO : dto.getEvaluatorlist()) {
                            User evaluator = userRepoistory.findByTxtUserName(evaluatorDTO.getUserName())
                                    .orElseThrow(() -> new IllegalArgumentException(
                                            "Evaluator not found: " + evaluatorDTO.getUserName()));
                            System.out.println("Adding evaluator: " + evaluator.getTxtUserName());
                            EvaluationSetupEvaluator ese = new EvaluationSetupEvaluator();
                            ese.setEvaluationSetup(setup);
                            ese.setEvaluator(evaluator);
                            ese.setBlnEvaluate("false");
                            ese.setEvaluatorLevel(evaluatorDTO.getEvaluatorLevel());
                            evaluatorRepository.saveAndFlush(ese);
                        }
                    }
                }
            }
            return dto;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Get all Campaign setups
     */
    public ResponseEntity<List<HrEvaluationCampaign>> getAllCampaignSetups() {
        try {
            List<HrEvaluationCampaign> campList = evaluationSetupRepository.findbyCampaignSetups();
            if (campList != null) {
                return ResponseEntity.status(HttpStatus.SC_OK).body(campList);
            } else {
                return ResponseEntity.status(HttpStatus.SC_OK).body(null);
            }
        } catch (Exception e) {
            System.out.print(e);
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get Employee by CampaignID
     */
    public ResponseEntity<List<HrEmployeeDto>> getSetupById(Integer campaignId) {
        try {
            List<HrEmployeeDto> emplist = evaluationSetupRepository.findEmployeeBySetupID(campaignId);
            if (emplist != null) {
                return ResponseEntity.status(HttpStatus.SC_OK)
                        .body(emplist);
            } else {
                return ResponseEntity.status(HttpStatus.SC_OK)
                        .body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    /**
     * Get Factors by EmployeeID
     */
    public ResponseEntity<List<HrEvaluationFactor>> getFactorsByEmpId(Integer empId, Integer campaignId) {
        try {
            List<HrEvaluationFactor> emplist = evaluationSetupRepository.getFactorsByEmpId(empId, campaignId);
            if (emplist != null) {
                return ResponseEntity.status(HttpStatus.SC_OK)
                        .body(emplist);
            } else {
                return ResponseEntity.status(HttpStatus.SC_OK)
                        .body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    /**
     * Get Evaluators by EmployeeID
     */
    public ResponseEntity<List<EvaluatorDTO>> getEvaluatorsByEmpId(Integer empId, Integer campaignId) {
        try {
            List<EvaluatorDTO> emplist = evaluationSetupRepository.getEvaluatorsByEmpId(empId, campaignId);
            if (emplist != null) {
                return ResponseEntity.status(HttpStatus.SC_OK)
                        .body(emplist);
            } else {
                return ResponseEntity.status(HttpStatus.SC_OK)
                        .body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    /**
     * Get Evaluators by EmployeeID
     */
    public ResponseEntity<List<EvaluationSetupResponse>> getEmpbyEvaluators() {
        try {
            int ser_user_id = Utils.getCurrentUser().getSerUserId();
            List<EvaluationSetupResponse> emplist = evaluationSetupRepository.findByEvaluators_serUserId(ser_user_id)
                    .stream()
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
            if (emplist != null) {
                return ResponseEntity.status(HttpStatus.SC_OK)
                        .body(emplist);
            } else {
                return ResponseEntity.status(HttpStatus.SC_OK)
                        .body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    /**
     * Delete the employee from emplyeeevaluationSetup
     */
    @Transactional
    public ResponseEntity<Object> deleteEmployeeSrtup(Integer employeeId, Integer campaignId) {
        try {
            HrEmployee employee = new HrEmployee();
            employee.setEmpId(employeeId);

            HrEvaluationCampaign campaign = new HrEvaluationCampaign();
            campaign.setCampaignId(campaignId);

            // Find setup record
            List<EmpEvaluationSetup> setups = evaluationSetupRepository.findAllByCampaignAndEmployee(campaign,
                    employee);

            if (setups.isEmpty()) {
                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND)
                        .body("Employee setup not found for the given employeeId and campaignId.");
            }

            for (EmpEvaluationSetup setup : setups) {
                // Check if it's safe to delete
                boolean canDelete = evaluatorEvlauationRepository.shouldProceedWithEvaluation(setup.getId());
                if (!canDelete) {
                    return ResponseEntity.status(HttpStatus.SC_CONFLICT)
                            .body("Cannot delete because evaluation has already been started");
                }
                evaluatorRepository.deleteBySetupId(setup.getId());
                factorSetupRepository.deleteBySetupId(setup.getId());
                evaluationSetupRepository.delete(setup);
            }
            return ResponseEntity.ok("Employee setup(s) processed successfully.");

        } catch (Exception e) {
            // Log error (you should use a logger in production)
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body("An error occurred while trying to delete the employee setup.");
        }
    }

    @Transactional
    public ResponseEntity<Object> deleteFactorsSetup(Integer employeeId, Integer campaignId, Integer factorId) {
        try {
            HrEmployee employee = new HrEmployee();
            employee.setEmpId(employeeId);

            HrEvaluationCampaign campaign = new HrEvaluationCampaign();
            campaign.setCampaignId(campaignId);

            // Find setup record
            List<EmpEvaluationSetup> setups = evaluationSetupRepository.findAllByCampaignAndEmployee(campaign,
                    employee);

            if (setups.isEmpty()) {
                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND)
                        .body("Employee setup not found for the given employeeId and campaignId.");
            }

            for (EmpEvaluationSetup setup : setups) {
                // Check if it's safe to delete
                boolean canDelete = evaluatorEvlauationRepository.shouldProceedWithEvaluation(setup.getId());
                if (!canDelete) {
                    return ResponseEntity.status(HttpStatus.SC_CONFLICT)
                            .body("Cannot delete because evaluation has already been started");
                }
                factorSetupRepository.deleteFctorsBySetupId(setup.getId(), factorId);
            }
            return ResponseEntity.ok("Factor(s) deleted successfully.");

        } catch (Exception e) {
            // Log error (you should use a logger in production)
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body("An error occurred while trying to delete the Factor.");
        }
    }

    @Transactional
    public ResponseEntity<Object> deleteEvaluators(Integer employeeId, Integer campaignId, String user_name) {
        try {
            HrEmployee employee = new HrEmployee();
            employee.setEmpId(employeeId);

            HrEvaluationCampaign campaign = new HrEvaluationCampaign();
            campaign.setCampaignId(campaignId);

            // Find setup record
            List<EmpEvaluationSetup> setups = evaluationSetupRepository.findAllByCampaignAndEmployee(campaign,
                    employee);

            if (setups.isEmpty()) {
                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND)
                        .body("Employee setup not found for the given employeeId and campaignId.");
            }

            Optional<User> user = userRepoistory.findByTxtUserName(user_name);
            if (user.isEmpty()) {
                System.out.println("User NOT FOUND with username: " + user_name);
                return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("User not found: " + user_name);
            }
            System.out.println("Found evaluator: " + user.get().getTxtUserName() + " (ID: " + user.get().getSerUserId() + ")");

            for (EmpEvaluationSetup setup : setups) {
                // Check if it's safe to delete
                boolean canDelete = evaluatorEvlauationRepository.shouldProceedWithEvaluation(setup.getId());
                System.out.println("Checking if delete is allowed for Setup ID " + setup.getId() + ": " + canDelete);
                
                if (!canDelete) {
                    System.out.println("Delete BLOCKED because evaluation already started.");
                    return ResponseEntity.status(HttpStatus.SC_CONFLICT)
                            .body("Cannot delete because evaluation has already been started");
                }
                System.out.println("Proceeding with delete for Evaluator ID: " + user.get().getSerUserId());
                evaluatorRepository.deleteevaluatorsBySetupId(setup.getId(), user.get().getSerUserId());
            }
            return ResponseEntity.ok("Evaluator(s) setup deleted successfully.");

        } catch (Exception e) {
            // Log error (you should use a logger in production)
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

}
