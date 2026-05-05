package com.drl.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "hr_tbl_evaluator_evaluation")
public class EvaluatorEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hr_tbl_evaluator_evaluation_seq")
    @SequenceGenerator(name = "hr_tbl_evaluator_evaluation_seq", sequenceName = "hr_tbl_evaluator_evaluation_seq", allocationSize = 1)
    @Column(name = "ser_evaluator_evaluation_id")
    private Integer evaluatorEvaluationId;

    @ManyToOne
    @JoinColumn(name = "ser_evaluation_factor_id", referencedColumnName = "ser_evaluation_factor_id", nullable = false)
    private HrEvaluationFactor evaluationFactor;

    @ManyToOne
    @JoinColumn(name = "ser_user_id", referencedColumnName = "ser_user_id", nullable = false)
    private User user;

    @Column(name = "obtain_marks")
    private Integer obtainMarks;

    @Column(name = "obtain_level")
    private String obtainLevel;

    @Column(name = "recommendation")
    private String recommendation;

    @Column(name = "user_comments")
    private String userComments;

    @ManyToOne
    @JoinColumn(name = "ser_evaluation_setup_evaluators_id", referencedColumnName = "ser_evaluation_setup_evaluators_id", nullable = false)
    private EvaluationSetupEvaluator evaluationSetupEvaluator;
    
}
