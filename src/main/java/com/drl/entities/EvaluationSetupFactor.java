package com.drl.entities;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "hr_tbl_evaluation_setup_factors",
       uniqueConstraints = @UniqueConstraint(columnNames = {"ser_evaluation_setup_id", "ser_evaluation_factor_id"}))
@Data
public class EvaluationSetupFactor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evaluation_setup_factors_seq")
    @SequenceGenerator(name = "evaluation_setup_factors_seq", sequenceName = "seq_evaluation_setup_factors", allocationSize = 1)
    @Column(name = "ser_evaluation_setup_factors_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ser_evaluation_setup_id", nullable = false)
    private EmpEvaluationSetup evaluationSetup;

    @ManyToOne
    @JoinColumn(name = "ser_evaluation_factor_id", nullable = false)
    private HrEvaluationFactor factor;
}
