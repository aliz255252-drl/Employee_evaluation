package com.drl.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "hr_tbl_evaluation_setup_evaluators",
       uniqueConstraints = @UniqueConstraint(columnNames = {"ser_evaluation_setup_id", "ser_user_id", "evaluator_level"}))
@Data
public class EvaluationSetupEvaluator {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evaluation_setup_evaluators_seq")
    @SequenceGenerator(name = "evaluation_setup_evaluators_seq", sequenceName = "seq_evaluation_setup_evaluators", allocationSize = 1)
    @Column(name = "ser_evaluation_setup_evaluators_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ser_evaluation_setup_id", nullable = false)
    private EmpEvaluationSetup evaluationSetup;

    @ManyToOne
    @JoinColumn(name = "ser_user_id", nullable = false)
    private User evaluator;

    @Column(name = "evaluator_level", nullable = false)
    private Integer evaluatorLevel;
    
    @Column(name = "bln_evaluate", nullable = false)
    private String blnEvaluate;
    

}
