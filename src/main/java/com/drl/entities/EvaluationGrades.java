package com.drl.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "hr_tbl_evaluation_setup_grades")
public class EvaluationGrades {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hr_tbl_evaluation_setup_grades_seq")
    @SequenceGenerator(
        name = "hr_tbl_evaluation_setup_grades_seq",
        sequenceName = "hr_tbl_evaluation_setup_grades_seq",
        allocationSize = 1
    )
    @Column(name = "evaluation_setup_grade_id")
    private Integer evaluationSetupGradeId;

    @Column(name = "grade", nullable = false, length = 100)
    private String grade;

    @Column(name = "initial_marks", nullable = false)
    private Double initialMarks;

    @Column(name = "final_marks", nullable = false)
    private Double finalMarks;

    @Column(name = "description", length = 255)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ser_evaluation_setup_id", nullable = false)
    private EmpEvaluationSetup evaluationSetup;  // assuming you already have EvaluationSetup entity
}
