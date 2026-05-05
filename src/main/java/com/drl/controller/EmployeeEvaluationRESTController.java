package com.drl.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drl.service.EvaluationService;
import com.drl.service.EmployeeEvaluationService;
import com.drl.utils.EvaluationGradesDTO;

@RestController
@RequestMapping("/api/v1/evaluations")
public class EmployeeEvaluationRESTController {

    @Autowired
    private EmployeeEvaluationService employeeEvaluationService;

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Map<Integer, List<EvaluationGradesDTO>>> getEmployeeEvaluations(
            @PathVariable Integer employeeId,
            @RequestParam(required = false) Integer campaignId) {
        System.out.println("Employee by Campaign :::::::::");
        Map<Integer, List<EvaluationGradesDTO>> evaluations = employeeEvaluationService
                .getGradesForEmployee(employeeId, campaignId);
        if (evaluations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(evaluations);
    }

    @GetMapping("/evaluated-employees")
    public ResponseEntity<?> getEvaluatedEmployees(
            @RequestParam(required = false) Integer campaignId,
            @RequestParam(required = false) Integer employeeId) {
        return evaluationService.getEvaluationAssignments(campaignId, employeeId, "completed");
    }
}
