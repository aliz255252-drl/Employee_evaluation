package com.drl.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "hr_tbl_evaluation_overall_assesment")
@Data
public class EvaluationOverallAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evaluation_overall_assesment_seq")
    @SequenceGenerator(name = "evaluation_overall_assesment_seq", sequenceName = "hr_tbl_evaluation_overall_assesment_id_seq", allocationSize = 1)
    @Column(name = "overall_assesment_id")
    private Long overallAssesmentId;

    @Column(name = "emp_id", nullable = false)
    private Long empId;

    @Column(name = "total_marks", nullable = false)
    private Integer totalMarks;

    @Column(name = "obtain_marks", nullable = false)
    private Integer obtainMarks;

    @Column(name = "additional_comments")
    private String additionalComments;

    @Column(name = "grading_expression")
    private String gradingExpression;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ser_evaluation_setup_id", nullable = false)
    private EmpEvaluationSetup evaluationSetup;
}
